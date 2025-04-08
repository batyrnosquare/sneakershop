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
    public Payment orderPayment(@CookieValue(name = "jwt") String jwt,@RequestBody Payment payment) {
        return paymentService.orderPayment(jwt, payment);
    }


    @PostMapping("/confirm")
    public Payment confirmPayment(@CookieValue(name = "jwt") String jwt, @RequestBody String confirmationCode) {
        return paymentService.confirmPayment(jwt, confirmationCode);
    }
}
