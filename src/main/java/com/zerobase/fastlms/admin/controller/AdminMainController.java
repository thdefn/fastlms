package com.zerobase.fastlms.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminMainController {

    @GetMapping("/admin/main.do") // 확장자로도 구분하기 위해 어드민은 .do로
    public String main(){
        return "admin/main";
    }
}
