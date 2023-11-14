package com.example.ShoppingApp.controller;


import com.example.ShoppingApp.dto.ItemDTO;
import com.example.ShoppingApp.entity.*;
import com.example.ShoppingApp.exception.EmptyCartException;
import com.example.ShoppingApp.exception.ProductNotFoundException;
import com.example.ShoppingApp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shoppingApp/customerGateway")
public class CartController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CartService cartService;

    @Autowired
    private LoginService loginService;

    @PostMapping(value = "/cart")
    public ResponseEntity<Cart> addToCart(@RequestParam String apiKey, @RequestBody ItemDTO item) {

        Login loggedUser = loginService.isTokenValid(apiKey);

        Customer customer = customerService.getCustomerById(loggedUser.getUser().getUserId());
        Long productId = item.getProductId();

        Optional<Product> optProduct = Optional.ofNullable(productService.getProductById(productId));

        if (optProduct.isPresent()) {
            Product product = optProduct.get();
            Item savedItem = itemService.addItem(product, item.getRequiredQuantity());
            Cart savedCart = cartService.saveCart(customer, savedItem);
            return new ResponseEntity<Cart>(savedCart, HttpStatus.ACCEPTED);
        } else {
            throw new ProductNotFoundException("Product with this Id does not exist");
        }
    }

    @GetMapping(value = "/cart/view")
    public ResponseEntity<List<Item>> viewCart(@RequestParam String apiKey) {

        Login loggedUser = loginService.isTokenValid(apiKey);
        Customer customer = customerService.getCustomerById(loggedUser.getUser().getUserId());

        List<Item> items = cartService.getAllItem(customer.getCart());
        if (items.isEmpty()) {
            throw new EmptyCartException("No items found in the carts");
        } else {
            return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
        }
    }
    
}

