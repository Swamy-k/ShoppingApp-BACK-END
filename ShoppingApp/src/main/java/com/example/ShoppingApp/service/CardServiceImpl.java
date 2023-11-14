package com.example.ShoppingApp.service;

import com.example.ShoppingApp.entity.Card;
import com.example.ShoppingApp.exception.CardDetailsAlreadyExistException;
import com.example.ShoppingApp.exception.CardDetailsNotFoundException;
import com.example.ShoppingApp.repository.CardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Card addCard(Card card) {
        Optional<Card> cardDetails = cardRepository.findByCardNumber(card.getCardNumber());
        if (cardDetails.isPresent()) {
            throw new CardDetailsAlreadyExistException("Card details already exists");
        } else {
            return cardRepository.save(card);
        }

    }

    @Override
    public String removeCard(Card card) {
        Optional<Card> cardDetails = cardRepository.findByCardNumber(card.getCardNumber());
        if (cardDetails.isPresent()) {
            cardRepository.delete(card);
            return "card no. " + card.getCardNumber() + " removed successfully";
        } else {
            throw new CardDetailsNotFoundException("Card details not found" + card.getCardNumber());
        }
    }

    @Override
    public Card updateCard(Card card, Card updatedCard) {
        Optional<Card> cardDetails = cardRepository.findByCardNumber(card.getCardNumber());
        if (cardDetails.isPresent()) {
            cardRepository.delete(card);
            return cardRepository.save(updatedCard);
        } else {
            throw new CardDetailsNotFoundException("Card details not found " + card.getCardNumber());
        }
    }
}
