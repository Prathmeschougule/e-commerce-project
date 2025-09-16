package com.ecom.project.repository;

import com.ecom.project.model.CardItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardItemRepository extends JpaRepository<CardItems,Long> {
}
