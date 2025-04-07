package com.example.sneakershop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    @OneToMany
    private List<Item> items;

    private Double totalPrice;

    private Integer totalQuantity;

    public Cart() {
    }
}
