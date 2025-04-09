package com.example.sneakershop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Item item;

    private Integer size;

    private Integer quantity;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Cart cart;

}
