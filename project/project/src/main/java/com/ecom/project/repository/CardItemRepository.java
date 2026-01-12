package com.ecom.project.repository;

import com.ecom.project.model.CardItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardItemRepository extends JpaRepository<CardItem,Long> {

    @Modifying
    @Query("DELETE  FROM CardItem ci WHERE ci.card.id = ?1 AND ci.product.id = ?2")
    void deleteCardItemByProductIdAndCardId(Long cardId, Long productId);

//    @Modifying
//    @Query("DELETE FROM CardItem ci WHERE ci.card.id = ?1 AND ci.product.id = ?2")
//    CardItem findCardItemsByProductIdAndCardId(Long cardId, Long productId);

    @Query("SELECT ci FROM CardItem ci WHERE ci.card.id = ?1 AND ci.product.id = ?2")
    CardItem findCardItemByProductIdAndCardId(  Long cardId,Long productId);

}
