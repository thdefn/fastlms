package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerListDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import org.springframework.web.multipart.MultipartFile;

public interface BannerService {
    boolean add(BannerInput parameter, MultipartFile image, String filePath);

    BannerListDto list();
}
