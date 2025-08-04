package com.tennisclub.tennisclub.service;

import com.tennisclub.tennisclub.dto.QuadraDTO;
import com.tennisclub.tennisclub.model.Quadra;
import com.tennisclub.tennisclub.repository.QuadraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuadraService {

    @Autowired
    private QuadraRepository repository;

    public Quadra cadastrar(QuadraDTO dto) {
        Quadra quadra = new Quadra();
        quadra.setNome(dto.nome());
        quadra.setTipo(dto.tipo());
        quadra.setDisponivel(dto.disponivel());
        quadra.setLocalizacao(dto.localizacao());
        return repository.save(quadra);
    }

    public List<Quadra> listarTodas() {
        return repository.findAll();
    }
}
