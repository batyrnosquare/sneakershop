package com.example.sneakershop.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItemsDTO {
    private Item item;
    private Integer quantity;
    private Integer size;

}
