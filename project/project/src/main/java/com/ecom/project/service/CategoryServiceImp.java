package com.ecom.project.service;

import com.ecom.project.exceptions.ResourceNotFoundException;
import com.ecom.project.model.Category;
import com.ecom.project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;


@Service
public class CategoryServiceImp implements CategoryService {

//    private  List<Category> categories = new ArrayList<>();
//    private   int  nextID;
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
        categoryRepository.delete(category);
        return "Category With Category ID" +categoryId + "Delete Successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
          Category existingCategory = categoryRepository.findById(categoryId)
                  .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));

          existingCategory.setCategoryName(category.getCategoryName());

          Category saveCategory = existingCategory ;
          categoryRepository.save(saveCategory);

          return saveCategory;
    }


}
