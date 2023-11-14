package com.example.ShoppingApp.dto;

import com.example.ShoppingApp.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {

    @NotNull(message = "Product Id must Not be Null")
    Long productId;

    private Product product;

    private Double itemPrice;

    @Min(value = 1, message = "Minimum quantity should be 1")
    @NotNull(message = "Product Id must not be Null")
    Integer requiredQuantity;


}
