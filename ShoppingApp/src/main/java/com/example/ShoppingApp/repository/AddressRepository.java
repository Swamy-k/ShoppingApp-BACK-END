package com.example.ShoppingApp.repository;

import com.example.ShoppingApp.entity.Address;
import com.example.ShoppingApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUser(User user);

    List<Address> findByCity(String city);

    List<Address> findByState(String state);

    List<Address> findByPincode(String city);

    List<Address> deleteAllByUser(User user);
}
