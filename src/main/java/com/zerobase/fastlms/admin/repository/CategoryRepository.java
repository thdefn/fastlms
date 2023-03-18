package com.zerobase.fastlms.admin.repository;

import com.zerobase.fastlms.admin.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByOrderBySortValueDesc();
}
