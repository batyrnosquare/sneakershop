package com.example.sneakershop.controller;

import com.example.sneakershop.model.User;
import com.example.sneakershop.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/all_users")
    public List<User> getAllUsers(){
        return userService.findAll();
    }

    @GetMapping("/get/user/username/{username}")
    public User getUserByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }

    @GetMapping("/get/user/id/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.findById(id);
    }

    @PutMapping("/update/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        return userService.update(id, user);
    }

    @DeleteMapping("/delete/user/{id}")
    public String deleteUserById(@PathVariable Long id){
        return userService.delete(id);
    }


}
