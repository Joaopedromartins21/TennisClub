package com.joaopedroaguiar.tennisclub.dto;

import com.joaopedroaguiar.tennisclub.model.Booking;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO BookingDTO - Data Transfer Object para operações com agendamentos
 * Sistema TennisClub - Desenvolvido por: João Pedro Aguiar
 */
public class BookingDTO {

    private Long id;

    @NotNull(message = "ID da quadra é obrigatório")
    private Long courtId;
    private String courtName;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long userId;
    private String userName;

    @NotNull(message = "Data do agendamento é obrigatória")
    private LocalDate bookingDate;

    @NotNull(message = "Horário de início é obrigatório")
    private LocalTime startTime;

    @NotNull(message = "Horário de fim é obrigatório")
    private LocalTime endTime;

    private Booking.BookingStatus status;
    private BigDecimal totalPrice;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Construtores
    public BookingDTO() {}

    public BookingDTO(Booking booking) {
        this.id = booking.getId();
        this.courtId = booking.getCourt().getId();
        this.courtName = booking.getCourt().getName();
        this.userId = booking.getUser().getId();
        this.userName = booking.getUser().getName();
        this.bookingDate = booking.getBookingDate();
        this.startTime = booking.getStartTime();
        this.endTime = booking.getEndTime();
        this.status = booking.getStatus();
        this.totalPrice = booking.getTotalPrice();
        this.notes = booking.getNotes();
        this.createdAt = booking.getCreatedAt();
        this.updatedAt = booking.getUpdatedAt();
    }

    public BookingDTO(Long courtId, Long userId, LocalDate bookingDate, LocalTime startTime, LocalTime endTime) {
        this.courtId = courtId;
        this.userId = userId;
        this.bookingDate = bookingDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = Booking.BookingStatus.PENDING;
    }

    // Métodos utilitários
    public LocalDateTime getStartDateTime() {
        return LocalDateTime.of(bookingDate, startTime);
    }

    public LocalDateTime getEndDateTime() {
        return LocalDateTime.of(bookingDate, endTime);
    }

    public long getDurationInHours() {
        return java.time.Duration.between(startTime, endTime).toHours();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourtId() {
        return courtId;
    }

    public void setCourtId(Long courtId) {
        this.courtId = courtId;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

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

    public Booking.BookingStatus getStatus() {
        return status;
    }

    public void setStatus(Booking.BookingStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
        return "BookingDTO{" +
                "id=" + id +
                ", courtId=" + courtId +
                ", courtName='" + courtName + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", bookingDate=" + bookingDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", createdAt=" + createdAt +
                '}';
    }
}

