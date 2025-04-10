package com.example.sneakershop.controller;

import com.example.sneakershop.model.Orders;
import com.example.sneakershop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;

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

    @GetMapping("/get/get_all")
    public Orders getAllOrders(@CookieValue(name = "jwt") String jwt) {
        return orderService.getAllOrders(jwt);
    }

    @GetMapping("/get/id/{id}")
    public Orders getOrderById(@PathVariable Long id, @CookieValue(name = "jwt") String jwt) {
        return orderService.getOrderById(id, jwt);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrderById(@PathVariable Long id, @CookieValue(name = "jwt") String jwt) {
        return orderService.deleteOrderById(id, jwt);
    }

}
