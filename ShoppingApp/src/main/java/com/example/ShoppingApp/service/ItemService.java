package com.example.ShoppingApp.service;

import com.example.ShoppingApp.entity.Item;
import com.example.ShoppingApp.entity.Product;

public interface ItemService {
    Item addItem(Item item);

    String removeItem(Item item);

    Item addItem(Product product, int quantity);
}
