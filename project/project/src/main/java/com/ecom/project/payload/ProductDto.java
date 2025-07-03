package com.ecom.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private  Long productId;
    private  String productName;
    private String productDescription;
    private String image;
    private  Integer quantity;
    private  double prize;
    private  double discount;
    private  double specialPrize;

}
