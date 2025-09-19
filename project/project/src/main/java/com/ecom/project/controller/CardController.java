package com.ecom.project.controller;

import com.ecom.project.model.Card;
import com.ecom.project.payload.CardDTO;
import com.ecom.project.repository.CardRepository;
import com.ecom.project.service.CardService;
import com.ecom.project.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/carts/products/{productId}/quantity/{quantity}")
    public ResponseEntity<CardDTO>  addProductToCard(@PathVariable Long productId,
                                                     @PathVariable Integer quantity){
        CardDTO cardDTO = cardService.addProductToCard(productId,quantity);
        return  new ResponseEntity<CardDTO>(cardDTO , HttpStatus.CREATED);
    }

    @GetMapping("/cards")
    public ResponseEntity<List<CardDTO>> getCards(){
        List<CardDTO> cardDTOS = cardService.getAllCards();
        return new ResponseEntity<List<CardDTO>>(cardDTOS,HttpStatus.FOUND);
    }

    @GetMapping("/cards/user/card")
    public  ResponseEntity<CardDTO> getCardById(){

        String emailId = authUtil.loggedInEmail();
        Card card = cardRepository.findCardByEmail(emailId);
        Long cardId = card.getCardId();

        CardDTO cardDTO = cardService.getCard(emailId,cardId);
        return  new ResponseEntity<CardDTO>(cardDTO , HttpStatus.OK);
    }
}
