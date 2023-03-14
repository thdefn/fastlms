package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity // 웹 시큐리티 활성화
@Configuration // 설정 파일임
public class SecurityConfiguration {

    private final MemberService memberService;

    @Bean
    UserAuthenticationFailureHandler getFailureHandler() {
        return new UserAuthenticationFailureHandler();
    }

    @Bean
    PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests()
                .antMatchers("/", "/member/register", "/member/email-auth").permitAll()
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/member/login")
                .failureHandler(getFailureHandler()).permitAll()

                .and()
                .build();
    }

    @Bean
    AuthenticationManager configure(AuthenticationManagerBuilder auth) throws Exception {
        return auth.userDetailsService(memberService)
                .passwordEncoder(getPasswordEncoder())
                .and()
                .build();
    }
}
