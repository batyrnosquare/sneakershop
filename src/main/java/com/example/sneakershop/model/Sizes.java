package com.example.sneakershop.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Sizes {
    @Id
    private Long id;

    private Integer size;

    private Integer quantity;

    public Sizes(Long id, Integer size, Integer quantity) {
        this.id = id;
        this.size = size;
        this.quantity = quantity;
    }

    public Sizes() {
    }

    public Sizes(Integer size, Integer quantity) {
        this.size = size;
        this.quantity = quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
