package com.example.sneakershop.service;

import com.example.sneakershop.constants.PaymentStatus;
import com.example.sneakershop.model.Orders;
import com.example.sneakershop.model.Payment;
import com.example.sneakershop.model.User;
import com.example.sneakershop.repository.OrderRepository;
import com.example.sneakershop.repository.PaymentRepository;
import com.example.sneakershop.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
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
        log.info("Username: {}", username);
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")));
        log.info("User: {}", user.get().getId());
        Orders order = orderRepository.findTopByUserIdOrderByIdDesc(user.get().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        log.info("Order: {}", order.getId());
        if (!order.getUserId().equals(user.get().getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to make this payment");
        }
        payment.setOrder(order);
        orderRepository.save(payment.getOrder());

        payment.setCreatedAt(LocalDateTime.now());
        payment.setAmount(order.getTotalPrice());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentMethod(payment.getPaymentMethod());
        payment.getOrder().setStatus(PaymentStatus.PENDING);
        payment.getOrder().setPayment(payment);
        String confirmationCode = String.valueOf((int) (Math.random() * 1000000));
        payment.setConfirmationCode(confirmationCode);
        log.info("Confirmation code: {}", confirmationCode);

        return paymentRepository.save(payment);
    }

    public Payment confirmPayment(String jwt, String confirmationCode) {
        if (jwt.startsWith("Bearer ")) {
            jwt = jwt.substring(7);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT token");
        }
        String username = jwtUtils.extractUsername(jwt);
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")));
        Orders order = orderRepository.findTopByUserIdOrderByIdDesc(user.get().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        if (!order.getUserId().equals(user.get().getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to confirm this payment");
        }
        Payment payment = paymentRepository.findTopByOrder_IdOrderByIdDesc(order.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));
        log.info("Confirmation code: {}", confirmationCode);
        log.info("Payment confirmation code: {}", payment.getConfirmationCode());
        if(!Objects.equals(confirmationCode, payment.getConfirmationCode())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid confirmation code");
        }else {
            payment.setStatus(PaymentStatus.CONFIRMED);
            payment.getOrder().setStatus(PaymentStatus.CONFIRMED);
        }
        return paymentRepository.save(payment);
    }


}
