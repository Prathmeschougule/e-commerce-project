package com.ecom.project.service;


import com.ecom.project.exceptions.APIException;
import com.ecom.project.exceptions.ResourceNotFoundException;
import com.ecom.project.model.Category;
import com.ecom.project.model.Product;
import com.ecom.project.payload.ProductDto;
import com.ecom.project.payload.ProductResponse;
import com.ecom.project.repository.CategoryRepository;
import com.ecom.project.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private  String path;


    @Override
    public ProductDto addProduct(Long categoryId, ProductDto productDTO) {
// Check  if product already  present or not
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));

        Product product = modelMapper.map(productDTO,Product.class);

   // Check  if product already  present or not

        boolean isProductNotPresent = true;

        List<Product> products = category.getProducts();

        for (int i = 0; i < products.size(); i++) {

            if (products.get(i).getProductName().equals(productDTO.getProductName())){
                isProductNotPresent = false;
                break;
            }

        }

        if (isProductNotPresent){
            product.setImage("default.png");
            product.setCategory(category);

            double specialPrice =   product.getPrice() -(product.getDiscount() * 0.01) * product.getPrice();
            product.setSpecialPrize(specialPrice);

            Product saveProduct = productRepository.save(product);

            return modelMapper.map(saveProduct,ProductDto.class);

        }else {

            throw  new APIException("Product is already exist !!");
        }

    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageableDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> productPage = productRepository.findAll(pageableDetails);


       List<Product> products = productPage.getContent();

       if (products.isEmpty()){
           throw new APIException("No Product Till Now");
       }

       List<ProductDto> productDto = products.stream()
               .map(product ->modelMapper.map(product,ProductDto.class))
               .collect(Collectors.toList());

       if(products.isEmpty()){
           throw new APIException("No product exist !!");
       }

       ProductResponse productResponse = new ProductResponse();

       productResponse.setContent(productDto);

       productResponse.setPageNumber(productPage.getNumber());
       productResponse.setPageSize(productPage.getSize());
       productResponse.setTotalPage(productPage.getTotalPages());
       productResponse.setTotalElements(productPage.getTotalElements());
       productResponse.setLastPage(productPage.isLast());

       return  productResponse;
    }

    @Override
    public ProductResponse searchCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("category","categoryId",categoryId));

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageableDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(category , pageableDetails);


        List<Product> products = productPage.getContent();

        List<ProductDto> productDtos = products.stream()
                .map(product -> modelMapper.map(product,ProductDto.class))
                .collect(Collectors.toList());

        if (products.isEmpty()){
            throw new APIException(category.getCategoryName()+  " Category Dose Not Exist !!");

        }

        ProductResponse productResponse = new ProductResponse();

        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());

        productResponse.setContent(productDtos);

        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {


        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageableDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase(keyword, pageableDetails);


        List<Product> products = productPage.getContent();
        List<ProductDto> productDTOS = products.stream()
                .map(product -> modelMapper.map(product,ProductDto.class))
                .toList();

        if (products.isEmpty()){
            throw  new APIException("Product Not Found With This Keyword :" + keyword  );
        }

        ProductResponse productResponse = new ProductResponse();
            productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalPage(productResponse.getTotalPage());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());

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
    public ProductDto updateProductImage(Long productId, MultipartFile image) throws IOException {

//        get the Product from the DB
       Product productFromDB = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));

//        upload image to the server
//        get the file name of the upload image
//        String path = "project/images";
        String filename = fileService.uploadImage(path, image);

//        Updating to the new file name to the project
        productFromDB.setImage(filename);

//        save updated product
        Product  updateProduct = productRepository.save(productFromDB);

//      Return the DTO after Mapping product to DTO
        return modelMapper.map(updateProduct,ProductDto.class);
    }




}
