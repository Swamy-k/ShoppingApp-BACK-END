package com.example.ShoppingApp.service;

import com.example.ShoppingApp.dto.OrderDTO;
import com.example.ShoppingApp.dto.PlaceOrderDTO;
import com.example.ShoppingApp.entity.Order;

public interface OrderService {
    public OrderDTO getOrderStatus(Long customerId);

    public Order createOrder(long customerId, String lastFourDigitsOfCardUsed, long addressId);

    public Order addOrder(Order order);

    public Order placeOrder(long customerId, PlaceOrderDTO paymentInfo);
}
