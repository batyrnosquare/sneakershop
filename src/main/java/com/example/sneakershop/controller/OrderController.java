package com.example.sneakershop.controller;

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
    public ResponseEntity<Orders> createOrder(@RequestHeader("Authorization") String jwt, @RequestBody OrderDTO order) {
        log.info(jwt);
        Orders createdOrder = orderService.createOrder(jwt, order);
        return ResponseEntity.status(201).body(createdOrder);
    }
}
