package com.example.ShoppingApp.repository;

import com.example.ShoppingApp.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {
     Optional<Card> findByCardNumber(String string);
}
