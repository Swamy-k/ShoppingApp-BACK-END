package com.example.ShoppingApp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderDTO {

    @Pattern(regexp="[0-9]{3}", message = "CVV must be 3 digits")
    @NotNull
    private String cvv;

    @NotNull
    private int addressId;

}