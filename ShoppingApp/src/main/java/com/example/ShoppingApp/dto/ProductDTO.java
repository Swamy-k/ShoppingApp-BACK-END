package com.example.ShoppingApp.dto;

import com.example.ShoppingApp.constants.ProductCategory;
import com.example.ShoppingApp.constants.ProductStatus;
import com.example.ShoppingApp.entity.Seller;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String productName;

    private String productDescription;

    @Min(value = 1, message = "Product price should not be 0")
    private Double price;

    @Min(value = 1, message = "Minimum quantity should be 1")
    private Integer quantity;

    private ProductCategory category;

    private ProductStatus productStatus;

}