package com.ecom.project.repository;

import com.ecom.project.model.Category;
import com.ecom.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategoryOrderByPriceAsc(Category category);
    List<Product> findByProductName(String keyword);

    List<Product> findByProductNameLikeIgnoreCase(String keyword);
}



