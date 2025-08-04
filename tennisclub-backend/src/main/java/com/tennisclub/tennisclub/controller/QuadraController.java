package com.tennisclub.tennisclub.controller;

import com.tennisclub.tennisclub.dto.QuadraDTO;
import com.tennisclub.tennisclub.model.Quadra;
import com.tennisclub.tennisclub.service.QuadraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quadras")
@CrossOrigin(origins = "http://localhost:3000")
public class QuadraController {

    @Autowired
    private QuadraService service;

    @PostMapping
    public ResponseEntity<Quadra> cadastrar(@RequestBody QuadraDTO dto) {
        Quadra novaQuadra = service.cadastrar(dto);
        return ResponseEntity.status(201).body(novaQuadra);
    }

    @GetMapping
    public ResponseEntity<List<Quadra>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }
}