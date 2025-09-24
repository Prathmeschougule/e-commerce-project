package com.ecom.project.model;

import jakarta.persistence.*;
import jakarta.websocket.OnError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "cardItem")
@NoArgsConstructor
@AllArgsConstructor
public class CardItem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardItemId;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private  Card card;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    private Integer quantity;
    private Double discount;
    private double productPrice;

}
