package com.example.sneakershop.controller;
import com.example.sneakershop.model.User;
import com.example.sneakershop.model.UserDTO;
import com.example.sneakershop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO user) {
        if (user == null || user.getUsername() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        UserDTO savedUser = userService.register(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.login(user));
    }
}
