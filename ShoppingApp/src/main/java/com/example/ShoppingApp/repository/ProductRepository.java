package com.example.ShoppingApp.repository;

import com.example.ShoppingApp.constants.ProductCategory;
import com.example.ShoppingApp.constants.ProductStatus;
import com.example.ShoppingApp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductName(String productName);

    Product findByProductId(long productId);

    List<Product> findByCategory(ProductCategory category);

    List<Product> findByProductStatus(ProductStatus productStatus);


}
