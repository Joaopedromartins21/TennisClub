package com.joaopedroaguiar.tennisclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TennisClub Application - Sistema de Agendamento de Quadras de Tênis
 * Desenvolvido por: João Pedro Aguiar
 * 
 * Esta aplicação fornece um sistema completo para gerenciamento e agendamento
 * de quadras de tênis, incluindo funcionalidades para usuários, quadras e reservas.
 */
@SpringBootApplication
public class TennisClubApplication {

    public static void main(String[] args) {
        SpringApplication.run(TennisClubApplication.class, args);
        System.out.println("=== TennisClub Application Started ===");
        System.out.println("Desenvolvido por: João Pedro Aguiar");
        System.out.println("API Base URL: /api/joaopedroaguiar/tennisclub/v1");
        System.out.println("Swagger UI: http://localhost:8080/api/joaopedroaguiar/tennisclub/v1/swagger-ui.html");
        System.out.println("H2 Console: http://localhost:8080/api/joaopedroaguiar/tennisclub/v1/h2-console");
        System.out.println("=======================================");
    }
}

