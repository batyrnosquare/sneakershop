package com.example.sneakershop.service;

import com.example.sneakershop.model.*;
import com.example.sneakershop.repository.CartRepository;
import com.example.sneakershop.repository.ItemRepository;
import com.example.sneakershop.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
public class CartService {

    private final CartRepository cartRepository;

    private final JwtUtils jwtUtils;

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public CartService(CartRepository cartRepository, JwtUtils jwtUtils, UserRepository userRepository, ItemRepository itemRepository) {
        this.cartRepository = cartRepository;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public Cart addItemToCart(Long itemId, Integer size, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

        Sizes sizes = item.getSizes().stream()
                .filter(s -> s.getSize().equals(size))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Selected sizes are unavailable"));

        if(sizes.getQuantity()<=0){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Selected sizes are out of stuck");
        }


        Cart cart = cartRepository.findByUserId(user.getId()).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(user.getId());
            newCart.setItems(new ArrayList<>());
            newCart.setTotalPrice(0.0);
            newCart.setTotalQuantity(0);
            return newCart;
        });

        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }

        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        cartItem.setSize(size);
        cartItem.setQuantity(1);
        cartItem.setCart(cart);
        cart.getItems().add(cartItem);

        cart.setTotalPrice(cart.getTotalPrice() + item.getPrice());
        cart.setTotalQuantity(cart.getTotalQuantity() + 1);

        return cartRepository.save(cart);
    }

    public Cart getCartElements (String jwt) {
        if (jwt == null || jwt.isBlank()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT is missing");
        }

        String username = jwtUtils.extractUsername(jwt);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        return cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));
    }


    public Cart deleteItemFromCart(Long itemId, Integer size, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getItem().getId().equals(itemId) && item.getSize().equals(size))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found in cart"));

        cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getItem().getPrice() * cartItem.getQuantity()));
        cart.setTotalQuantity(cart.getTotalQuantity() - cartItem.getQuantity());
        cart.getItems().remove(cartItem);

        return cartRepository.save(cart);
    }
}
