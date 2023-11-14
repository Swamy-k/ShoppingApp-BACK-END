package com.example.ShoppingApp.controller;

import com.example.ShoppingApp.dto.OrderDTO;
import com.example.ShoppingApp.dto.PlaceOrderDTO;
import com.example.ShoppingApp.entity.Login;
import com.example.ShoppingApp.entity.Order;
import com.example.ShoppingApp.service.LoginService;
import com.example.ShoppingApp.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppingApp/orderGateway")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private LoginService loginService;

    //This endpoint gives the current status of the cart and prices
    @GetMapping("/order")
    public ResponseEntity<OrderDTO> getOrders(@RequestParam String apiKey) {
        Login loggedInUser = loginService.isTokenValid(apiKey);
        Long customerId = loggedInUser.getUser().getUserId();
        OrderDTO order = orderService.getOrderStatus(customerId);

        return new ResponseEntity<OrderDTO>(order, HttpStatus.ACCEPTED);

    }

    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@RequestParam String key, @RequestBody @Valid PlaceOrderDTO paymentInfo) {
        Login loggedInUser = loginService.isTokenValid(key);
        Long customerId = loggedInUser.getUser().getUserId();
        Order order = orderService.placeOrder(customerId, paymentInfo);
        return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
    }
}
