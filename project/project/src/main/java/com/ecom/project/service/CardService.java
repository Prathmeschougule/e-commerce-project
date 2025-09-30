package com.ecom.project.service;

import com.ecom.project.payload.CardDTO;
import com.ecom.project.payload.ProductDto;
import jakarta.transaction.Transactional;

import java.util.List;

public interface CardService {
    CardDTO addProductToCard(Long productId, Integer quantity);

    List<CardDTO> getAllCards();

    CardDTO getCard(String emailId, Long cardId);

    @Transactional
    CardDTO updateCardQuantityInCard(Long productId, Integer quantity);

    String deleteProductFromCard(Long cardId, Long productId);

    ProductDto updateProductInCards(Long cardId, Long productId);
}
