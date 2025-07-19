package com.ecom.project.service;


import com.ecom.project.exceptions.ResourceNotFoundException;
import com.ecom.project.model.Category;
import com.ecom.project.model.Product;
import com.ecom.project.payload.ProductDto;
import com.ecom.project.payload.ProductResponse;
import com.ecom.project.repository.CategoryRepository;
import com.ecom.project.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

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

        double specialPrice =   product.getPrice() -(product.getDiscount() * 0.01) * product.getPrice();
        product.setSpecialPrize(specialPrice);

        Product saveProduct = productRepository.save(product);

        return modelMapper.map(saveProduct,ProductDto.class);
    }

    @Override
    public ProductResponse getAllProducts() {

       List<Product> products = productRepository.findAll();


       List<ProductDto> productDto = products.stream()
               .map(product ->modelMapper.map(product,ProductDto.class))
               .collect(Collectors.toList());


       ProductResponse productResponse = new ProductResponse();
       productResponse.setContent(productDto);

       return  productResponse;
    }

    @Override
    public ProductResponse searchCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("category","categoryId",categoryId));

        List<Product> products =  productRepository.findByCategoryOrderByPriceAsc(category);

        List<ProductDto> productDtos = products.stream()
                .map(product -> modelMapper.map(product,ProductDto.class))
                .collect(Collectors.toList());

        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(productDtos);

        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {

        List<Product> products =  productRepository.findByProductNameLikeIgnoreCase(keyword);

        List<ProductDto> productDTOS = products.stream()
                .map(product -> modelMapper.map(product,ProductDto.class))
                .toList();

        ProductResponse productResponse = new ProductResponse();
            productResponse.setContent(productDTOS);
            return productResponse;

    }

    @Override
    public ProductDto updateProduct(Product product, Long productId) {

//         get the Existing Product  from the DB

        Product productFromDb= productRepository.findById(productId).
                orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));

//        update the product  from the request body

        productFromDb.setProductName(product.getProductName());
        productFromDb.setProductDescription(product.getProductDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setSpecialPrize(product.getSpecialPrize());

//      save product
        Product saveProduct = productRepository.save(productFromDb);

//      return product dto

        return  modelMapper.map(saveProduct,ProductDto.class);


    }


}
