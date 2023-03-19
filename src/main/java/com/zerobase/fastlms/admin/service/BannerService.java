package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.dto.BannerListDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerService {
    boolean add(BannerInput parameter, MultipartFile image, String filePath);

    BannerListDto list();

    BannerDto detail(long bannerId);

    void update(BannerInput parameter, MultipartFile image, String folderPath);

    @Transactional
    void delete(List<String> delete);

    List<BannerDto> listBySorted();
}
