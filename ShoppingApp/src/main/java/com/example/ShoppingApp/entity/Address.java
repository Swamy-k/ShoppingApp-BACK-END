package com.example.ShoppingApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="address_id")
    private Long addressId;

    @NotNull
    @Column(name = "state")
    private String state;

    @NotNull
    @Column(name = "city")
    private String city;

    @NotNull
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "pin_code")
    private String pincode;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("addresses")
    @JsonIgnore
    private User user;


    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", pincode='" + pincode + '\'' +
                ", user=" + user +
                '}';
    }
}
