package com.zerobase.fastlms.admin.service.impl;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.entity.Category;
import com.zerobase.fastlms.admin.model.CategoryModifyInput;
import com.zerobase.fastlms.admin.repository.CategoryRepository;
import com.zerobase.fastlms.admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private Sort getSortBySortValueDesc(){
        return Sort.by(Sort.Direction.DESC, "sortValue");
    }

    @Override
    public List<CategoryDto> list() {
        return CategoryDto.of(
                categoryRepository.findAllByOrderBySortValueDesc()
        );
    }

    @Override
    public boolean add(String categoryName) {

        // 카테고리명이 중복인지 체크

        Category category = Category.builder()
                .categoryName(categoryName)
                .usingYn(true)
                .sortValue(0)
                .build();

        categoryRepository.save(category);

        return true;
    }

    @Override
    public boolean update(CategoryModifyInput parameter) {
        Optional<Category> optionalCategory = categoryRepository.findById(parameter.getId());

        if(!optionalCategory.isPresent()){
            return false;
        }

        Category category = optionalCategory.get();
        category.setCategoryName(parameter.getCategoryName());
        category.setSortValue(parameter.getSortValue());
        category.setUsingYn(parameter.isUsingYn());

        categoryRepository.save(category);

        return true;
    }

    @Override
    public boolean delete(long id) {
        categoryRepository.deleteById(id);
        return true;
    }
}
