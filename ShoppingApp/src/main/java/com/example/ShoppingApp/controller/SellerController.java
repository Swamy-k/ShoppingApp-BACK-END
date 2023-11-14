package com.example.ShoppingApp.controller;

import com.example.ShoppingApp.dto.ProductDTO;
import com.example.ShoppingApp.dto.UserDTO;
import com.example.ShoppingApp.entity.Address;
import com.example.ShoppingApp.entity.Login;
import com.example.ShoppingApp.entity.Product;
import com.example.ShoppingApp.entity.Seller;
import com.example.ShoppingApp.service.LoginService;
import com.example.ShoppingApp.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingApp/sellerGateway")
public class SellerController {
    @Autowired
    private SellerService sellerService;

    @Autowired
    private LoginService loginService;

    // Adds new seller
    @PostMapping(value = "/seller")
    public ResponseEntity<Seller> addSeller(@RequestBody @Valid Seller seller) {
        Seller addedSeller = sellerService.addSeller(seller);
        return new ResponseEntity<Seller>(addedSeller, HttpStatus.CREATED);
    }

    // Updates the fields provided in the userInfo (any field except userId can be updated)
    @PutMapping(value = "/seller")
    public ResponseEntity<Seller> updateCustomer(@RequestBody @Valid UserDTO userDTO, @RequestParam String key) {
        Login currentLogin = loginService.isTokenValid(key);
        Seller updatedSeller = sellerService.updateSeller(userDTO, currentLogin.getUser().getUserId());
        return new ResponseEntity<Seller>(updatedSeller, HttpStatus.OK);
    }


    //  Deletes seller
    @DeleteMapping(value = "/seller")
    public ResponseEntity<String> removeSeller(@RequestBody @Valid UserDTO userInfo, Integer sellerId) {
        String deleteSeller = sellerService.removeSeller(userInfo);
        return new ResponseEntity<String>(deleteSeller, HttpStatus.ACCEPTED);
    }


    // View all seller
    @GetMapping(value = "/seller")
    public ResponseEntity<List<Seller>> viewSellers() {
        List<Seller> sellerList = sellerService.getAllSellers();
        return new ResponseEntity<List<Seller>>(sellerList, HttpStatus.FOUND);
    }

    //Adds product
    @PostMapping(value = "/seller/addProduct")
    public ResponseEntity<Seller> addProduct(@RequestParam String key, @RequestBody @Valid Product product) {
        Login currentLogin = loginService.isTokenValid(key);
        Seller addedProduct = sellerService.addProducts(currentLogin.getUser().getUserId(), product);
        return new ResponseEntity<Seller>(addedProduct, HttpStatus.CREATED);
    }


    //  Adds a new address to the address list of the seller
    @PostMapping(value = "/seller/addAddress")
    public ResponseEntity<Seller> addSellerAddress(@RequestParam String key, @Valid @RequestBody Address address) {
        Login currentLogin = loginService.isTokenValid(key);
        Seller seller = sellerService.addSellerAddress(currentLogin.getUser().getUserId(), address);
        return new ResponseEntity<Seller>(seller, HttpStatus.CREATED);
    }

    // Adds a new address to the address list of the seller
    @DeleteMapping(value = "/seller/removeAddress")
    public ResponseEntity<Seller> removeSellerAddress(@RequestParam String key, @RequestParam Long addressId) {
        Login currentLogin = loginService.isTokenValid(key);
        Seller seller = sellerService.removeSellerAddress(currentLogin.getUser().getUserId(), addressId);
        return new ResponseEntity<Seller>(seller, HttpStatus.OK);
    }

    //  updates product status
    @PutMapping(value = "/seller/updateProductStatus")
    public ResponseEntity<Seller> updateProductStatus(@RequestParam String key, @RequestParam Long productId) {
        Login currentLogin = loginService.isTokenValid(key);
        Seller addedProduct = sellerService.updateProductStatus(currentLogin.getUser().getUserId(), productId);
        return new ResponseEntity<Seller>(addedProduct, HttpStatus.OK);
    }

    //  update product
    @PutMapping(value = "/seller/updateProduct")
    public ResponseEntity<Seller> updateProduct(@RequestParam String key, @RequestParam Long productId, @RequestBody @Valid ProductDTO product) {
        Login currentLogin = loginService.isTokenValid(key);
        Seller addedProduct = sellerService.updateProducts(currentLogin.getUser().getUserId(), productId, product);
        return new ResponseEntity<Seller>(addedProduct, HttpStatus.ACCEPTED);
    }

    // View all products added by seller
    @GetMapping(value = "/seller/products")
    public ResponseEntity<List<Product>> viewSellers(@RequestParam String key) {
        Login currentLogin = loginService.isTokenValid(key);
        List<Product> productList = sellerService.getAllProductsBySeller(currentLogin.getUser().getUserId());
        return new ResponseEntity<List<Product>>(productList, HttpStatus.FOUND);
    }
}
