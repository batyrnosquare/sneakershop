package com.example.sneakershop.controller;
import com.example.sneakershop.model.UserDTO;
import com.example.sneakershop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> loginUser(@RequestBody UserDTO user) {
        UserDTO loggedUser = userService.login(user);

        ResponseCookie responseCookie = ResponseCookie.from("jwt", loggedUser.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .build();
        return ResponseEntity.ok()
                .header("Set-Cookie", responseCookie.toString())
                .body("Login successful");
    }
}
