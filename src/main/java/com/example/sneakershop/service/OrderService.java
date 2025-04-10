package com.example.sneakershop.service;

import com.example.sneakershop.model.*;
import com.example.sneakershop.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final SizesRepository sizesRepository;

    public OrderService(OrderRepository orderRepository, JwtUtils jwtUtils, UserRepository userRepository, CartRepository cartRepository, SizesRepository sizesRepository) {
        this.orderRepository = orderRepository;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.sizesRepository = sizesRepository;
    }

    public Orders createOrder(String jwt) {
        jwtMissing(jwt);
        String username = jwtUtils.extractUsername(jwt);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        Long userId = user.getId();

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

        if (cart.getItems() == null || cart.getItems().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty");

        Orders order = new Orders();
        order.setUserId(userId);
        order.setStatus("ORDER CREATED!");
        order.setCreatedAt(LocalDateTime.now());

        for(CartItem cartItem : cart.getItems()){

            OrderItems orderItems = new OrderItems();
            orderItems.setItem(cartItem.getItem());
            orderItems.setSize(cartItem.getSize());
            orderItems.setQuantity(cartItem.getQuantity());
            order.addOrderItem(orderItems);

            Item item = cartItem.getItem();

            Sizes requestedSizes = item.getSizes().stream()
                    .filter(size -> size.getSize().equals(cartItem.getSize()))
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Size " + cartItem.getSize() + " currently not available for item " + item.getId()));

            if (requestedSizes.getQuantity() < cartItem.getQuantity()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not enough items available. Items left: " + requestedSizes.getQuantity());
            }

            requestedSizes.setQuantity(requestedSizes.getQuantity() - cartItem.getQuantity());
            sizesRepository.save(requestedSizes);
        }
        order.calculateTotals();

        Orders savedOrder = orderRepository.save(order);
        cart.getItems().clear();
        cart.setTotalQuantity(0);
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);

        return savedOrder;
    }


    public Orders getAllOrders(String jwt) {
        jwtMissing(jwt);
        String username = jwtUtils.extractUsername(jwt);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        Long userId = user.getId();

        return orderRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
    }

    public Orders getOrderById(Long id, String jwt) {
        jwtMissing(jwt);
        String username = jwtUtils.extractUsername(jwt);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        Long userId = user.getId();

        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (!order.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to view this order");
        }
        return order;
    }

    private void jwtMissing(String jwt) {
        if (jwt == null || jwt.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT is missing");
        }
    }

    public String deleteOrderById(Long id, String jwt) {
        jwtMissing(jwt);
        String username = jwtUtils.extractUsername(jwt);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        Long userId = user.getId();

        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        if (!order.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete this order");
        }

        orderRepository.delete(order);
        return "Order with id " + id + " deleted successfully";
    }
}
