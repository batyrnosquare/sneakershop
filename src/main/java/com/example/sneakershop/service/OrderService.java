package com.example.sneakershop.service;

import com.example.sneakershop.model.*;
import com.example.sneakershop.repository.ItemRepository;
import com.example.sneakershop.repository.OrderRepository;
import com.example.sneakershop.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")));
        Long userId = user.get().getId();
        Orders order = new Orders();
        order.setUserId(userId);
        order.setStatus("ORDER CREATED!");
        List<OrderItems> items = orderDTO.getOrderItems().stream()
                .map(orderItemDTO -> {
                    if (orderItemDTO.getQuantity() <= 0) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be greater than 0");
                    }
                    Item item = itemRepository.findById(orderItemDTO.getItem().getId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));

                    Sizes requestedSizes = item.getSizes().stream()
                            .filter(size -> size.getSize().equals(orderItemDTO.getSize()))
                            .findFirst()
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Size " + orderItemDTO.getSize() + " currently not available for item " + item.getId()));

                    if (requestedSizes.getQuantity() < orderItemDTO.getQuantity()) {
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough items available. Items left: " + requestedSizes.getQuantity());
                    }
                    OrderItems orderItem = new OrderItems();
                    orderItem.setItem(item);
                    orderItem.setSize(orderItemDTO.getSize());
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
