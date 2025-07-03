package com.ecom.project.controller;

import com.ecom.project.model.Product;
import com.ecom.project.payload.ProductDto;
import com.ecom.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDto> addProduct(@RequestBody  Product product ,
                                                 @PathVariable Long categoryId){
        
         ProductDto productDto = productService.addProduct(categoryId,product);

         return  new ResponseEntity<>(productDto, HttpStatus.CREATED);

    }
}
