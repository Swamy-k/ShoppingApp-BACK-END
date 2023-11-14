package com.example.ShoppingApp.dto;

import com.example.ShoppingApp.entity.Item;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private List<Item> orderedItems = new ArrayList<>();
    private Double itemsCost;
    private Double gst;
    private Double deliveryCharge;
    private Double totalAmount;
    private String cardUsedForPayment;
    private LocalDate orderDate;


}