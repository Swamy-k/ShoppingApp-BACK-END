package com.example.ShoppingApp.controller;

import com.example.ShoppingApp.constants.ProductCategory;
import com.example.ShoppingApp.entity.Product;
import com.example.ShoppingApp.service.ProductService;
import com.example.ShoppingApp.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shoppingApp/productGateway")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SellerService sellerService;
    
    @GetMapping(value = "/allProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> allProducts = productService.getAllProducts();
        return new ResponseEntity<List<Product>>(allProducts, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/category/{category}")
    public ResponseEntity<List<Product>> getAllProductsByCategory(@PathVariable("category") ProductCategory category) {
        List<Product> productsCategoryList = productService.getProductsByCategory(category);
        return new ResponseEntity<List<Product>>(productsCategoryList, HttpStatus.ACCEPTED);
    }

}
