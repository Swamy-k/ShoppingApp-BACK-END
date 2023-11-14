package com.example.ShoppingApp.service;

import com.example.ShoppingApp.dto.UserDTO;
import com.example.ShoppingApp.entity.*;

import java.util.List;

public interface CustomerService {
    Customer addCustomer(Customer customer);

    String removeCustomer(UserDTO userInfo);

    List<Customer> viewAllCustomers();

    Customer updateCustomer(UserDTO userInfo, Long id);

    Customer findByUsernameAndPassword(String username, String password);

    Customer persistCustomer(Long customerID, Login login);

    Customer getCustomerById(Long customerId);

    Customer addCustomerCard(Long customerId, Card card);

    Customer addCustomerAddress(Long customerId, Address address);

    Customer removeCustomerAddress(Long customerId, Long addressId);

    Customer addCustomerOrder(Long customerId, Order order);
}
