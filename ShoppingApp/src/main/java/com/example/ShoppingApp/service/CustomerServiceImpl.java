package com.example.ShoppingApp.service;

import com.example.ShoppingApp.dto.UserDTO;
import com.example.ShoppingApp.entity.*;
import com.example.ShoppingApp.exception.AddressNotFoundException;
import com.example.ShoppingApp.exception.CustomerAlreadyExistException;
import com.example.ShoppingApp.exception.CustomerNotFoundException;
import com.example.ShoppingApp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CardService cardService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private OrderService orderService;

    @Override
    public Customer addCustomer(Customer customer) {

        Optional<Customer> opt = customerRepository.findByUserName(customer.getUserName());

        if (opt.isEmpty()) {
            customer.setCart(new Cart());
            return customerRepository.save(customer);

        } else {
            throw new CustomerAlreadyExistException("Customer with the given username already exists.");
        }
    }

    @Override
    public String removeCustomer(UserDTO userInfo) {

        Optional<Customer> customer = customerRepository.findByUserName(userInfo.getUserName());

        //if the customer is present & login detials match we delete the customer
        if (customer.isPresent() && customer.get().getUserPassword().equals(userInfo.getUserPassword())) {
            customerRepository.delete(customer.get());
        } else {
            throw new CustomerNotFoundException("username/password is wrong. Please provide the correct details to perform this operation");
        }

        return "Successfully deleted " + userInfo.getUserName() + "'s Account from the database";

    }


    @Override
    public List<Customer> viewAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("No Customers registered on the portal!");
        }
        return customers;
    }


    @Override
    public Customer updateCustomer(UserDTO customerInfo, Long id) {
        Optional<Customer> opt = customerRepository.findById(id);
        if (opt.isPresent()) {
            Customer customer = opt.get();

            if (customerInfo.getEmail() != null) {
                customer.setEmail(customerInfo.getEmail());
            }

            if (customerInfo.getFirstName() != null) {
                customer.setFirstName(customerInfo.getFirstName());
            }

            if (customerInfo.getLastName() != null) {
                customer.setLastName(customerInfo.getLastName());
            }


            if (customerInfo.getMobileNumber() != null) {
                customer.setMobileNumber(customerInfo.getMobileNumber());
            }

            if (customerInfo.getUserName() != null) {
                customer.setUserName(customerInfo.getUserName());
            }

            if (customerInfo.getUserPassword() != null) {
                customer.setUserPassword(customerInfo.getUserPassword());
            }

            return customerRepository.save(customer);

        } else {
            throw new CustomerNotFoundException("No customer exists with the given id!");
        }

    }


    @Override
    public Customer findByUsernameAndPassword(String username, String password) {

        Optional<Customer> customer = customerRepository.findByUserNameAndUserPassword(username, password);

        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new CustomerNotFoundException("No such customer. Please check the provided details.");
        }
    }


    @Override
    public Customer getCustomerById(Long customerId) {

        Optional<Customer> opt = customerRepository.findById(customerId);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new CustomerNotFoundException("No such customer. Please check the provided details.");
        }
    }


    @Override
    public Customer addCustomerCard(Long customerId, Card card) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isPresent()) {
            cardService.addCard(card);
            customer.get().setCardDetails(card);
            return customerRepository.save(customer.get());
        } else {
            throw new CustomerAlreadyExistException("Customer with the given username already exists.");
        }

    }

    @Override
    public Customer addCustomerAddress(Long customerId, Address address) {
        Optional<Customer> getCustomer = customerRepository.findById(customerId);

        if (getCustomer.isPresent()) {
            address.setUser(getCustomer.get());
            addressService.addAddress(address);
            getCustomer.get().getAddresses().add(address);

            return customerRepository.save(getCustomer.get());
        } else {
            throw new CustomerNotFoundException("No such customer. Please check the provided details.");
        }
    }

    @Override
    public Customer persistCustomer(Long customerID, Login login) {
        Optional<Customer> checkCustomer = customerRepository.findById(customerID);
        if (checkCustomer.isPresent()) {
            Customer customer = checkCustomer.get();
            customer.setLogin(login);
            customerRepository.save(customer);
            return customer;
        } else {
            throw new CustomerNotFoundException("No such customer. Please check the provided details.");
        }

    }

    @Override
    public Customer removeCustomerAddress(Long customerId, Long addressId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        boolean flag = false;

        if (customer.isPresent()) {
            for (int i = 0; i < customer.get().getAddresses().size(); i++) {
                if (customer.get().getAddresses().get(i).getAddressId() == addressId) {
                    customer.get().getAddresses().remove(i);
                    flag = true;
                }
            }

            if (!flag) {
                throw new AddressNotFoundException("Address Not Found");
            }

            //save the customer & address to get bi directional
            Customer savedcustomer = customerRepository.save(customer.get());
            addressService.deleteAddress(addressId);

            return savedcustomer;
        } else {
            throw new CustomerNotFoundException("No such customer. Please check the provided details.");
        }
    }


    @Override
    public Customer addCustomerOrder(Long customerId, Order order) {

        Optional<Customer> getCustomer = customerRepository.findById(customerId);

        if (getCustomer.isPresent()) {
            //Persist the order
            Order addOrder = orderService.addOrder(order);
            //Add the orders to the customer Id provided
            getCustomer.get().getOrders().add(addOrder);
            return customerRepository.save(getCustomer.get());
        } else {
            throw new CustomerAlreadyExistException("Wrong customer ID, check your login key");
        }

    }
}
