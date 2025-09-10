package com.ecom.project.repository;

import com.ecom.project.model.Product;
import com.ecom.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User,Long> {
//    Optional<User> findByName(String username);
    Boolean existsByUserName(String username);
    Optional<User> findByUserName(String username);
    Boolean existsByEmail(String email);
}
