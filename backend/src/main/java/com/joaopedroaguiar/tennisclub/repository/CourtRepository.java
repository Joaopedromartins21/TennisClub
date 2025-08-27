package com.joaopedroaguiar.tennisclub.repository;

import com.joaopedroaguiar.tennisclub.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repositório CourtRepository - Interface para operações de dados das quadras
 * Sistema TennisClub - Desenvolvido por: João Pedro Aguiar
 */
@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {

    /**
     * Busca quadras pelo nome (busca parcial, case-insensitive)
     * @param name Nome ou parte do nome da quadra
     * @return Lista de quadras encontradas
     */
    @Query("SELECT c FROM Court c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Court> findByNameContainingIgnoreCase(@Param("name") String name);

    /**
     * Busca quadras ativas
     * @param isActive Status ativo
     * @return Lista de quadras ativas ou inativas
     */
    List<Court> findByIsActive(Boolean isActive);

    /**
     * Busca quadras por faixa de preço
     * @param minPrice Preço mínimo por hora
     * @param maxPrice Preço máximo por hora
     * @return Lista de quadras na faixa de preço especificada
     */
    @Query("SELECT c FROM Court c WHERE c.pricePerHour BETWEEN :minPrice AND :maxPrice")
    List<Court> findByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

    /**
     * Busca quadras ativas ordenadas por preço
     * @return Lista de quadras ativas ordenadas por preço crescente
     */
    @Query("SELECT c FROM Court c WHERE c.isActive = true ORDER BY c.pricePerHour ASC")
    List<Court> findActiveCourtsByPriceAsc();

    /**
     * Busca quadras ativas ordenadas por nome
     * @return Lista de quadras ativas ordenadas por nome
     */
    @Query("SELECT c FROM Court c WHERE c.isActive = true ORDER BY c.name ASC")
    List<Court> findActiveCourtsOrderByName();

    /**
     * Conta o número total de quadras ativas
     * @return Número de quadras ativas
     */
    @Query("SELECT COUNT(c) FROM Court c WHERE c.isActive = true")
    long countActiveCourts();

    /**
     * Busca quadras com agendamentos
     * @return Lista de quadras que possuem pelo menos um agendamento
     */
    @Query("SELECT DISTINCT c FROM Court c JOIN c.bookings b")
    List<Court> findCourtsWithBookings();

    /**
     * Busca a quadra mais barata ativa
     * @return Quadra com menor preço por hora
     */
    @Query("SELECT c FROM Court c WHERE c.isActive = true ORDER BY c.pricePerHour ASC LIMIT 1")
    Court findCheapestActiveCourt();

    /**
     * Busca quadras por preço máximo
     * @param maxPrice Preço máximo por hora
     * @return Lista de quadras com preço menor ou igual ao especificado
     */
    @Query("SELECT c FROM Court c WHERE c.isActive = true AND c.pricePerHour <= :maxPrice ORDER BY c.pricePerHour ASC")
    List<Court> findActiveCourtsWithMaxPrice(@Param("maxPrice") BigDecimal maxPrice);
}

