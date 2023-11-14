package com.example.ShoppingApp.service;

import com.example.ShoppingApp.entity.Card;

public interface CardService {
     Card addCard(Card card);

     String removeCard(Card card);

     Card updateCard(Card card, Card updatedCard);
}
