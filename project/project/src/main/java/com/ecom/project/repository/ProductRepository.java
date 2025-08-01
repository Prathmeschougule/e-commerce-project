package com.ecom.project.repository;

import com.ecom.project.model.Category;
import com.ecom.project.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageableDetails);

    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageableDetails);
}



