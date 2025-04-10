package com.example.sneakershop.repository;

import com.example.sneakershop.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findTopByOrder_IdOrderByIdDesc(Long orderId);

}
