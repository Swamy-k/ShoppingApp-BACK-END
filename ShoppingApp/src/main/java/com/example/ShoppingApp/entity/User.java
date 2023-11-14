package com.example.ShoppingApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


//Stores all subclasses in one table. Requires nullable fields for
//subclass fields
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)

//InheritanceType.TABLE_PER_CLASS:
// strategy stores all parent class fields in the subClass tables
//one table per Entity, with parent fields duplicated in subclass fields
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)

//InheritanceType.JOINED: strategy stores only the fields specific to those subclasses in subclass tables
//while all the shared fields are stored in parent class table
//one table per entity, including Parent.
//Parent field not duplicated in subclass tables
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;


    @NotNull
    @Pattern(regexp = "[a-z]{6,12}", message = "Username must be between 6 to 12 characters. Must only contain lowercase characters.")
    @Column(name = "user_name")
    private String userName;

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9]{6,12}", message = "Password must contain between 6 to 12 characters. Must be alphanumeric with both Upper and lowercase characters.")
    @Column(name = "user_password")
    private String userPassword;

    @NotNull
    @Pattern(regexp = "[0-9]{10}", message = "Mobile number must have 10 digits")
    @Column(name = "mobile_number")
    private String mobileNumber;

    @Pattern(regexp = "[a-z]{3,12}", message = "First Name must not contains numbers or special characters")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    @Pattern(regexp = "[a-z]{3,12}", message = "Last Name must not contains numbers or special characters")
    private String lastName;
    @Email
    @NotNull
    @Column(name = "email")
    private String email;

    @JsonIgnoreProperties("user")
    @OneToOne(cascade = CascadeType.ALL)
    private Login login;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<Address> addresses = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                ", login=" + login +
                ", addresses=" + addresses +
                '}';
    }
}
