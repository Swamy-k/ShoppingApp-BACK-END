package com.example.ShoppingApp.service;

import com.example.ShoppingApp.entity.Address;
import com.example.ShoppingApp.entity.Customer;
import com.example.ShoppingApp.entity.User;
import com.example.ShoppingApp.exception.AddressNotFoundException;
import com.example.ShoppingApp.exception.CustomerNotFoundException;
import com.example.ShoppingApp.exception.UserNotFoundException;
import com.example.ShoppingApp.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public Address addAddress(Address address) {
        Address newAddress = null;
        newAddress = addressRepository.save(address);
        return newAddress;
    }

    @Override
    public Set<User> getAllUsersByCity(String city) {
        List<Address> addressList = addressRepository.findByCity(city);
        Set<User> users = new HashSet<>();

        if (!addressList.isEmpty()) {
            for (Address address : addressList) {
                users.add(address.getUser());
            }
        } else {
            throw new UserNotFoundException("No user found in " + city);
        }
        return users;
    }

    @Override
    public Set<User> getAllUsersByState(String state) {
        List<Address> addressList = addressRepository.findByState(state);

        Set<User> users = new HashSet<>();
        if (!addressList.isEmpty()) {
            for (Address address : addressList) {
                users.add(address.getUser());
            }
        } else {
            throw new CustomerNotFoundException("No user found in " + state);
        }
        return users;
    }

    @Override
    public Set<User> getAllUserByPincode(String pincode) {
        List<Address> addressList = addressRepository.findByPincode(pincode);
        Set<User> users = new HashSet<>();
        if (!addressList.isEmpty()) {
            for (Address address : addressList) {
                users.add(address.getUser());
            }
        } else {
            throw new UserNotFoundException("User not found in " + pincode);
        }
        return users;
    }

    @Override
    public Address persistCustomer(Customer customer, Address address) {
        address.setUser(customer);
        return addressRepository.save(address);
    }

    @Override
    public String deleteAddress(Long addressId) {
        Optional<Address> address = addressRepository.findById(addressId);

        //Change the user reference to Null to remove the bi-directional link
        address.ifPresent(value -> value.setUser(null));

        if (address.isPresent()) {
            addressRepository.deleteById(addressId);
            return "Address with ID : " + addressId + " deleted.";
        } else {
            throw new AddressNotFoundException("Address not found");
        }

    }

    @Override
    public boolean checkAddressId(Long addressId) {
        Optional<Address> address = addressRepository.findById(addressId);
        return address.isPresent();
    }

    @Override
    public Address getAddressById(Long addressId) {
        Optional<Address> address = addressRepository.findById(addressId);
        if (address.isPresent()) {
            return address.get();
        } else {
            throw new AddressNotFoundException("Invalid address ID");
        }

    }
}
