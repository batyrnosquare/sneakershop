package com.example.sneakershop.controller;

import com.example.sneakershop.model.Orders;
import com.example.sneakershop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public Orders createOrder(Principal principal) {
        return orderService.createOrder(principal.getName());
    }

    @GetMapping("/get/get_all")
    public Orders getAllOrders(Principal principal) {
        return orderService.getAllOrders(principal.getName());
    }

    @GetMapping("/get/id/{id}")
    public Orders getOrderById(@PathVariable Long id, Principal principal) {
        return orderService.getOrderById(id, principal.getName());
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrderById(@PathVariable Long id, Principal principal) {
        return orderService.deleteOrderById(id, principal.getName());
    }

}
