package com.ecom.project.repository;

import com.ecom.project.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {

    @Query("SELECT c FROM Card c WHERE c.user.email = ?1")
    Card findCardByEmail(String email);

    @Query("SELECT c FROM Card c WHERE c.user.email = ?1 AND c.id = ?2")
    Card findCardByEmailAndCardId(String emailId, Long cardId);

    @Query("SELECT c FROM Card c JOIN FETCH c.cartItems ci JOIN FETCH ci.product p WHERE p.id = ?1")
    List<Card> findCardsByProductId(Long productId);
}

