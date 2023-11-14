package com.example.ShoppingApp.repository;

import com.example.ShoppingApp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUserName(String username);

    Customer findByUserId(long customerId);

    Optional<Customer> findByUserPassword(String username);

    Optional<Customer> findByUserNameAndUserPassword(String userName, String password);

}
