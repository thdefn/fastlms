package com.zerobase.fastlms.admin.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryModifyInput {
    String categoryName;
    long id;
    int sortValue;
    boolean usingYn;
}
