package com.zerobase.fastlms.admin.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSearchParam {
    long pageIndex;
    long pageSize; // 페이지 개수
    String searchType;
    String searchValue;

    String userId;


    /**
     * limit 0, 10 : pageIndex: 1
     * limit 10, 10 : pageIndex: 2
     * limit 20, 10 : pageIndex: 3
     * limit 30, 10 : pageIndex: 4
     */
    public long getPageStart(){
        init();
        return (pageIndex - 1) * pageSize ;
    }

    public long getPageEnd(){
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

    public String getQueryString(){
        init();
        StringBuilder stringBuilder = new StringBuilder();

        if(searchType != null && searchType.length() > 0){
            stringBuilder.append(String.format("searchType=%s",searchType));
        }

        if(searchValue != null && searchValue.length() > 0){
            if(stringBuilder.length() > 0 ){
                stringBuilder.append("&");
            }
            stringBuilder.append(String.format("searchValue=%s",searchValue));
        }
        return stringBuilder.toString();
    }
}
