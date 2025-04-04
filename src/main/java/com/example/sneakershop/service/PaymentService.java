package com.example.sneakershop.service;

import com.example.sneakershop.model.Orders;
import com.example.sneakershop.model.Payment;
import com.example.sneakershop.model.User;
import com.example.sneakershop.repository.OrderRepository;
import com.example.sneakershop.repository.PaymentRepository;
import com.example.sneakershop.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentService {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final JwtUtils jwtUtils;

    public PaymentService(UserRepository userRepository, PaymentRepository paymentRepository, OrderRepository orderRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.jwtUtils = jwtUtils;
    }

    public Payment orderPayment(String jwt, Payment payment) {
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT token");
        }
        String username = jwtUtils.extractUsername(jwt);
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")));
        Orders order = orderRepository.findById(payment.getOrder().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        if (!order.getUserId().equals(user.get().getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to make this payment");
        }
        payment.setCreatedAt(LocalDateTime.now());
        payment.setAmount(order.getTotalPrice());
        payment.setStatus("PENDING");
        payment.setPaymentMethod(payment.getPaymentMethod());
        payment.getOrder().setStatus("PAYMENT PENDING");
        payment.getOrder().setPayment(payment);

        orderRepository.save(payment.getOrder());
        return paymentRepository.save(payment);
    }
}
