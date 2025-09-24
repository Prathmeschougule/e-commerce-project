package com.ecom.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user ;

    @OneToMany(mappedBy = "card" , cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE},orphanRemoval = true)
    private List<CardItem> cardItems = new ArrayList<>();

    private Double totalPrice = 0.0;
}
