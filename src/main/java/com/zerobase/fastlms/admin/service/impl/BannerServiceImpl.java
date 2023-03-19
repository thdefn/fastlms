package com.zerobase.fastlms.admin.service.impl;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.dto.BannerListDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.entity.OpenType;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.repository.BannerRepository;
import com.zerobase.fastlms.admin.service.BannerService;
import com.zerobase.fastlms.util.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;


    @Override
    public boolean add(BannerInput parameter, MultipartFile image, String filePath) {
        if (image.getOriginalFilename().isEmpty()) {
            return false;
        }

        String origin = image.getOriginalFilename();
        String extension = origin.substring(origin.lastIndexOf("."));

        File file = new File(Constant.BANNER_IMG_PATH, UUID.randomUUID() + extension);

        try {
            image.transferTo(file);
        } catch (IOException e) {
            return false;
        }

        bannerRepository.save(
                Banner.builder()
                        .bannerName(parameter.getName())
                        .imagePath(file.getName())
                        .openType(OpenType.valueOf(parameter.getOpenType().toUpperCase()))
                        .openYn(parameter.isOpenYn())
                        .regDt(LocalDateTime.now())
                        .sortValue(parameter.getSortValue())
                        .url(parameter.getLink())
                        .build()
        );

        return true;
    }

    @Override
    public BannerListDto list() {
        long totalCount = bannerRepository.count();
        return BannerListDto.of(
                bannerRepository.findAll()
                        .stream().map(BannerDto::of).collect(Collectors.toList()),
                totalCount
        );
    }

    @Override
    public BannerDto detail(long bannerId) {
        Optional<Banner> optionalBanner = bannerRepository.findById(bannerId);
        if(!optionalBanner.isPresent()){
            return null;
        }

        return BannerDto.of(
                optionalBanner.get()
        );
    }

    @Override
    public void update(BannerInput parameter, MultipartFile image, String folderPath) {

        Optional<Banner> optionalBanner = bannerRepository.findById(parameter.getBannerId());
        if(!optionalBanner.isPresent()){
            return;
        }

        Banner banner = optionalBanner.get();

        if (image != null && !image.getOriginalFilename().isEmpty()) {
            String origin = image.getOriginalFilename();
            String extension = origin.substring(origin.lastIndexOf("."));

            File file = new File(Constant.BANNER_IMG_PATH, UUID.randomUUID() + extension);

            try {
                image.transferTo(file);
                banner.setImagePath(file.getName());
                System.out.println(file.getName());
            } catch (IOException e) {
            }
        }

        banner.setBannerName(parameter.getName());
        banner.setOpenType(OpenType.valueOf(parameter.getOpenType().toUpperCase()));
        banner.setOpenYn(parameter.isOpenYn());
        banner.setSortValue(parameter.getSortValue());
        banner.setUrl(parameter.getLink());

        bannerRepository.save(banner);
    }

    @Override
    public void delete(List<String> delete) {
        bannerRepository.deleteByIdIn(
                delete.stream().map(Long::parseLong).collect(Collectors.toList())
        );
    }

    @Override
    public List<BannerDto> listBySorted() {
        return bannerRepository.findAllByOpenYnIsTrueOrderBySortValueDesc()
                .stream().map(BannerDto::of).collect(Collectors.toList());
    }
}
