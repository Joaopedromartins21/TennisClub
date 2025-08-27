package com.joaopedroaguiar.tennisclub.repository;

import com.joaopedroaguiar.tennisclub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório UserRepository - Interface para operações de dados dos usuários
 * Sistema TennisClub - Desenvolvido por: João Pedro Aguiar
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca um usuário pelo email
     * @param email Email do usuário
     * @return Optional contendo o usuário se encontrado
     */
    Optional<User> findByEmail(String email);

    /**
     * Verifica se existe um usuário com o email especificado
     * @param email Email a ser verificado
     * @return true se existe, false caso contrário
     */
    boolean existsByEmail(String email);

    /**
     * Busca usuários pelo nome (busca parcial, case-insensitive)
     * @param name Nome ou parte do nome
     * @return Lista de usuários encontrados
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByNameContainingIgnoreCase(@Param("name") String name);

    /**
     * Busca usuários por role
     * @param role Role do usuário
     * @return Lista de usuários com a role especificada
     */
    List<User> findByRole(User.Role role);

    /**
     * Busca usuários ativos
     * @param isActive Status ativo
     * @return Lista de usuários ativos ou inativos
     */
    List<User> findByIsActive(Boolean isActive);

    /**
     * Busca usuários ativos por role
     * @param role Role do usuário
     * @param isActive Status ativo
     * @return Lista de usuários ativos com a role especificada
     */
    List<User> findByRoleAndIsActive(User.Role role, Boolean isActive);

    /**
     * Conta o número total de usuários ativos
     * @return Número de usuários ativos
     */
    @Query("SELECT COUNT(u) FROM User u WHERE u.isActive = true")
    long countActiveUsers();

    /**
     * Conta o número de usuários por role
     * @param role Role do usuário
     * @return Número de usuários com a role especificada
     */
    long countByRole(User.Role role);

    /**
     * Busca usuários com agendamentos
     * @return Lista de usuários que possuem pelo menos um agendamento
     */
    @Query("SELECT DISTINCT u FROM User u JOIN u.bookings b")
    List<User> findUsersWithBookings();
}

