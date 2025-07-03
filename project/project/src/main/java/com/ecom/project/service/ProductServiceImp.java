package com.ecom.project.service;

import com.ecom.project.exceptions.ResourceNotFoundException;
import com.ecom.project.model.Category;
import com.ecom.project.model.Product;
import com.ecom.project.payload.ProductDto;
import com.ecom.project.repository.CategoryRepository;
import com.ecom.project.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
     private   CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProductDto addProduct(Long categoryId, Product product) {

        Category category = categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));

        product.setImage("default.png");
        product.setCategory(category);

        double specialPrice =   product.getPrize() -(product.getDiscount() * 0.01) * product.getPrize();
        product.setSpecialPrize(specialPrice);

        Product saveProduct = productRepository.save(product);

        return modelMapper.map(saveProduct,ProductDto.class);
    }
}
