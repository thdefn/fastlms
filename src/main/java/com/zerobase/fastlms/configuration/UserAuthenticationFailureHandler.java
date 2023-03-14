package com.zerobase.fastlms.configuration;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 스프링 부트에서 만들어진 핸들러를 상속
 */
public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String msg = "로그인에 실패하였습니다.";

        // org.springframework.security.authentication.InternalAuthenticationServiceException: 이메일 활성화 이후에 로그인을 해주세요
        if (exception instanceof Exception){ // 객체가 어떤 클래스를 상속받았는지
            msg = exception.getMessage();

        }
        setUseForward(true);
        setDefaultFailureUrl("/member/login?error=true");
        request.setAttribute("errorMessage",msg);

        System.out.println("로그인에 실패하였습니다.");

        super.onAuthenticationFailure(request, response, exception);
    }
}
