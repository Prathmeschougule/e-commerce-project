package com.ecom.project.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "product_Id")
    private  Product product;

    @ManyToOne
    @JoinColumn(name="order_Id")
    private Order order;

    private Integer quantity;

    private double discount;

    private  double orderedProductPrice;

}
