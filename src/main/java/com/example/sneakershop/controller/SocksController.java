package com.example.sneakershop.controller;

import com.example.sneakershop.model.Socks;
import com.example.sneakershop.service.SocksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/socks")
@RequiredArgsConstructor
public class SocksController {

    private SocksService socksService;

    @Autowired
    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping("/save")
        public ResponseEntity<Socks> saveSocks(@RequestBody Socks socks) {
            return ResponseEntity.status(201).body(socksService.save(socks));
        }

    @GetMapping("/get/{id}")
    public Socks getSocksById(@PathVariable Long id) {
        return socksService.findById(id);
    }

    @GetMapping("/get_all")
    public ResponseEntity<List<Socks>> getAllSocks() {
        return ResponseEntity.ok(socksService.findAll());
    }

    @PutMapping("/update/{id}")
    public Socks updateSocks(@RequestBody Socks socks) {
        return socksService.update(socks);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSocks(@PathVariable Long id) {
        socksService.delete(id);
    }

}
