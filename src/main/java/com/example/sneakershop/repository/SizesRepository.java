package com.example.sneakershop.repository;

import com.example.sneakershop.model.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SizesRepository extends JpaRepository<Sizes, Long> {

    Optional<Sizes> findByItem_IdAndSize(Long itemId, Integer size);
}
