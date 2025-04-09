package com.example.sneakershop.controller;


import com.example.sneakershop.model.Cart;
import com.example.sneakershop.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public Cart addToCart(@RequestBody Map<String, Object> payload,@CookieValue(name = "jwt") String jwt) {
        Long itemId = Long.parseLong(payload.get("itemId").toString());
        Integer size = Integer.parseInt(payload.get("size").toString());
        return cartService.addItemToCart(itemId, size, jwt);
    }
}

