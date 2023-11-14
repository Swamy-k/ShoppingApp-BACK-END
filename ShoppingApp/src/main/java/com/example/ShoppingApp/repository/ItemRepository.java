package com.example.ShoppingApp.repository;

import com.example.ShoppingApp.entity.Item;
import com.example.ShoppingApp.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
