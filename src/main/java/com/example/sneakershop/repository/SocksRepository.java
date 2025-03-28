package com.example.sneakershop.repository;

import com.example.sneakershop.model.Socks;
import com.example.sneakershop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocksRepository extends JpaRepository<Socks, Long> {
}
