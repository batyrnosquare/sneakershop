package com.example.sneakershop.repository;

import com.example.sneakershop.model.Shoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShoesRepository extends JpaRepository<Shoes, Long> {
}
