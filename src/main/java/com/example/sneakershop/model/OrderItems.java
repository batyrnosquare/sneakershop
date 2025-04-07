package com.example.sneakershop.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "order_items")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    private Integer size;


    private Integer quantity;

    public OrderItems() {
    }

}
