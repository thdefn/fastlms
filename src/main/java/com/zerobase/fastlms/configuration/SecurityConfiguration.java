package com.zerobase.fastlms.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity // 웹 시큐리티 활성화
@Configuration // 설정 파일임
public class SecurityConfiguration {
    private final UserAuthenticationSuccessHandler successHandler;

    private final UserAuthenticationFailureHandler failureHandler;

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().antMatchers("/resources/assets/**", "/favicon.ico");
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

        return http.authorizeHttpRequests()
                .antMatchers("/", "/member/register", "/member/email-auth",
                        "/member/find/password", "/member/reset/password").permitAll()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/member/login")
                .failureHandler(failureHandler)
                .successHandler(successHandler)
                .permitAll()

                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 모든 http 메서드 포함
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true) // 세션은 초기화

                .and()
                .exceptionHandling()
                .accessDeniedPage("/error/denied")

                .and()
                .build();
    }

    /**
     * PasswordEncoder와 UserDetailsService의 구현체 자동 주입
     * 스프링 시큐리티에서 PasswordEncoder 설정이 Bcrypt이기 때문에
     * 회원가입할 때에도 동일한 형태로 저장이 되어야 로그인 가능
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }
}
