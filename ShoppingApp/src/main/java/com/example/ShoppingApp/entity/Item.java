package com.example.ShoppingApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Long itemId;

    @OneToOne
    @JsonIgnoreProperties(value={
            "rating",
            "productId",
            "seller",
            "quantity"

    })

    private Product product;

    @Column(name = "item_price")
    private Double itemPrice;

    @Column(name = "required_quantity")
    private Integer requiredQuantity;
}
