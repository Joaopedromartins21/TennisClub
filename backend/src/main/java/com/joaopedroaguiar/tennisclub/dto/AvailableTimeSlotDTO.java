package com.joaopedroaguiar.tennisclub.dto;

import java.time.LocalTime;

/**
 * DTO AvailableTimeSlotDTO - Data Transfer Object para horários disponíveis
 * Sistema TennisClub - Desenvolvido por: João Pedro Aguiar
 */
public class AvailableTimeSlotDTO {

    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isAvailable;

    // Construtores
    public AvailableTimeSlotDTO() {}

    public AvailableTimeSlotDTO(LocalTime startTime, LocalTime endTime, boolean isAvailable) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = isAvailable;
    }

    // Getters e Setters
    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "AvailableTimeSlotDTO{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", isAvailable=" + isAvailable +
                '}';
    }
}

