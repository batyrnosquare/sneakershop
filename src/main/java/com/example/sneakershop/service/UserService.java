package com.example.sneakershop.service;

import com.example.sneakershop.constants.Role;
import com.example.sneakershop.model.User;
import com.example.sneakershop.model.UserDTO;
import com.example.sneakershop.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, JwtUtils jwtUtils, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDTO register(UserDTO userInfo){
        try {
            if (userRepository.existsByUsername(userInfo.getUsername())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already taken!");
            }
            if(userRepository.existsByEmail(userInfo.getEmail())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already taken!");
            }else {
                User user = new User();
                user.setUsername(userInfo.getUsername());
                user.setRole(Role.USER);
                user.setEmail(userInfo.getEmail());
                user.setPassword(passwordEncoder.encode(userInfo.getPassword()));
                userRepository.save(user);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return userInfo;
    }

    public UserDTO login(UserDTO userInfo) {
        UserDTO response = new UserDTO();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userInfo.getUsername(),
                            userInfo.getPassword()
                    )
            );
            var user = userRepository.findByUsername(userInfo.getUsername());
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken( new HashMap<>(), user);

            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login failed: " + e.getMessage());
        }
        return response;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public String delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(id);
        return "User with id " + id + " deleted successfully";
    }
}
