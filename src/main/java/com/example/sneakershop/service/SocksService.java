package com.example.sneakershop.service;

import com.example.sneakershop.model.Item;
import com.example.sneakershop.model.Socks;
import com.example.sneakershop.repository.ItemRepository;
import com.example.sneakershop.repository.SocksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocksService {

    private final ItemRepository itemRepository;

    private final SocksRepository socksRepository;

    public Socks save(Socks socks) {
        try {
            return socksRepository.save(socks);
        } catch (Exception e) {
            throw new RuntimeException("Error saving socks: " + e.getMessage());
        }
    }


    public Socks findById(Long id) {
        return socksRepository.findById(id).orElse(null);
    }

    public Socks update(Socks socks) {
        return socksRepository.save(socks);
    }

    public void delete(Long id) {
        socksRepository.deleteById(id);
    }
}
