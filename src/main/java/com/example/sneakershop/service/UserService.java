package com.example.sneakershop.service;

import com.example.sneakershop.constants.Role;
import com.example.sneakershop.model.User;
import com.example.sneakershop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    public User register(User user){
        User newUser = userRepository.findByUsername(user.getUsername());
        try {
            if (newUser != null) {
                return null;
            } else {
                user.setRole(Role.USER);
                user.setPassword(user.getPassword());
                userRepository.save(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User login(User user) {
        User newUser = userRepository.findByUsername(user.getUsername());
        try {
            if (newUser != null) {
                if (newUser.getPassword().equals(user.getPassword())) {
                    return newUser;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(Long id) {

        return userRepository.findById(id);
    }


    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
