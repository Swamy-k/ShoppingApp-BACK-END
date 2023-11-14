package com.example.ShoppingApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "seller")
@PrimaryKeyJoinColumn(name = "seller_id")
public class Seller extends User {

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("seller")
    private List<Product> products = new ArrayList<>();

    @Override
    public String toString() {
        return "Seller{" +
                "products=" + products +
                '}';
    }
}
