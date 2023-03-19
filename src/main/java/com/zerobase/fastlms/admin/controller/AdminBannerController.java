package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.dto.BannerListDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.service.BannerService;
import com.zerobase.fastlms.util.Constant;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminBannerController {

    private final BannerService bannerService;


    @GetMapping("/admin/banner/list.do")
    public String list(Model model) {
        BannerListDto dto = bannerService.list();
        model.addAttribute("list",dto.getList());
        model.addAttribute("totalCount",dto.getTotalCount());
        return "admin/banner/list";
    }

    @GetMapping("/admin/banner/add.do")
    public String add(Model model) {
        return "/admin/banner/add";
    }

    @PostMapping("/admin/banner/add.do")
    public String addSubmit(Model model, HttpServletRequest request,
                            BannerInput parameter, MultipartFile image) {
        String folderPath = request.getSession().getServletContext().getRealPath(Constant.BANNER_IMG_PATH);
        bannerService.add(parameter, image, folderPath);
        return "redirect:/admin/banner/list.do";
    }

    @PostMapping("/admin/banner/delete.do")
    public String delete(Model model) {
        return "redirect:/admin/category/list.do";
    }

    @PostMapping("/admin/banner/detail.do")
    public String update(Model model,
                         BannerInput parameter) {
        return "redirect:/admin/category/detail.do";
    }


}
