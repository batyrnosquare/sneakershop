package com.example.sneakershop.controller;

import com.example.sneakershop.model.User;
import com.example.sneakershop.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all_users")
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @PostMapping("/user/{username}")
    public User getUserByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.findById(id);
    }

    @DeleteMapping("/delete/user/{id}")
    public String deleteUserById(@PathVariable Long id){
        return userService.delete(id);
    }


}
