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

}
