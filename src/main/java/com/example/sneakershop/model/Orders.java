package com.example.sneakershop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItems> orderItems = new ArrayList<>();

    private Integer totalQuantity;

    private Double totalPrice;

    @OneToOne
    @JoinColumn(name = "payment_id")
    @JsonIgnore
    private Payment payment;



    public void calculateTotals() {
        this.totalQuantity = orderItems.stream()
                .mapToInt(OrderItems::getQuantity)
                .sum();
        this.totalPrice = orderItems.stream()
                .mapToDouble(item -> item.getItem().getPrice() * item.getQuantity())
                .sum();
    }

    public Orders() {
    }

    public void addOrderItem(OrderItems item){
        this.orderItems.add(item);
        item.setOrder(this);
    }
}
