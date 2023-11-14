package com.example.ShoppingApp.service;

import com.example.ShoppingApp.entity.Cart;
import com.example.ShoppingApp.entity.Customer;
import com.example.ShoppingApp.entity.Item;

import java.util.List;

public interface CartService {
    Cart saveCart(Customer customer, Item item);

    List<Item> getAllItem(Cart cart);

    List<Item> sendToOrder(long customerId);

}
