package com.ecom.project.model;

import jakarta.persistence.*;
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

    private  String productName;
    private String productDescription;
    private  Integer quantity;
    private String image;
    private  double prize;
    private  double discount;
    private  double specialPrize;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
