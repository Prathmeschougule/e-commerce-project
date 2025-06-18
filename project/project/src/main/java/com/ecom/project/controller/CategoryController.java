package com.ecom.project.controller;
import com.ecom.project.model.Category;
import com.ecom.project.payload.CategoryDto;
import com.ecom.project.payload.CategoryResponse;
import com.ecom.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name = "pageNumber") Integer pageNumber, @RequestParam(name = "pageSize" ) Integer  pageSize){
         CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber,pageSize);
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDto> getByCategoriesId(@PathVariable Long categoryId){
        CategoryDto categories = categoryService.getByCategoriesId(categoryId);
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDto> createCategories(@Valid @RequestBody CategoryDto  categoryDto){
          CategoryDto savedCategoryDto = categoryService.createCategory(categoryDto);
         return new ResponseEntity<>(savedCategoryDto,HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable  Long categoryId){
            CategoryDto deleteObject =  categoryService.deleteCategory(categoryId);
            return  new ResponseEntity<>(deleteObject, HttpStatus.OK);

    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDto>updateCategory( @Valid @RequestBody CategoryDto categoryDto,@PathVariable Long categoryId){
            CategoryDto updateCategory = categoryService.updateCategory(categoryDto,categoryId);
            return new ResponseEntity<>(updateCategory ,HttpStatus.CREATED);
    }

}
