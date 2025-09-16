package com.ecom.project.service;

import com.ecom.project.exceptions.APIException;
import com.ecom.project.exceptions.ResourceNotFoundException;
import com.ecom.project.model.Card;
import com.ecom.project.model.CardItems;
import com.ecom.project.model.Product;
import com.ecom.project.payload.CardDTO;
import com.ecom.project.payload.ProductDto;
import com.ecom.project.repository.CardItemRepository;
import com.ecom.project.repository.CardRepository;
import com.ecom.project.repository.ProductRepository;
import com.ecom.project.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        CardItems cardItems = cardItemRepository.findCardItemsByProductIdAndCardId(
                card.getCardId(),
                productId
        );

        if (cardItems != null){
            throw new APIException("Product"+ product.getProductName() + "Is Already Exist");
        }

        if (product.getQuantity() == 0){
            throw  new APIException(product.getProductName() + " is not Available");
        }

        if (product.getQuantity() < quantity){
            throw  new APIException("please make  on order of the" + product.getProductName()
                    + "Less then or equal to the quantity" + product.getQuantity() +"." );
        }

        CardItems newCardItem = new CardItems();

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

        List<CardItems> cardItemss =  card.getCardItems();

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
}
