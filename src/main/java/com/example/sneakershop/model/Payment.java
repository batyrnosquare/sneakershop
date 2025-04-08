package com.example.sneakershop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentMethod;

    private Double amount;

    private String status;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Orders order;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private String confirmationCode;


    public Payment() {
    }

}
