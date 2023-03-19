package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.Banner;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BannerListDto {
    List<BannerDto> list;
    long totalCount;

    public static BannerListDto of(List<BannerDto> list, long totalCount){
        return BannerListDto.builder()
                .totalCount(totalCount)
                .list(list)
                .build();
    }
}
