package com.example.sneakershop.service;

import com.example.sneakershop.model.Socks;
import com.example.sneakershop.repository.SocksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocksService {

    @Autowired
    private SocksRepository socksRepository;

    public Socks save(Socks socks) {
        socksRepository.save(socks);
        return socks;
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
