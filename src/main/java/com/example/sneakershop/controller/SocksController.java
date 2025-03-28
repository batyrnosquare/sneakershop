package com.example.sneakershop.controller;

import com.example.sneakershop.model.Socks;
import com.example.sneakershop.service.SocksService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socks")
@RequiredArgsConstructor
public class SocksController {

    @Autowired
    private SocksService socksService;

    @PostMapping("/save")
    public Socks saveSocks(@RequestBody Socks socks) {
        socksService.save(socks);
        return socks;
    }

    @PostMapping("/get/{id}")
    public Socks getSocksById(@PathVariable Long id) {
        return socksService.findById(id);
    }

    @PostMapping("/update/{id}")
    public Socks updateSocks(@RequestBody Socks socks) {
        return socksService.update(socks);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSocks(@PathVariable Long id) {
        socksService.delete(id);
    }

}
