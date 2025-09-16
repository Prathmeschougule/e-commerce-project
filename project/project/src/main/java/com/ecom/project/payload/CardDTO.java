package com.ecom.project.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

    private  Long CardId;
    private Double totalPrice =  0.0;
    private List<ProductDto> product = new ArrayList<>();
}
