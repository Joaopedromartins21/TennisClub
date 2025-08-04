package com.tennisclub.tennisclub.controller;

import com.tennisclub.tennisclub.dto.ReservaDTO;
import com.tennisclub.tennisclub.model.Reserva;
import com.tennisclub.tennisclub.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservaController {
    
    @Autowired
    private ReservaService service;

    @PostMapping
    public ResponseEntity<Reserva> realizarReserva(@RequestBody ReservaDTO dto) {
        try {
            Reserva reserva = service.realizarReserva(dto);
            return ResponseEntity.ok(reserva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/quadra/{quadraId}")
    public ResponseEntity<List<Reserva>> listarReservasPorQuadra(@PathVariable Long quadraId) {
        return ResponseEntity.ok(service.listarReservasPorQuadra(quadraId));
    }

    @GetMapping("/jogador/{jogadorId}")
    public ResponseEntity<List<Reserva>> listarReservasPorJogador(@PathVariable Long jogadorId) {
        return ResponseEntity.ok(service.listarReservasPorJogador(jogadorId));
    }

    @GetMapping("/todas")
    public ResponseEntity<List<Reserva>> listarTodasReservas() {
        return ResponseEntity.ok(service.listarTodasReservas());
    }

}
