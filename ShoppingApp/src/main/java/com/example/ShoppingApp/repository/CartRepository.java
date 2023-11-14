package com.example.ShoppingApp.repository;

import com.example.ShoppingApp.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByCartId(Long cartId);
}
