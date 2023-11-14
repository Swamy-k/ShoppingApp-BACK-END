package com.example.ShoppingApp.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="customers")
@PrimaryKeyJoinColumn(name = "customer_id")
public class Customer extends User{

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Order> orders = new ArrayList<Order>();

    @OneToOne
    @JoinColumn(name = "card_Number")
    private Card cardDetails;

    @JsonIgnore
    public Card getCardDetails() {
        return this.cardDetails;
    }

}
