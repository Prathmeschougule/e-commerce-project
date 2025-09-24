package com.ecom.project.service;

import com.ecom.project.exceptions.APIException;
import com.ecom.project.exceptions.ResourceNotFoundException;
import com.ecom.project.model.Card;
import com.ecom.project.model.CardItem;
import com.ecom.project.model.CardItem;
import com.ecom.project.model.Product;
import com.ecom.project.payload.CardDTO;
import com.ecom.project.payload.ProductDto;
import com.ecom.project.repository.CardItemRepository;
import com.ecom.project.repository.CardRepository;
import com.ecom.project.repository.ProductRepository;
import com.ecom.project.util.AuthUtil;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CardServiceImpl implements   CardService{

    @Autowired
  private   CardRepository cardRepository;

    @Autowired
  private   ProductRepository productRepository;

    @Autowired
  private   CardItemRepository cardItemRepository;

    @Autowired
   private ModelMapper modelMapper;

    @Autowired
  private AuthUtil authUtil;

    @Override
    public CardDTO addProductToCard(Long productId, Integer quantity) {

//        find existing card or create
          Card card = createCard();

//       Retrive product
        Product product = productRepository.findById(productId)
                .orElseThrow( () ->new ResourceNotFoundException("Product","productId",productId));


//      Perform Validation
        CardItem cardItem = cardItemRepository.findCardItemByProductIdAndCardId(
                card.getCardId(),
                productId
        );

        if (cardItem != null){
            throw new APIException("Product"+ product.getProductName() + "Is Already Exist");
        }

        if (product.getQuantity() == 0){
            throw  new APIException(product.getProductName() + " is not Available");
        }

        if (product.getQuantity() < quantity){
            throw  new APIException("please make  on order of the" + product.getProductName()
                    + "Less then or equal to the quantity" + product.getQuantity() +"." );
        }

        CardItem newCardItem = new CardItem();

        newCardItem.setProduct(product);
        newCardItem.setCard(card);
        newCardItem.setQuantity(quantity);
        newCardItem.setDiscount(product.getDiscount());
        newCardItem.setProductPrice(product.getSpecialPrize());

        card.getCardItems().add(newCardItem);

        cardItemRepository.save(newCardItem);

        product.setQuantity(product.getQuantity());

        card.setTotalPrice(card.getTotalPrice() + product.getSpecialPrize() * quantity );

        cardRepository.save(card);

        CardDTO cardDTO = modelMapper.map(card,CardDTO.class);

        List<CardItem> cardItemss =  card.getCardItems();

        Stream<ProductDto> productStream = cardItemss.stream()
                .map(item ->{
                    ProductDto map =  modelMapper.map(item.getProduct(), ProductDto.class);
                    map.setQuantity(item.getQuantity());
                    return map;
                });

        cardDTO.setProduct(productStream.toList());

        return cardDTO;
    }


    private Card createCard(){
        Card  userCard = cardRepository.findCardByEmail(authUtil.loggedInEmail());
        if(userCard != null){
            return  userCard;
        }

        Card  card = new Card();
        card.setTotalPrice(0.00);
        card.setUser(authUtil.loggedInUser());
        Card newCard = cardRepository.save(card);

        return  newCard;
    }

    @Override
    public List<CardDTO> getAllCards() {
     List<Card> cards = cardRepository.findAll();

     if (cards.isEmpty()){
         throw new APIException("No Card Exist ");
     }

     List<CardDTO> cardDTOS= cards.stream()

             .map(card -> {
                 CardDTO cardDTO= modelMapper.map(card,CardDTO.class);
                 List<ProductDto> products = card.getCardItems().stream()
                         .map(p -> modelMapper.map(p.getProduct(),ProductDto.class))
                         .collect(Collectors.toList());

                 cardDTO.setProduct(products);

                 return cardDTO;
             }).collect(Collectors.toList());

     return cardDTOS;
    }

    @Override
    public CardDTO getCard(String emailId, Long cardId) {

        Card card = cardRepository.findCardByEmailAndCardId(emailId,cardId);

        if (card ==  null){
            throw  new ResourceNotFoundException("Card","cardId",cardId);
        }

        CardDTO cardDTO = modelMapper.map(card, CardDTO.class);

        card.getCardItems().forEach(c -> c.getProduct().setQuantity(c.getQuantity()));

        List<ProductDto> products =  card.getCardItems().stream()
                .map( p -> modelMapper.map(p.getProduct(),ProductDto.class))
                .toList();

        cardDTO.setProduct(products);

         return  cardDTO;
    }

    @Transactional
    @Override
    public CardDTO updateCardQuantityInCard(Long productId, Integer quantity) {

        String email = authUtil.loggedInEmail();
        Card userCard = cardRepository.findCardByEmail(email);
        Long cardId = userCard.getCardId();

        Card card = cardRepository.findById(cardId).
                orElseThrow(() -> new ResourceNotFoundException("Card","cardId",cardId));


        Product product = productRepository.findById(productId).
                orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));

        if (product.getQuantity() == 0){
            throw  new APIException(product.getProductName() + " is not Available");
        }

        if (product.getQuantity() < quantity){
            throw  new APIException("please make  on order of the" + product.getProductName()
                    + "Less then or equal to the quantity" + product.getQuantity() +"." );
        }


        CardItem cardItem = cardItemRepository.findCardItemByProductIdAndCardId(productId,cardId);

        if (cardItem == null){
            throw new APIException("Product" + product.getProductName() + "Not Avilable ");
        }

        cardItem.setProductPrice(product.getSpecialPrize());
        cardItem.setQuantity(cardItem.getQuantity() + quantity);
        cardItem.setDiscount(product.getDiscount());
        card.setTotalPrice(card.getTotalPrice() + (cardItem.getProductPrice() * quantity));

        cardRepository.save(card);

        CardItem updatedItem = cardItemRepository.save(cardItem);
        if (updatedItem.getQuantity() == 0){
            cardItemRepository.deleteById(updatedItem.getCardItemId());
        }

        CardDTO cardDTO = modelMapper.map(card,CardDTO.class);
        List<CardItem> cardItems1 = card.getCardItems();

        Stream<ProductDto> productStream  = cardItems1.stream().map(
                item -> {
                    ProductDto prd = modelMapper.map(item.getProduct(),ProductDto.class);
                    prd.setQuantity(item.getQuantity());
                    return prd;
                }
        );

        cardDTO.setProduct(productStream.toList());

        return cardDTO;
    }

    @Override
    public String deleteProductFromCard(Long cardId, Long productId) {

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card","cardId",cardId));

        CardItem cardItem = cardItemRepository.findCardItemByProductIdAndCardId(cardId,productId);

        if (cardItem == null){
            throw new ResourceNotFoundException("Product" , "productId" , productId);
        }

        card.setTotalPrice((card.getTotalPrice() - (cardItem.getProductPrice() * cardItem.getQuantity())));

        cardItemRepository.deleteCardItemByProductIdAndCardId(cardId,productId);

        return "Product" + cardItem.getProduct().getProductName() + "Removed from the card!!!";
    }

}
