package com.ecom.project.service;

import com.ecom.project.payload.CardDTO;

public interface CardService {
    CardDTO addProductToCard(Long productId, Integer quantity);
}
