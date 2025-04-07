package com.example.sneakershop.service;

import com.example.sneakershop.model.Cart;
import com.example.sneakershop.model.Item;
import com.example.sneakershop.model.User;
import com.example.sneakershop.repository.CartRepository;
import com.example.sneakershop.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, JwtUtils jwtUtils, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    public Cart addItemToCart(Item item, String jwt) {
        if (jwt.startsWith("Bearer ")) {
            jwt= jwt.substring(7);
        } else {
            System.out.println("Invalid JWT token");
        }
        String username = jwtUtils.extractUsername(jwt);
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")));

        Cart cart = cartRepository.findByUserId(user.orElse(null).getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

        cart.getItems().add(item);
        cart.setTotalPrice(cart.getTotalPrice() + item.getPrice());
        cart.setTotalQuantity(cart.getTotalQuantity() + 1);
        cart.setUserId(user.get().getId());
        return cartRepository.save(cart);
    }

}
