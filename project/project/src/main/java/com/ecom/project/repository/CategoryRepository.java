package com.ecom.project.repository;

import com.ecom.project.model.Category;
import com.ecom.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {


    Category findByCategoryName(String categoryName);

}

