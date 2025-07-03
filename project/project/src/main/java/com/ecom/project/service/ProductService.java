package com.ecom.project.service;


import com.ecom.project.model.Product;
import com.ecom.project.payload.ProductDto;

public interface ProductService {
    ProductDto addProduct(Long categoryId, Product product);
}

