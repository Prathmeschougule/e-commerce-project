package com.ecom.project.service;
import com.ecom.project.model.Category;
import com.ecom.project.payload.CategoryDto;
import com.ecom.project.payload.CategoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {
   CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize);
   CategoryDto createCategory(CategoryDto categoryDto);
   CategoryDto deleteCategory(Long categoryId);
   CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
   CategoryDto getByCategoriesId(Long categoryId);
}