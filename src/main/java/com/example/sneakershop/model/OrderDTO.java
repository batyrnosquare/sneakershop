package com.example.sneakershop.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class OrderDTO {
    private Long userId;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderItemsDTO> orderItems;

}
