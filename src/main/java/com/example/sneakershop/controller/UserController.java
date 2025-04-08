package com.example.sneakershop.controller;
import com.example.sneakershop.model.UserDTO;
import com.example.sneakershop.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
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
    public UserDTO registerUser(@Valid @RequestBody UserDTO user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody UserDTO user, HttpServletResponse response) {
        UserDTO loggedUser = userService.login(user);

        ResponseCookie responseCookie = ResponseCookie.from("jwt", loggedUser.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .build();
        response.addHeader("Set-Cookie", responseCookie.toString());
        return "Login successful";
    }
}
