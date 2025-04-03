package com.example.sneakershop.service;

import com.example.sneakershop.model.Order;
import com.example.sneakershop.model.User;
import com.example.sneakershop.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

}
