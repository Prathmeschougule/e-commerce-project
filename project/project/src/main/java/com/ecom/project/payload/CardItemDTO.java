package com.ecom.project.payload;


import com.ecom.project.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Normalized;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardItemDTO {

    private Long cardItemId;
    private CardDTO card;
    private ProductDto  product;

    private Integer quantity;
    private Double discount;
    private double productPrice;
}
