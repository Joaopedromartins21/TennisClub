package com.joaopedroaguiar.tennisclub.controller;

import com.joaopedroaguiar.tennisclub.dto.CourtDTO;
import com.joaopedroaguiar.tennisclub.service.CourtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Controlador CourtController - API REST para operações com quadras
 * Sistema TennisClub - Desenvolvido por: João Pedro Aguiar
 * 
 * Este controlador fornece endpoints para gerenciamento de quadras de tênis,
 * incluindo operações CRUD e consultas específicas.
 */
@RestController
@RequestMapping("/courts")
@Tag(name = "Quadras", description = "API para gerenciamento de quadras de tênis - João Pedro Aguiar TennisClub")
@CrossOrigin(origins = "*")
public class CourtController {

    @Autowired
    private CourtService courtService;

    @Operation(summary = "Criar nova quadra", 
               description = "Cria uma nova quadra de tênis no sistema - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Quadra criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<CourtDTO> createCourt(@Valid @RequestBody CourtDTO courtDTO) {
        CourtDTO createdCourt = courtService.createCourt(courtDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourt);
    }

    @Operation(summary = "Listar todas as quadras", 
               description = "Retorna lista de todas as quadras cadastradas - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Lista de quadras retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<CourtDTO>> getAllCourts() {
        List<CourtDTO> courts = courtService.getAllCourts();
        return ResponseEntity.ok(courts);
    }

    @Operation(summary = "Listar quadras ativas", 
               description = "Retorna lista de quadras ativas disponíveis para agendamento - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Lista de quadras ativas retornada com sucesso")
    @GetMapping("/active")
    public ResponseEntity<List<CourtDTO>> getActiveCourts() {
        List<CourtDTO> courts = courtService.getActiveCourts();
        return ResponseEntity.ok(courts);
    }

    @Operation(summary = "Listar quadras ativas por preço", 
               description = "Retorna quadras ativas ordenadas por preço crescente - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Lista de quadras ordenadas por preço retornada com sucesso")
    @GetMapping("/active/by-price")
    public ResponseEntity<List<CourtDTO>> getActiveCourtsByPrice() {
        List<CourtDTO> courts = courtService.getActiveCourtsByPrice();
        return ResponseEntity.ok(courts);
    }

    @Operation(summary = "Listar quadras ativas por nome", 
               description = "Retorna quadras ativas ordenadas por nome - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Lista de quadras ordenadas por nome retornada com sucesso")
    @GetMapping("/active/by-name")
    public ResponseEntity<List<CourtDTO>> getActiveCourtsOrderByName() {
        List<CourtDTO> courts = courtService.getActiveCourtsOrderByName();
        return ResponseEntity.ok(courts);
    }

    @Operation(summary = "Buscar quadra por ID", 
               description = "Retorna uma quadra específica pelo ID - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Quadra encontrada"),
        @ApiResponse(responseCode = "404", description = "Quadra não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CourtDTO> getCourtById(
            @Parameter(description = "ID da quadra") @PathVariable Long id) {
        try {
            CourtDTO court = courtService.getCourtById(id);
            return ResponseEntity.ok(court);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar quadras por nome", 
               description = "Retorna quadras que contenham o nome especificado - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Lista de quadras retornada com sucesso")
    @GetMapping("/search")
    public ResponseEntity<List<CourtDTO>> getCourtsByName(
            @Parameter(description = "Nome ou parte do nome da quadra") @RequestParam String name) {
        List<CourtDTO> courts = courtService.getCourtsByName(name);
        return ResponseEntity.ok(courts);
    }

    @Operation(summary = "Buscar quadras por faixa de preço", 
               description = "Retorna quadras dentro da faixa de preço especificada - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Lista de quadras retornada com sucesso")
    @GetMapping("/price-range")
    public ResponseEntity<List<CourtDTO>> getCourtsByPriceRange(
            @Parameter(description = "Preço mínimo por hora") @RequestParam BigDecimal minPrice,
            @Parameter(description = "Preço máximo por hora") @RequestParam BigDecimal maxPrice) {
        List<CourtDTO> courts = courtService.getCourtsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(courts);
    }

    @Operation(summary = "Buscar quadras por preço máximo", 
               description = "Retorna quadras com preço menor ou igual ao especificado - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Lista de quadras retornada com sucesso")
    @GetMapping("/max-price")
    public ResponseEntity<List<CourtDTO>> getCourtsByMaxPrice(
            @Parameter(description = "Preço máximo por hora") @RequestParam BigDecimal maxPrice) {
        List<CourtDTO> courts = courtService.getCourtsByMaxPrice(maxPrice);
        return ResponseEntity.ok(courts);
    }

    @Operation(summary = "Buscar quadra mais barata", 
               description = "Retorna a quadra ativa com menor preço por hora - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Quadra mais barata encontrada"),
        @ApiResponse(responseCode = "404", description = "Nenhuma quadra ativa encontrada")
    })
    @GetMapping("/cheapest")
    public ResponseEntity<CourtDTO> getCheapestCourt() {
        try {
            CourtDTO court = courtService.getCheapestCourt();
            return ResponseEntity.ok(court);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Atualizar quadra", 
               description = "Atualiza os dados de uma quadra existente - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Quadra atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Quadra não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CourtDTO> updateCourt(
            @Parameter(description = "ID da quadra") @PathVariable Long id,
            @Valid @RequestBody CourtDTO courtDTO) {
        try {
            CourtDTO updatedCourt = courtService.updateCourt(id, courtDTO);
            return ResponseEntity.ok(updatedCourt);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Ativar/Desativar quadra", 
               description = "Altera o status ativo de uma quadra - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status da quadra alterado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Quadra não encontrada")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<CourtDTO> toggleCourtStatus(
            @Parameter(description = "ID da quadra") @PathVariable Long id,
            @RequestBody Map<String, Boolean> statusRequest) {
        try {
            Boolean isActive = statusRequest.get("isActive");
            CourtDTO updatedCourt = courtService.toggleCourtStatus(id, isActive);
            return ResponseEntity.ok(updatedCourt);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Excluir quadra", 
               description = "Remove uma quadra do sistema - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Quadra excluída com sucesso"),
        @ApiResponse(responseCode = "404", description = "Quadra não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourt(
            @Parameter(description = "ID da quadra") @PathVariable Long id) {
        try {
            courtService.deleteCourt(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Contar quadras ativas", 
               description = "Retorna o número total de quadras ativas - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Contagem retornada com sucesso")
    @GetMapping("/count/active")
    public ResponseEntity<Map<String, Long>> countActiveCourts() {
        long count = courtService.countActiveCourts();
        return ResponseEntity.ok(Map.of("count", count));
    }

    @Operation(summary = "Listar quadras com agendamentos", 
               description = "Retorna quadras que possuem agendamentos - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Lista de quadras retornada com sucesso")
    @GetMapping("/with-bookings")
    public ResponseEntity<List<CourtDTO>> getCourtsWithBookings() {
        List<CourtDTO> courts = courtService.getCourtsWithBookings();
        return ResponseEntity.ok(courts);
    }
}

