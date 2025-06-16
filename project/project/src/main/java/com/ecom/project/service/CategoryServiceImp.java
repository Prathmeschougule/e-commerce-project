package com.ecom.project.service;

import com.ecom.project.exceptions.APIException;
import com.ecom.project.exceptions.ResourceNotFoundException;
import com.ecom.project.model.Category;
import com.ecom.project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CategoryServiceImp implements CategoryService {

//    private  List<Category> categories = new ArrayList<>();
//    private   int  nextID;
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> getAllCategories() {
        List<Category> category = categoryRepository.findAll();
        if (category.isEmpty())
            throw  new APIException("No Category Create Till Now ");
        return category;
    }

    @Override
    public void createCategory(Category category) {

        Category saveCategory= categoryRepository.findByCategoryName(category.getCategoryName());
        if (saveCategory != null)
            throw  new APIException("Category with The Name " + saveCategory.getCategoryName() + "Already Exist!!!!");
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

    @Override
    public Category getByCategoriesId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
        return category;

    }


}
