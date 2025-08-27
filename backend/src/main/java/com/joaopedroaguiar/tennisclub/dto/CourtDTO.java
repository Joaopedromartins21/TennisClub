package com.joaopedroaguiar.tennisclub.dto;

import com.joaopedroaguiar.tennisclub.model.Court;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO CourtDTO - Data Transfer Object para operações com quadras
 * Sistema TennisClub - Desenvolvido por: João Pedro Aguiar
 */
public class CourtDTO {

    private Long id;

    @NotBlank(message = "Nome da quadra é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String name;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String description;

    @NotNull(message = "Preço por hora é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero")
    private BigDecimal pricePerHour;

    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Construtores
    public CourtDTO() {}

    public CourtDTO(Court court) {
        this.id = court.getId();
        this.name = court.getName();
        this.description = court.getDescription();
        this.pricePerHour = court.getPricePerHour();
        this.isActive = court.getIsActive();
        this.createdAt = court.getCreatedAt();
        this.updatedAt = court.getUpdatedAt();
    }

    public CourtDTO(String name, String description, BigDecimal pricePerHour) {
        this.name = name;
        this.description = description;
        this.pricePerHour = pricePerHour;
        this.isActive = true;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "CourtDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", pricePerHour=" + pricePerHour +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
}

