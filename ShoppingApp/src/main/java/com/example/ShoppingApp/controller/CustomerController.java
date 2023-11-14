package com.example.ShoppingApp.controller;

import com.example.ShoppingApp.dto.UserDTO;
import com.example.ShoppingApp.entity.Address;
import com.example.ShoppingApp.entity.Card;
import com.example.ShoppingApp.entity.Customer;
import com.example.ShoppingApp.entity.Login;
import com.example.ShoppingApp.service.AddressService;
import com.example.ShoppingApp.service.CustomerService;
import com.example.ShoppingApp.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingApp/customerGateway")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private AddressService addressService;


    // Adds a new customer
    @PostMapping(value = "/customer")
    public ResponseEntity<Customer> addCustomer(@RequestBody @Valid Customer customer) {
        Customer newCustomer = customerService.addCustomer(customer);
        return new ResponseEntity<Customer>(newCustomer, HttpStatus.CREATED);
    }


    // Returns a list of all the customers
    @GetMapping(value = "/customers")
    public ResponseEntity<List<Customer>> viewCustomers() {
        List<Customer> customers = customerService.viewAllCustomers();
        return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
    }

    //  Deletes the user provided if the id and password matches
    @DeleteMapping(value = "/customer")
    public ResponseEntity<String> deleteCustomer(@RequestBody UserDTO userDTO) {
        String status = customerService.removeCustomer(userDTO);
        return new ResponseEntity<String>(status, HttpStatus.OK);
    }

    //  Updates the fields provided in the userInfo (any field except userId can be updated
    @PutMapping(value = "/customer")
    public ResponseEntity<Customer> updateCustomerUsingAPI(@RequestBody @Valid UserDTO userInfo, @RequestParam String key) {
        Login currentLogin = loginService.isTokenValid(key);
        Customer updatedCustomer = customerService.updateCustomer(userInfo, currentLogin.getUser().getUserId());
        return new ResponseEntity<Customer>(updatedCustomer, HttpStatus.OK);
    }

    //  Adds a new customer card details
    @PostMapping(value = "/customer/card")
    public ResponseEntity<Customer> addCustomerCardDetails(@RequestParam String key, @RequestBody Card card) {
        Login currentLogin = loginService.isTokenValid(key);
        Customer getCustomer = customerService.addCustomerCard(currentLogin.getUser().getUserId(), card);
        return new ResponseEntity<Customer>(getCustomer, HttpStatus.CREATED);
    }

    // Adds a new address to the address list of the customer
    @PostMapping(value = "/customer/address")
    public ResponseEntity<Customer> addCustomerAddress(@RequestParam String key, @RequestBody @Valid Address address) {
        Login currentLogin = loginService.isTokenValid(key);
        Customer customer = customerService.addCustomerAddress(currentLogin.getUser().getUserId(), address);
        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);

    }

    // Adds a new address to the address list of the seller
    @DeleteMapping(value = "/customer/address")
    public ResponseEntity<Customer> removeCustomerAddress(@RequestParam String key, @RequestParam Long addressId) {
        Login currentLogin = loginService.isTokenValid(key);
        Customer customer = customerService.removeCustomerAddress(currentLogin.getUser().getUserId(), addressId);
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);

    }
}

