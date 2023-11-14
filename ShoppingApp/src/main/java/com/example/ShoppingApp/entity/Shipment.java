package com.example.ShoppingApp.entity;

import com.example.ShoppingApp.constants.DeliveryStatus;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Shipment {
    private String shippedFrom;

    private String shippedTo;

    private LocalDate expectedDate;

    private DeliveryStatus deliveryStatus = DeliveryStatus.IN_TRANSIT;


}
