package com.ecom.project.repository;

import com.ecom.project.model.CardItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardItemRepository extends JpaRepository<CardItems,Long> {


    @Query("SELECT ci FROM CardItems ci WHERE ci.card.cardId = ?1 AND ci.product.id = ?2")
    CardItems findCardItemsByProductIdAndCardId(Long cardId, Long productId);

}
