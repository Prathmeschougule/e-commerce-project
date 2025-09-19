package com.ecom.project.service;

import com.ecom.project.payload.CardDTO;

import java.util.List;

public interface CardService {
    CardDTO addProductToCard(Long productId, Integer quantity);

    List<CardDTO> getAllCards();

    CardDTO getCard(String emailId, Long cardId);
}
