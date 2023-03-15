package com.zerobase.fastlms.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminMemberController {

    @GetMapping("/admin/member/list.do") // 확장자로도 구분하기 위해 어드민은 .do로
    public String main(){
        return "admin/member/list";
    }
}
