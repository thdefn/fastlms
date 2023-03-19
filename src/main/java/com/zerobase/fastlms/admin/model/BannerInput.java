package com.zerobase.fastlms.admin.model;

import com.zerobase.fastlms.admin.entity.OpenType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BannerInput {
    long bannerId;
    String name;
    String link;
    String openType;
    int sortValue;
    boolean openYn;

}
