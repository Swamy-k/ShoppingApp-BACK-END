package com.example.ShoppingApp.controller;

import com.example.ShoppingApp.dto.UserDTO;
import com.example.ShoppingApp.entity.User;
import com.example.ShoppingApp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppingApp")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login/{type}")
    public ResponseEntity<User> loginUser(@RequestBody UserDTO userDTO, @PathVariable String type) {
        User loggedInUser = loginService.login(userDTO, type);
        return new ResponseEntity<User>(loggedInUser, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestParam String key) {
        String logoutMessage = loginService.logout(key);
        return new ResponseEntity<String>(logoutMessage, HttpStatus.OK);
    }
}
