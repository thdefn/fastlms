package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.CategoryInput;
import com.zerobase.fastlms.admin.model.MemberSearchParam;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminCategoryController {

    private final CategoryService categoryService;


    @GetMapping("/admin/category/list.do") // 확장자로도 구분하기 위해 어드민은 .do로
    public String list(Model model,
                       MemberSearchParam parameter) {
        List<CategoryDto> list = categoryService.list();
        model.addAttribute("list",list);
       return "admin/category/list";
    }

    @PostMapping("/admin/category/add.do")
    public String add(Model model,
                      CategoryInput parameter){
        boolean result = categoryService.add(parameter.getCategoryName());
        return "redirect:/admin/category/list.do";
    }


}
