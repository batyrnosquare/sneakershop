package com.example.sneakershop.controller;

import com.example.sneakershop.model.Cart;
import com.example.sneakershop.model.Orders;
import com.example.sneakershop.model.OrderDTO;
import com.example.sneakershop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public Orders createOrder(@CookieValue(name = "jwt") String jwt) {
        return orderService.createOrder(jwt);
    }
}
