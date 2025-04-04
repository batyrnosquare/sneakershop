package com.example.sneakershop.controller;


import com.example.sneakershop.model.Payment;
import com.example.sneakershop.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;


    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/order")
    public ResponseEntity<Payment> orderPayment(@RequestHeader("Authorization") String jwt,@RequestBody Payment payment) {
        Payment createdPayment = paymentService.orderPayment(jwt, payment);
        return ResponseEntity.status(201).body(createdPayment);
    }
}
