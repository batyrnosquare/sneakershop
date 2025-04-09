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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
public class PaymentService {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final JwtUtils jwtUtils;
    private final JavaMailSenderImpl mailSender;

    public PaymentService(UserRepository userRepository, PaymentRepository paymentRepository, OrderRepository orderRepository, JwtUtils jwtUtils, JavaMailSenderImpl mailSender) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.jwtUtils = jwtUtils;
        this.mailSender = mailSender;
    }

    public Payment orderPayment(String jwt, Payment payment) {
        if(jwt == null || jwt.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT is missing");
        }
        String username = jwtUtils.extractUsername(jwt);
        log.info("Username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        log.info("User: {}", user.getId());
        Orders order = orderRepository.findTopByUserIdOrderByIdDesc(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        log.info("Order: {}", order.getId());
        if (!order.getUserId().equals(user.getId())) {
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

        sendConfirmationEmail(user.getEmail(), confirmationCode);

        return paymentRepository.save(payment);
    }

    public Payment confirmPayment(String jwt, String confirmationCode) {
        if(jwt == null || jwt.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "JWT is missing");
        }
        String username = jwtUtils.extractUsername(jwt);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
        Orders order = orderRepository.findTopByUserIdOrderByIdDesc(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        if (!order.getUserId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to confirm this payment");
        }
        Payment payment = paymentRepository.findTopByOrder_IdOrderByIdDesc(order.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));
        if(!Objects.equals(confirmationCode, payment.getConfirmationCode())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid confirmation code");
        }else {
            payment.setStatus(PaymentStatus.CONFIRMED);
            payment.getOrder().setStatus(PaymentStatus.CONFIRMED);
        }
        return paymentRepository.save(payment);
    }

    private void sendConfirmationEmail(String userEmail, String confirmationCode){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Payment Confirmation Code");
        mailMessage.setText("Your payment confirmation code is: " + confirmationCode);
        mailSender.send(mailMessage);
        log.info("Confirmation email send to: {}", userEmail);
    }


}
