package com.zerobase.fastlms.admin.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BannerListParam {
    int pageIndex;
    int pageSize; // 페이지 개수

    public int getPageStartIdx(){
        init();
        return (pageIndex - 1);
    }

    public int getPageEnd(){
        init();
        return pageSize;
    }

    public void init(){
        if (pageIndex < 1){
            pageIndex = 1;
        }

        if (pageSize < 10){
            pageSize = 10;
        }
    }
}
