package com.example.sneakershop.controller;


import com.example.sneakershop.model.Payment;
import com.example.sneakershop.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;


    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/order")
    public Payment orderPayment(Principal principal, @RequestBody Payment payment) {
        log.info("User: {}", principal.getName());
        return paymentService.orderPayment(principal.getName(), payment);
    }


    @PostMapping("/confirm")
    public Payment confirmPayment(Principal principal, @RequestBody String confirmationCode) {
        return paymentService.confirmPayment(principal.getName(), confirmationCode);
    }
}
