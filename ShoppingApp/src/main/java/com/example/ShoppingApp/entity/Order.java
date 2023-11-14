package com.example.ShoppingApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Integer orderId;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "items")
    private List<Item> orderedItems;

    @Column(name = "products_cost")
    private Double itemsCost;

    @Column(name = "gst")
    private Double gst;

    @Column(name = "delivery_charge")
    private Double deliveryCharge;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "shippedFrom", column = @Column(name = "shipped_from_address")),
            @AttributeOverride(name = "shippedTo", column = @Column(name = "shipped_to_address")),
            @AttributeOverride(name = "expectedDate", column = @Column(name = "expected_delivery_date"))
    })
    private Shipment shipment;

    //Last 4 digits of card used to make the payment
    @Column(name = "card_used_for_payment")
    private String cardUsedForPayment;

    @Column(name = "order_date")
    private LocalDate orderDate;

}