package com.ecom.project.controller;

import com.ecom.project.model.Product;
import com.ecom.project.payload.ProductDto;
import com.ecom.project.payload.ProductResponse;
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

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(){
        ProductResponse productResponse = productService.getAllProducts();
        return  new  ResponseEntity<>(productResponse,HttpStatus.OK);

    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> searchByCategory(@PathVariable Long  categoryId){
         ProductResponse productResponse = productService.searchCategory(categoryId);

          return  new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/product/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductByKeyWord( @PathVariable String keyword){
        ProductResponse productResponse = productService.searchProductByKeyword( '%' +keyword + '%');
        return  new ResponseEntity<>(productResponse,HttpStatus.FOUND);

    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody Product product , @PathVariable Long productId){

        ProductDto productedProductDto = productService.updateProduct(product,productId);

        return  new ResponseEntity<>(productedProductDto,HttpStatus.OK);
    }

}
