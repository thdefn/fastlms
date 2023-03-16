package com.zerobase.fastlms.admin;

import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping("/admin/member/list.do") // 확장자로도 구분하기 위해 어드민은 .do로
    public String main(Model model){
        List<Member> members = memberService.list();
        model.addAttribute("list", members);
        return "admin/member/list";
    }
}
