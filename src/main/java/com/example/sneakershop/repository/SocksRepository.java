package com.example.sneakershop.repository;

import com.example.sneakershop.model.Socks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocksRepository extends JpaRepository<Socks, Long> {
}
