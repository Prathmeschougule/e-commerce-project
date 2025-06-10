package com.ecom.project.service;

import com.ecom.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    private  List<Category> categories = new ArrayList<>();
    private   int  nextID;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextID++);
        categories.add(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category =  categories.stream()
                        .filter(c -> c.getCategoryId().equals(categoryId))
                        .findFirst()
                        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "Resource Not Found "));

        categories.remove(category);

        return  "Category Id " +  categoryId + " " + "Delete Successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
       Optional<Category> OptionalCategory =  categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst();
       if(OptionalCategory.isPresent()){
           Category existingCategory = OptionalCategory.get();
           existingCategory.setCategoryName(category.getCategoryName());
           return  existingCategory;

       } else {
           throw  new ResponseStatusException(HttpStatus.NOT_FOUND , "Resource Not Found ");
       }

    }


}
