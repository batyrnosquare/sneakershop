package com.example.sneakershop.service;

import com.example.sneakershop.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtils{
    private SecretKey secretKey;
    private static final Long EXPIRATION_TIME = 86400000L;

    public JwtUtils(){
        String secretString = "319023798124719347120128403175831491304732589718349148798272";
        byte[] secretBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.secretKey =new SecretKeySpec(secretBytes, "HmacSHA256");
    }
    public String generateToken (Optional<User> userDetails){
        User user = userDetails.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken (HashMap<String, Object> claims, Optional<User> userDetails){
        User user = userDetails.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }


    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }


    public <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        return (extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }






}
