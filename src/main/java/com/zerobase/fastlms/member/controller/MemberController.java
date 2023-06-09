package com.zerobase.fastlms.member.controller;

import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.model.ResetPasswordRequestInput;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class MemberController {
    // 스프링 시큐리티에 의해 모든 페이지에 대해 로그인 이후 이용할 수 있게 설정됨

    private final MemberService memberService;

    // get post 둘 다 받을 수 있도록
    @RequestMapping("/member/login")
    public String login(HttpServletRequest request) {
        return "member/login";
    }

    @RequestMapping("/member/login-success")
    public String loginSuccess(HttpServletRequest request) {
        String targetUri = "/";
        Object object = request.getSession().getAttribute("prev");
        if (object != null) {
            String originalUrl = object.toString();
            targetUri = originalUrl.substring(originalUrl.indexOf("/", 8));
        }
        String userId = request.getSession().getAttribute("username").toString();
        String ip = RequestUtil.getClientIp(request);
        String agent = RequestUtil.getUserAgent(request);
        memberService.loginHistory(ip, agent, userId);
        return "redirect:" + targetUri;
    }

    @GetMapping("/member/find/password")
    public String findPassword() {
        return "member/find_password";
    }

    @PostMapping("/member/find/password")
    public String findPasswordSubmit(Model model,
                                     ResetPasswordRequestInput parameter) {
        boolean result = false;
        try {
            result = memberService.sendResetPassword(parameter);
        } catch (Exception e) {
        }

        model.addAttribute("result", result);
        return "member/find_password_result"; // 주소와 view가 함께 바뀜
    }

    @GetMapping("/member/reset/password")
    public String resetPassword(Model model,
                                HttpServletRequest request) {
        String uuid = request.getParameter("id");
        model.addAttribute("uuid", uuid);

        boolean result = memberService.checkResetPassword(uuid);

        model.addAttribute("result", result);

        return "member/reset_password";
    }

    @PostMapping("/member/reset/password")
    public String resetPasswordSubmit(Model model,
                                      ResetPasswordInput parameter) {
        boolean result = false;
        try {
            result = memberService.resetPassword(parameter.getUuid(), parameter.getPassword());
        } catch (Exception e) {
        }

        model.addAttribute("result", result);

        return "member/reset_password_result";
    }

    @GetMapping("/member/register")
    public String register() {
        return "member/register";
    }

    @PostMapping("/member/register")
    public String registerSubmit(Model model, //클라이언트에게 데이터를 내리기 위해 사용되는 인터페이스
                                 HttpServletRequest request,
                                 MemberInput parameter) {
        boolean result = memberService.register(parameter);
        model.addAttribute("result", result);
        return "member/register-complete";
    }

    // http://www.naver.com/news/list.do?id=123
    // 프로토콜://도메인(IP)/서브주소
    // 포트는 http 80, https 443일 때 생략된다
    @GetMapping("/member/email-auth")
    public String emailAuth(Model model,
                            HttpServletRequest request) {
        String uuid = request.getParameter("id");
        System.out.println(uuid);

        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result", result);

        return "member/email-auth";
    }

    @GetMapping("/member/info")
    public String memberInfo() {
        return "member/info";
    }
}
