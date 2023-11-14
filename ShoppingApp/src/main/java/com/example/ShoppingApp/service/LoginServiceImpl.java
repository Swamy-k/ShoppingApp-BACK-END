package com.example.ShoppingApp.service;

import com.example.ShoppingApp.constants.LoginStatus;
import com.example.ShoppingApp.dto.UserDTO;
import com.example.ShoppingApp.entity.Login;
import com.example.ShoppingApp.entity.User;
import com.example.ShoppingApp.exception.CustomerNotFoundException;
import com.example.ShoppingApp.exception.InvalidLoginKeyException;
import com.example.ShoppingApp.exception.LoginFailedException;
import com.example.ShoppingApp.exception.SellerNotFoundException;
import com.example.ShoppingApp.repository.LoginRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private LoginRepository loginRepository;


    @Override
    public User login(UserDTO loginInfo, String userType) {
        if (userType.equalsIgnoreCase("customer")) {
            try {
                User customer = customerService.findByUsernameAndPassword(loginInfo.getUserName(), loginInfo.getUserPassword());
                Login newLogin = null;
                if (customer.getLogin() == null) {
                    newLogin = new Login();
                } else {
                    newLogin = customer.getLogin();
                    newLogin.newLogin();
                }
                loginRepository.save(newLogin);
                newLogin.setUser(customer);
                return customerService.persistCustomer(customer.getUserId(), newLogin);

            } catch (CustomerNotFoundException error) {
                throw new LoginFailedException(error.getMessage());
            }
        } else if (userType.equalsIgnoreCase("seller")) {
            try {
                User seller = sellerService.findByUsernameAndPassword(loginInfo.getUserName(), loginInfo.getUserPassword());
                Login newLogin = null;
                if (seller.getLogin() == null) {
                    newLogin = new Login();
                } else {
                    newLogin = seller.getLogin();
                    newLogin.newLogin();
                }

                loginRepository.save(newLogin);
                newLogin.setUser(seller);
                return sellerService.persistSeller(seller.getUserId(), newLogin);

            } catch (SellerNotFoundException error) {
                throw new LoginFailedException(error.getMessage());
            }


        } else {
            throw new LoginFailedException("Mention the correct path. Only ecommerce/login/customer and ecommerce/login/seller is allowed.");
        }
    }


    public String logout(String key) {
        Optional<Login> opt = loginRepository.findByApiKey(key);

        if (opt.isPresent()) {
            Login currentLogin = opt.get();
            if (currentLogin.getStatus() == LoginStatus.LOGGED_OUT) {
                return "User is already logged out";
            }
            currentLogin.revokeLogin();
            loginRepository.save(currentLogin);
        } else {
            throw new InvalidLoginKeyException("Invalid Login Key!");
        }

        return "User logged out successfully!";
    }

    @Override
    public Login isTokenValid(String apiKey) {

        Optional<Login> opt = loginRepository.findByApiKey(apiKey);

        if (opt.isPresent()) {
            Login currentLogin = opt.get();
            LocalDateTime expiryTime = currentLogin.getKeyExpiryDate();
            if (currentLogin.getStatus() == LoginStatus.LOGGED_IN && LocalDateTime.now().isBefore(expiryTime)) {
                return currentLogin;
            } else {
                currentLogin.revokeLogin();
                loginRepository.save(currentLogin);
                throw new InvalidLoginKeyException("Login Key has expired please login again to generate a new key!");
            }

        } else {
            throw new InvalidLoginKeyException("Invalid Login Key!");
        }
    }
}
