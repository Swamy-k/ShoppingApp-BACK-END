package com.example.ShoppingApp.service;

import com.example.ShoppingApp.dto.UserDTO;
import com.example.ShoppingApp.entity.Login;
import com.example.ShoppingApp.entity.User;

public interface LoginService {
     User login(UserDTO userInfo, String UserType);

     Login isTokenValid(String token);

     String logout(String key);
}
