package com.ecom.project.service;


import com.ecom.project.model.Product;
import com.ecom.project.payload.ProductDto;
import com.ecom.project.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductDto addProduct(Long categoryId, ProductDto productDTO);


    ProductResponse getAllProducts();

    ProductResponse searchCategory(Long categoryId);

    ProductResponse searchProductByKeyword(String keyword);

    ProductDto updateProduct(ProductDto productDTO, Long productId);

    ProductDto deleteProduct(Long productId);

    ProductDto updateProductImage(Long productId, MultipartFile image);
}




