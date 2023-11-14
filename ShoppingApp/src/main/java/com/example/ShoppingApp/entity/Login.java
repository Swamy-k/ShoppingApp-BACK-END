package com.example.ShoppingApp.entity;

import com.example.ShoppingApp.constants.LoginStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "login")
public class Login implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long loginId;

    @Column(name = "api_key")
    private String apiKey = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);

    private LocalDateTime keyExpiryDate = LocalDateTime.now().plusHours(4);

    private LoginStatus status = LoginStatus.LOGGED_IN;

    @JsonIgnoreProperties("login")
    @OneToOne(cascade = CascadeType.ALL)
    private User user;


    public void newLogin() {
        this.apiKey = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        this.keyExpiryDate = LocalDateTime.now().plusHours(4);
        this.status = LoginStatus.LOGGED_IN;
    }

    public void revokeLogin() {
        this.apiKey = null;
        this.keyExpiryDate = null;
        this.status = LoginStatus.LOGGED_OUT;
    }


}
