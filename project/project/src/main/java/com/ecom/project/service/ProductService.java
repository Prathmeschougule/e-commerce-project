package com.ecom.project.service;


import com.ecom.project.model.Product;
import com.ecom.project.payload.ProductDto;
import com.ecom.project.payload.ProductResponse;

public interface ProductService {
    ProductDto addProduct(Long categoryId, Product product);


    ProductResponse getAllProducts();

    ProductResponse searchCategory(Long categoryId);

    ProductResponse searchProductByKeyword(String keyword);

    ProductDto updateProduct(Product product, Long productId);
}


