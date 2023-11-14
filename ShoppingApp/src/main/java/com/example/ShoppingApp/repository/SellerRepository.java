package com.example.ShoppingApp.repository;

import com.example.ShoppingApp.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
     Optional<Seller> findByUserName(String userName);
     Optional<Seller> findByUserPassword(String userPassword);
     Optional<Seller> findByUserNameAndUserPassword(String userName, String password);
}
