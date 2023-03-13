package com.zerobase.fastlms.member.controller;

import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.member.model.MemberInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/register")
    public String register() {
        return "member/register";
    }

    @PostMapping("/member/register")
    public String registerSubmit(Model model, //클라이언트에게 데이터를 내리기 위해 사용되는 인터페이스
                                 HttpServletRequest request,
                                 MemberInput parameter) {
        boolean result = memberService.register(parameter);
        model.addAttribute("result",result);
        return "member/register-complete";
    }
}
