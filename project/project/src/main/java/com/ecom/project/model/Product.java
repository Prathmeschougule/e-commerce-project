package com.ecom.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @NotBlank
    @Size(min = 3 , message = "Product Name Must Contain atlest 3 later ")
    private  String productName;

    @NotBlank
    @Size(min = 3 , message = "Product description Must Contain atlest 6 later ")
    private String productDescription;
    private  Integer quantity;
    private String image;
    private  double price;
    private  double discount;
    private  double specialPrize;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
