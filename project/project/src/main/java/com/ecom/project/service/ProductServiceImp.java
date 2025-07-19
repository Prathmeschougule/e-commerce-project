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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;
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
    public ProductDto addProduct(Long categoryId, ProductDto productDTO) {

        Category category = categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));

        Product product = modelMapper.map(productDTO,Product.class);

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
    public ProductDto updateProduct(ProductDto productDTO, Long productId) {

//         get the Existing Product  from the DB

        Product productFromDb= productRepository.findById(productId).
                orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));

        Product product = modelMapper.map(productFromDb,Product.class);

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

    @Override
    public ProductDto deleteProduct(Long productId) {
//       get the product from the db
        Product product = productRepository.findById(productId).
                orElseThrow(()-> new ResourceNotFoundException("ProductDto","productId",productId));
//       then delete the product
        productRepository.delete(product);
//        return
        return  modelMapper.map(product,ProductDto.class);
    }

    @Override
    public ProductDto updateProductImage(Long productId, MultipartFile image) {

//        get the Product from the DB
       Product productFromDB = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));

//        upload image to the server
//        get the file name of the upload image
        String path = "/image";
        String filename =  uploadImage(path, image);

//        Updating to the new file name to the project
        productFromDB.setImage(filename);

//        save updated product
        Product  updateProduct = productRepository.save(productFromDB);

//      Return the DTO after Mapping product to DTO
        return modelMapper.map(updateProduct,ProductDto.class);
    }

    private String uploadImage(String path, MultipartFile file) {

//        File Name Of Current / Original file
        String originalFileName = file.getName();

//        Generate a unique file name
        String randomId = UUID.randomUUID().toString();
        String filename = randomId.concat(originalFileName.substring(originalFileName.lastIndexOf('.')));
        String filePath = path + File.pathSeparator + filename;

    }


}
