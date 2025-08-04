package com.tennisclub.tennisclub.service;

import com.tennisclub.tennisclub.dto.JogadorDTO;
import com.tennisclub.tennisclub.dto.LoginDTO;
import com.tennisclub.tennisclub.model.Jogador;
import com.tennisclub.tennisclub.repository.JogadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JogadorService {

    @Autowired
    private JogadorRepository repository;

    public Jogador cadastrar(JogadorDTO dto) {
        if (repository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        Jogador jogador = new Jogador();
        jogador.setNome(dto.nome());
        jogador.setEmail(dto.email());
        jogador.setSenha(dto.senha());
        jogador.setNivel(dto.nivel());
        jogador.setLocalizacao(dto.localizacao());
        jogador.setPapel(dto.papel() != null ? dto.papel() : "ALUNO"); // Se não especificado, será ALUNO

        return repository.save(jogador);
    }


    public Jogador autenticar(String email, String senha) {
        return repository.findByEmail(email)
                .filter(jogador -> jogador.getSenha().equals(senha))
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos"));
    }

    public List<Jogador> listarTodos() {
        return repository.findAll();
    }

    public Jogador buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }


}