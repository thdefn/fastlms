package com.zerobase.fastlms.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 스프링 부트에서 만들어진 핸들러를 상속
 */
@Component
public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    public UserAuthenticationSuccessHandler() {
        super();
        setUseReferer(true);
        setAlwaysUseDefaultTargetUrl(true);
        setDefaultTargetUrl("/member/login-success");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        request.getSession().setAttribute("username",request.getParameter("username"));
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            request.getSession().setAttribute("prev", savedRequest.getRedirectUrl());
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
