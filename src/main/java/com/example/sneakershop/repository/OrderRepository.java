package com.example.sneakershop.repository;

import com.example.sneakershop.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findTopByUserIdOrderByIdDesc(Long userId);

}
