package com.example.sneakershop.controller;


import com.example.sneakershop.model.Cart;
import com.example.sneakershop.model.Item;
import com.example.sneakershop.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody Item item, @CookieValue(name = "jwt") String jwt) {
        Cart savedCart = cartService.addItemToCart(item, jwt);
        return ResponseEntity.ok(savedCart);
    }
}

