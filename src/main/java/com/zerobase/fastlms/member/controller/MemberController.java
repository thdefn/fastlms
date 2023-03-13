package com.zerobase.fastlms.member.controller;

import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.member.model.MemberInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    //@RequestMapping(value = "/member/register", method = RequestMethod.GET)
    @GetMapping("/member/register")
    public String register() {
        return "member/register";
    }

    //register.html에서 form이 submit 될 때 작동
    //@RequestMapping(value = "/member/register", method = RequestMethod.POST)
    @PostMapping("/member/register")
    public String registerSubmit(HttpServletRequest request,
                                 MemberInput parameter) {
        boolean result = memberService.register(parameter);
        return "member/register-complete";
    }
}
