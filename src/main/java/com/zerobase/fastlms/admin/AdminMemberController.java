package com.zerobase.fastlms.admin;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberDetailParam;
import com.zerobase.fastlms.admin.model.MemberSearchParam;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.util.PageUtil;
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
    public String list(Model model,
                       MemberSearchParam parameter){
        parameter.init();
        List<MemberDto> members = memberService.list(parameter);
        model.addAttribute("list", members);
        long totalCount = 0;
        if (members != null && members.size() > 0){
            totalCount = members.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        PageUtil pageUtil = new PageUtil(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);
        model.addAttribute("pager",pageUtil.pager());
        model.addAttribute("totalCount",totalCount);
        return "admin/member/list";
    }

    @GetMapping("/admin/member/detail.do") // 확장자로도 구분하기 위해 어드민은 .do로
    public String detail(Model model,
                       MemberDetailParam parameter){
        MemberDto member = memberService.detail(parameter.getUserId());
        model.addAttribute("member",member);
        return "admin/member/detail";
    }
}
