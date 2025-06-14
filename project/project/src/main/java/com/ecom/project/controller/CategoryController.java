package com.ecom.project.controller;
import com.ecom.project.model.Category;
import com.ecom.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

//    public CategoryController(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<String> createCategories( @Valid @RequestBody Category Category){
         categoryService.createCategory(Category);
         return new ResponseEntity<>("Add Successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable  Long categoryId){

        try {
            String status =  categoryService.deleteCategory(categoryId);
            return  new ResponseEntity<>(status, HttpStatus.OK);
        }catch (ResponseStatusException e){
            return  new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<String>updateCategory(@RequestBody Category category,@PathVariable Long categoryId){
        try {
            Category saveCategory = categoryService.updateCategory(category,categoryId);

            return new ResponseEntity<>("Update Category Id " + categoryId ,HttpStatus.CREATED);

        }catch (ResponseStatusException e){

            return  new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }


    }

}
