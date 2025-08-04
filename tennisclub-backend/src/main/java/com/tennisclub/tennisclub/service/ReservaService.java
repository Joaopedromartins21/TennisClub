package com.tennisclub.tennisclub.service;

import com.tennisclub.tennisclub.dto.ReservaDTO;
import com.tennisclub.tennisclub.model.Reserva;
import com.tennisclub.tennisclub.model.Quadra;
import com.tennisclub.tennisclub.model.Jogador;
import com.tennisclub.tennisclub.repository.ReservaRepository;
import com.tennisclub.tennisclub.repository.QuadraRepository;
import com.tennisclub.tennisclub.repository.JogadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;
    
    @Autowired
    private QuadraRepository quadraRepository;
    
    @Autowired
    private JogadorRepository jogadorRepository;

    @Transactional
    public Reserva realizarReserva(ReservaDTO dto) {
        Quadra quadra = quadraRepository.findById(dto.quadraId())
            .orElseThrow(() -> new RuntimeException("Quadra não encontrada"));
            
        Jogador jogador = jogadorRepository.findById(dto.jogadorId())
            .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));

        List<Reserva> reservasExistentes = reservaRepository
            .findByQuadraAndDataHoraAndStatus(quadra, dto.dataHora(), "CONFIRMADA");

        if (!quadra.isDisponivel()) {
            throw new RuntimeException("Quadra indisponível");
        }

        Reserva reservaExistente = null;
        if (!reservasExistentes.isEmpty()) {
            reservaExistente = reservasExistentes.get(0);
            if (reservaExistente.getJogadores().size() >= 2) {
                throw new RuntimeException("Quadra já está com lotação máxima");
            }
            reservaExistente.getJogadores().add(jogador);
            return reservaRepository.save(reservaExistente);
        }

        Reserva novaReserva = new Reserva();
        novaReserva.setQuadra(quadra);
        novaReserva.getJogadores().add(jogador);
        novaReserva.setDataHora(dto.dataHora());
        novaReserva.setStatus("CONFIRMADA");
        
        return reservaRepository.save(novaReserva);
    }

    public List<Reserva> listarReservasPorQuadra(Long quadraId) {
        return reservaRepository.findByQuadraId(quadraId);
    }

    public List<Reserva> listarReservasPorJogador(Long jogadorId) {
        return reservaRepository.findByJogadoresId(jogadorId);
    }

    public List<Reserva> listarTodasReservas() {
        return reservaRepository.findAll();
    }

}
