package com.example.ShoppingApp.entity;

import com.example.ShoppingApp.constants.ProductCategory;
import com.example.ShoppingApp.constants.ProductStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long productId;

    @NotNull(message = "Product name cannot be empty")
    @Column(name = "product_name")
    private String productName;

    @NotNull(message = "Product must have description")
    @Column(name = "product_description")
    private String productDescription;

    @Min(value = 1, message = "Product price should not be 0")
    @Column(name = "price")
    private Double price;

    @Min(value = 0, message = "Minimum quantity should be 1")
    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "category")
    private ProductCategory category;

    @Column(name = "productStatus")
    private ProductStatus productStatus;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("products")
    @JsonIgnore
    private Seller seller;

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", category=" + category +
                ", productStatus=" + productStatus +
                '}';
    }
}
