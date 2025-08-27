package com.joaopedroaguiar.tennisclub.repository;

import com.joaopedroaguiar.tennisclub.model.Booking;
import com.joaopedroaguiar.tennisclub.model.Court;
import com.joaopedroaguiar.tennisclub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Repositório BookingRepository - Interface para operações de dados dos agendamentos
 * Sistema TennisClub - Desenvolvido por: João Pedro Aguiar
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Busca agendamentos por usuário
     * @param user Usuário
     * @return Lista de agendamentos do usuário
     */
    List<Booking> findByUser(User user);

    /**
     * Busca agendamentos por usuário ordenados por data e hora
     * @param user Usuário
     * @return Lista de agendamentos do usuário ordenados
     */
    @Query("SELECT b FROM Booking b WHERE b.user = :user ORDER BY b.bookingDate DESC, b.startTime DESC")
    List<Booking> findByUserOrderByDateAndTime(@Param("user") User user);

    /**
     * Busca agendamentos por quadra
     * @param court Quadra
     * @return Lista de agendamentos da quadra
     */
    List<Booking> findByCourt(Court court);

    /**
     * Busca agendamentos por quadra e data
     * @param court Quadra
     * @param bookingDate Data do agendamento
     * @return Lista de agendamentos da quadra na data especificada
     */
    List<Booking> findByCourtAndBookingDate(Court court, LocalDate bookingDate);

    /**
     * Busca agendamentos por status
     * @param status Status do agendamento
     * @return Lista de agendamentos com o status especificado
     */
    List<Booking> findByStatus(Booking.BookingStatus status);

    /**
     * Busca agendamentos por data
     * @param bookingDate Data do agendamento
     * @return Lista de agendamentos na data especificada
     */
    List<Booking> findByBookingDate(LocalDate bookingDate);

    /**
     * Busca agendamentos em um período de datas
     * @param startDate Data inicial
     * @param endDate Data final
     * @return Lista de agendamentos no período especificado
     */
    @Query("SELECT b FROM Booking b WHERE b.bookingDate BETWEEN :startDate AND :endDate ORDER BY b.bookingDate, b.startTime")
    List<Booking> findByBookingDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * Verifica se existe conflito de horário para uma quadra em uma data específica
     * @param court Quadra
     * @param bookingDate Data do agendamento
     * @param startTime Horário de início
     * @param endTime Horário de fim
     * @return true se existe conflito, false caso contrário
     */
    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.court = :court AND b.bookingDate = :bookingDate " +
           "AND b.status IN ('PENDING', 'CONFIRMED') " +
           "AND ((b.startTime < :endTime AND b.endTime > :startTime))")
    boolean existsConflictingBooking(@Param("court") Court court, 
                                   @Param("bookingDate") LocalDate bookingDate,
                                   @Param("startTime") LocalTime startTime, 
                                   @Param("endTime") LocalTime endTime);

    /**
     * Verifica se existe conflito de horário excluindo um agendamento específico
     * @param court Quadra
     * @param bookingDate Data do agendamento
     * @param startTime Horário de início
     * @param endTime Horário de fim
     * @param excludeBookingId ID do agendamento a ser excluído da verificação
     * @return true se existe conflito, false caso contrário
     */
    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.court = :court AND b.bookingDate = :bookingDate " +
           "AND b.id != :excludeBookingId AND b.status IN ('PENDING', 'CONFIRMED') " +
           "AND ((b.startTime < :endTime AND b.endTime > :startTime))")
    boolean existsConflictingBookingExcluding(@Param("court") Court court, 
                                            @Param("bookingDate") LocalDate bookingDate,
                                            @Param("startTime") LocalTime startTime, 
                                            @Param("endTime") LocalTime endTime,
                                            @Param("excludeBookingId") Long excludeBookingId);

    /**
     * Busca agendamentos ativos (PENDING ou CONFIRMED) por usuário
     * @param user Usuário
     * @return Lista de agendamentos ativos do usuário
     */
    @Query("SELECT b FROM Booking b WHERE b.user = :user AND b.status IN ('PENDING', 'CONFIRMED') ORDER BY b.bookingDate, b.startTime")
    List<Booking> findActiveBookingsByUser(@Param("user") User user);

    /**
     * Busca agendamentos futuros por usuário
     * @param user Usuário
     * @param currentDate Data atual
     * @return Lista de agendamentos futuros do usuário
     */
    @Query("SELECT b FROM Booking b WHERE b.user = :user AND b.bookingDate >= :currentDate ORDER BY b.bookingDate, b.startTime")
    List<Booking> findFutureBookingsByUser(@Param("user") User user, @Param("currentDate") LocalDate currentDate);

    /**
     * Conta agendamentos por status
     * @param status Status do agendamento
     * @return Número de agendamentos com o status especificado
     */
    long countByStatus(Booking.BookingStatus status);

    /**
     * Busca agendamentos de hoje
     * @param today Data de hoje
     * @return Lista de agendamentos de hoje
     */
    @Query("SELECT b FROM Booking b WHERE b.bookingDate = :today ORDER BY b.startTime")
    List<Booking> findTodayBookings(@Param("today") LocalDate today);

    /**
     * Busca os próximos agendamentos de uma quadra
     * @param court Quadra
     * @param currentDate Data atual
     * @return Lista dos próximos agendamentos da quadra
     */
    @Query("SELECT b FROM Booking b WHERE b.court = :court AND b.bookingDate >= :currentDate " +
           "AND b.status IN ('PENDING', 'CONFIRMED') ORDER BY b.bookingDate, b.startTime LIMIT 10")
    List<Booking> findUpcomingBookingsByCourt(@Param("court") Court court, @Param("currentDate") LocalDate currentDate);
}

