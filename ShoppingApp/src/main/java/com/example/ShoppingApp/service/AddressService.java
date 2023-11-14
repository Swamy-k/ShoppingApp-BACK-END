package com.example.ShoppingApp.service;

import com.example.ShoppingApp.entity.Address;
import com.example.ShoppingApp.entity.Customer;
import com.example.ShoppingApp.entity.User;

import java.util.Set;

public interface AddressService {
    public Address addAddress(Address address);

    public Set<User> getAllUsersByCity(String city);

    public Set<User> getAllUsersByState(String state);

    public Set<User> getAllUserByPincode(String pincode);

    public Address persistCustomer(Customer customer, Address address);

    public String deleteAddress(Long addressId);

    public boolean checkAddressId(Long addressId);

    public Address getAddressById(Long addressId);
}
