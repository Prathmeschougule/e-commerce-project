package com.ecom.project.service;


import com.ecom.project.payload.ProductDto;
import com.ecom.project.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    ProductDto addProduct(Long categoryId, ProductDto productDTO);


    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse searchCategory(Long categoryId);

    ProductResponse searchProductByKeyword(String keyword);

    ProductDto updateProduct(ProductDto productDTO, Long productId);

    ProductDto deleteProduct(Long productId);

    ProductDto updateProductImage(Long productId, MultipartFile image) throws IOException;
}




