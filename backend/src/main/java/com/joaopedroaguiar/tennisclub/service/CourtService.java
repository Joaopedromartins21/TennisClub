package com.joaopedroaguiar.tennisclub.service;

import com.joaopedroaguiar.tennisclub.dto.CourtDTO;
import com.joaopedroaguiar.tennisclub.model.Court;
import com.joaopedroaguiar.tennisclub.repository.CourtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço CourtService - Lógica de negócios para operações com quadras
 * Sistema TennisClub - Desenvolvido por: João Pedro Aguiar
 */
@Service
@Transactional
public class CourtService {

    @Autowired
    private CourtRepository courtRepository;

    /**
     * Cria uma nova quadra
     * @param courtDTO Dados da quadra a ser criada
     * @return DTO da quadra criada
     */
    public CourtDTO createCourt(CourtDTO courtDTO) {
        Court court = new Court();
        court.setName(courtDTO.getName());
        court.setDescription(courtDTO.getDescription());
        court.setPricePerHour(courtDTO.getPricePerHour());
        court.setIsActive(true);

        Court savedCourt = courtRepository.save(court);
        return new CourtDTO(savedCourt);
    }

    /**
     * Busca todas as quadras
     * @return Lista de DTOs das quadras
     */
    @Transactional(readOnly = true)
    public List<CourtDTO> getAllCourts() {
        return courtRepository.findAll()
                .stream()
                .map(CourtDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca quadras ativas
     * @return Lista de DTOs das quadras ativas
     */
    @Transactional(readOnly = true)
    public List<CourtDTO> getActiveCourts() {
        return courtRepository.findByIsActive(true)
                .stream()
                .map(CourtDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca quadras ativas ordenadas por preço
     * @return Lista de DTOs das quadras ativas ordenadas por preço
     */
    @Transactional(readOnly = true)
    public List<CourtDTO> getActiveCourtsByPrice() {
        return courtRepository.findActiveCourtsByPriceAsc()
                .stream()
                .map(CourtDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca quadras ativas ordenadas por nome
     * @return Lista de DTOs das quadras ativas ordenadas por nome
     */
    @Transactional(readOnly = true)
    public List<CourtDTO> getActiveCourtsOrderByName() {
        return courtRepository.findActiveCourtsOrderByName()
                .stream()
                .map(CourtDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca quadra por ID
     * @param id ID da quadra
     * @return DTO da quadra encontrada
     * @throws RuntimeException se a quadra não for encontrada
     */
    @Transactional(readOnly = true)
    public CourtDTO getCourtById(Long id) {
        Court court = courtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quadra não encontrada com ID: " + id));
        return new CourtDTO(court);
    }

    /**
     * Busca quadras por nome (busca parcial)
     * @param name Nome ou parte do nome
     * @return Lista de DTOs das quadras encontradas
     */
    @Transactional(readOnly = true)
    public List<CourtDTO> getCourtsByName(String name) {
        return courtRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(CourtDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca quadras por faixa de preço
     * @param minPrice Preço mínimo por hora
     * @param maxPrice Preço máximo por hora
     * @return Lista de DTOs das quadras na faixa de preço
     */
    @Transactional(readOnly = true)
    public List<CourtDTO> getCourtsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return courtRepository.findByPriceRange(minPrice, maxPrice)
                .stream()
                .map(CourtDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca quadras com preço máximo
     * @param maxPrice Preço máximo por hora
     * @return Lista de DTOs das quadras com preço menor ou igual ao especificado
     */
    @Transactional(readOnly = true)
    public List<CourtDTO> getCourtsByMaxPrice(BigDecimal maxPrice) {
        return courtRepository.findActiveCourtsWithMaxPrice(maxPrice)
                .stream()
                .map(CourtDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca a quadra mais barata ativa
     * @return DTO da quadra mais barata
     */
    @Transactional(readOnly = true)
    public CourtDTO getCheapestCourt() {
        Court court = courtRepository.findCheapestActiveCourt();
        if (court == null) {
            throw new RuntimeException("Nenhuma quadra ativa encontrada");
        }
        return new CourtDTO(court);
    }

    /**
     * Atualiza uma quadra existente
     * @param id ID da quadra
     * @param courtDTO Dados atualizados da quadra
     * @return DTO da quadra atualizada
     * @throws RuntimeException se a quadra não for encontrada
     */
    public CourtDTO updateCourt(Long id, CourtDTO courtDTO) {
        Court court = courtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quadra não encontrada com ID: " + id));

        // Atualiza os dados
        court.setName(courtDTO.getName());
        court.setDescription(courtDTO.getDescription());
        court.setPricePerHour(courtDTO.getPricePerHour());
        if (courtDTO.getIsActive() != null) {
            court.setIsActive(courtDTO.getIsActive());
        }

        Court updatedCourt = courtRepository.save(court);
        return new CourtDTO(updatedCourt);
    }

    /**
     * Ativa ou desativa uma quadra
     * @param id ID da quadra
     * @param isActive Status ativo
     * @return DTO da quadra atualizada
     * @throws RuntimeException se a quadra não for encontrada
     */
    public CourtDTO toggleCourtStatus(Long id, Boolean isActive) {
        Court court = courtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quadra não encontrada com ID: " + id));

        court.setIsActive(isActive);
        Court updatedCourt = courtRepository.save(court);
        return new CourtDTO(updatedCourt);
    }

    /**
     * Exclui uma quadra
     * @param id ID da quadra
     * @throws RuntimeException se a quadra não for encontrada
     */
    public void deleteCourt(Long id) {
        Court court = courtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quadra não encontrada com ID: " + id));

        courtRepository.delete(court);
    }

    /**
     * Conta o número total de quadras ativas
     * @return Número de quadras ativas
     */
    @Transactional(readOnly = true)
    public long countActiveCourts() {
        return courtRepository.countActiveCourts();
    }

    /**
     * Busca quadras com agendamentos
     * @return Lista de DTOs das quadras que possuem agendamentos
     */
    @Transactional(readOnly = true)
    public List<CourtDTO> getCourtsWithBookings() {
        return courtRepository.findCourtsWithBookings()
                .stream()
                .map(CourtDTO::new)
                .collect(Collectors.toList());
    }
}

