package com.tennisclub.tennisclub.controller;


import com.tennisclub.tennisclub.dto.JogadorDTO;
import com.tennisclub.tennisclub.dto.LoginDTO;
import com.tennisclub.tennisclub.model.Jogador;
import com.tennisclub.tennisclub.service.JogadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jogadores")
@CrossOrigin(origins = "http://localhost:3000") // Para o frontend React

public class JogadorController {

    @Autowired
    private JogadorService service;

    @PostMapping
    public ResponseEntity<Jogador> cadastrar(@RequestBody JogadorDTO dto) {
        Jogador novoJogador = service.cadastrar(dto);
        return ResponseEntity.status(201).body(novoJogador);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            Jogador jogador = service.autenticar(loginDTO.email(), loginDTO.senha());
            return ResponseEntity.ok(jogador);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Jogador>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

}

