package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberSearchParam;
import com.zerobase.fastlms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminCategoryController {


    @GetMapping("/admin/category/list.do") // 확장자로도 구분하기 위해 어드민은 .do로
    public String list(Model model,
                       MemberSearchParam parameter) {
       return "admin/category/list";
    }


}
