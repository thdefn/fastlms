package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.entity.OpenType;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BannerDto {
    long id;

    String bannerName;

    String imagePath;

    String url;

    String openType;

    int sortValue;

    boolean openYn;

    LocalDateTime regDt;

    public static BannerDto of(Banner banner){
        return BannerDto.builder()
                .id(banner.getId())
                .bannerName(banner.getBannerName())
                .imagePath(banner.getImagePath())
                .url(banner.getUrl())
                .openType(banner.getOpenType().name())
                .sortValue(banner.getSortValue())
                .openYn(banner.isOpenYn())
                .regDt(banner.getRegDt())
                .build();
    }
}
