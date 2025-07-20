package com.ecom.project.controller;

import com.ecom.project.payload.ProductDto;
import com.ecom.project.payload.ProductResponse;
import com.ecom.project.service.ProductService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDto> addProduct(@RequestBody  ProductDto productDTO ,
                                                 @PathVariable Long categoryId){
        
         ProductDto productDto = productService.addProduct(categoryId,productDTO);

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
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDTO , @PathVariable Long productId){

        ProductDto productedProductDto = productService.updateProduct(productDTO,productId);

        return  new ResponseEntity<>(productedProductDto,HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long productId){
       ProductDto deletedProduct = productService.deleteProduct(productId);
       return new ResponseEntity<>(deletedProduct,HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDto>updateProductImage(@PathVariable Long productId,
                                                        @RequestParam("image")MultipartFile image) throws IOException {

        ProductDto updatedProduct = productService.updateProductImage(productId,image);
        return  new ResponseEntity<>(updatedProduct,HttpStatus.OK);

    }

}
