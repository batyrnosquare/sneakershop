package com.example.sneakershop.service;

import com.example.sneakershop.model.*;
import com.example.sneakershop.repository.ItemRepository;
import com.example.sneakershop.repository.OrderRepository;
import com.example.sneakershop.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ItemRepository itemRepository, JwtUtils jwtUtils, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    public Orders createOrder(String jwt, OrderDTO orderDTO) {
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7).trim();
        }
        log.info(jwt);
        log.info("Order items: {}", orderDTO.getOrderItems());

        String username = jwtUtils.extractUsername(jwt);
        Optional<User> user = userRepository.findByUsername(username);
        Long userId = user.get().getId();
        Orders order = new Orders();
        order.setUserId(userId);
        order.setStatus(orderDTO.getStatus());
        List<OrderItems> items = orderDTO.getOrderItems().stream()
                .map(orderItemDTO -> {
                    if (orderItemDTO.getQuantity() <= 0) {
                        throw new IllegalArgumentException("Quantity must be greater than 0");
                    }
                    Item item = itemRepository.findById(orderItemDTO.getItem().getId())
                            .orElseThrow(() -> new RuntimeException("Item not found"));
                    OrderItems orderItem = new OrderItems();
                    orderItem.setItem(item);
                    orderItem.setQuantity(orderItemDTO.getQuantity());
                    orderItem.setOrder(order);

                    return orderItem;
                })
                        .collect(Collectors.toList());

        order.setOrderItems(items);
        order.setCreatedAt(LocalDateTime.now());
        order.calculateTotals();
        return orderRepository.save(order);
    }



}
