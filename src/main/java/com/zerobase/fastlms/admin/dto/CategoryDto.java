package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.Category;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoryDto {
    Long id;
    String categoryName;
    int sortValue;
    boolean usingYn;

    public static List<CategoryDto> of(List<Category> categories){
        if(categories != null){
            List<CategoryDto> dtos = new ArrayList<>();
            for(Category x : categories){
                dtos.add(of(x));
            }
            return dtos;
        }
        return null;
    }

    public static CategoryDto of(Category category){
        return CategoryDto.builder()
                .categoryName(category.getCategoryName())
                .id(category.getId())
                .sortValue(category.getSortValue())
                .usingYn(category.isUsingYn())
                .build();
    }
}
