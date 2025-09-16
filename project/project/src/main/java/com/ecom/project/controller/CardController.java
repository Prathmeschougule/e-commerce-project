package com.ecom.project.controller;

import com.ecom.project.payload.CardDTO;
import com.ecom.project.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CardDTO>  addProductToCard(@PathVariable Long productId,
                                                     @PathVariable Integer quantity){
        CardDTO cardDTO = cardService.addProductToCard(productId,quantity);
        return  new ResponseEntity<CardDTO>(cardDTO , HttpStatus.CREATED);
    }
}
