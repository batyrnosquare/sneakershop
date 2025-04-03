package com.example.sneakershop.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDTO {

    private String username;
    private String password;
    private String email;
    private String role;
    private String token;
    private String refreshToken;

    public UserDTO(String username, String password, String email, String role, String token) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.token = token;
        this.refreshToken = refreshToken;
    }

}
