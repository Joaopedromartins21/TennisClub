package com.joaopedroaguiar.tennisclub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuração de Segurança - Spring Security Configuration
 * Sistema TennisClub - Desenvolvido por: João Pedro Aguiar
 * 
 * Esta configuração define as políticas de segurança da aplicação,
 * incluindo CORS, autenticação e autorização.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configuração da cadeia de filtros de segurança
     * Para fins de demonstração do portfólio, a segurança está simplificada
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authz -> authz
                // Permite acesso público aos endpoints da API para demonstração
                .requestMatchers("/users/**").permitAll()
                .requestMatchers("/courts/**").permitAll()
                .requestMatchers("/bookings/**").permitAll()
                // Permite acesso ao Swagger UI e documentação da API
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/swagger-ui.html").permitAll()
                .requestMatchers("/api-docs/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                // Permite acesso ao console H2 para desenvolvimento
                .requestMatchers("/h2-console/**").permitAll()
                // Permite acesso aos recursos estáticos
                .requestMatchers("/static/**").permitAll()
                .requestMatchers("/public/**").permitAll()
                // Qualquer outra requisição requer autenticação
                .anyRequest().authenticated()
            )
            // Desabilita frame options para permitir H2 Console
            .headers(headers -> headers.frameOptions().disable());

        return http.build();
    }

    /**
     * Configuração CORS para permitir requisições do frontend
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Permite requisições de qualquer origem para demonstração
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        
        // Permite todos os métodos HTTP
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        
        // Permite todos os headers
        configuration.setAllowedHeaders(Arrays.asList("*"));
        
        // Permite credenciais
        configuration.setAllowCredentials(true);
        
        // Expõe headers de resposta
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }

    /**
     * Bean para codificação de senhas
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

