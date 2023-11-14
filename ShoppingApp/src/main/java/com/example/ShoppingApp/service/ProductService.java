package com.example.ShoppingApp.service;

import com.example.ShoppingApp.constants.ProductCategory;
import com.example.ShoppingApp.dto.ProductDTO;
import com.example.ShoppingApp.entity.Product;
import com.example.ShoppingApp.entity.Seller;

import java.util.List;

public interface ProductService {
    Product addProduct(Seller seller, Product product);

    Product addProduct(Product product);

    Product updateProductStatusToOutOfStock(Long productId);

    Product updateProductStatusToUnAvailable(Long productId);

    Product updateProductStatusToAvailable(Long productId);

    Product getProductById(Long id);

    Product reduceQuantity(Long id, int quantityToReduce);


    String updateProduct(Long productId, ProductDTO product);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(ProductCategory category);

    String updateProductStatus(Long productId);

}
