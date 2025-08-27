package com.joaopedroaguiar.tennisclub.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuração OpenAPI/Swagger - Documentação da API
 * Sistema TennisClub - Desenvolvido por: João Pedro Aguiar
 * 
 * Esta configuração define as informações da documentação da API,
 * incluindo metadados, contato e servidores.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TennisClub API")
                        .version("1.0.0")
                        .description("Sistema de Agendamento de Quadras de Tênis - API REST completa para gerenciamento de usuários, quadras e agendamentos. " +
                                   "Desenvolvido como projeto de portfólio demonstrando conhecimentos em Java, Spring Boot, JPA/Hibernate, " +
                                   "arquitetura REST, documentação de API e boas práticas de desenvolvimento.")
                        .contact(new Contact()
                                .name("João Pedro Aguiar")
                                .email("joaopedroaguiar@example.com")
                                .url("https://github.com/Joaopedromartins21"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080/api/joaopedroaguiar/tennisclub/v1")
                                .description("Servidor de Desenvolvimento - João Pedro Aguiar TennisClub"),
                        new Server()
                                .url("https://tennisclub-api.joaopedroaguiar.com/api/joaopedroaguiar/tennisclub/v1")
                                .description("Servidor de Produção - João Pedro Aguiar TennisClub")));
    }
}

