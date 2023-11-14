package com.example.ShoppingApp.service;

import com.example.ShoppingApp.dto.ProductDTO;
import com.example.ShoppingApp.dto.UserDTO;
import com.example.ShoppingApp.entity.Address;
import com.example.ShoppingApp.entity.Login;
import com.example.ShoppingApp.entity.Product;
import com.example.ShoppingApp.entity.Seller;

import java.util.List;

public interface SellerService {

    Seller addSeller(Seller seller);

    String removeSeller(UserDTO userInfo);

    Seller updateSeller(UserDTO sellerInfo, Long id);

    Seller addProducts(Long sellerId, Product product);

    Seller findByUsernameAndPassword(String username, String password);

    Seller persistSeller(Long sellerId, Login login);

    Seller addSellerAddress(Long sellerId, Address address);

    Seller updateProducts(Long sellerId, Long productId, ProductDTO product);

    Seller updateProductStatus(Long sellerId, Long productId);

    Seller removeSellerAddress(Long sellerId, Long addressId);

    List<Product> getAllProductsBySeller(Long sellerId);

    List<Seller> getAllSellers();

    Seller getSellerById(Long sellerId);
}
