package com.joaopedroaguiar.tennisclub.service;

import com.joaopedroaguiar.tennisclub.dto.AvailableTimeSlotDTO;
import com.joaopedroaguiar.tennisclub.dto.BookingDTO;
import com.joaopedroaguiar.tennisclub.model.Booking;
import com.joaopedroaguiar.tennisclub.model.Court;
import com.joaopedroaguiar.tennisclub.model.User;
import com.joaopedroaguiar.tennisclub.repository.BookingRepository;
import com.joaopedroaguiar.tennisclub.repository.CourtRepository;
import com.joaopedroaguiar.tennisclub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço BookingService - Lógica de negócios para operações com agendamentos
 * Sistema TennisClub - Desenvolvido por: João Pedro Aguiar
 */
@Service
@Transactional
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private UserRepository userRepository;

    // Horários de funcionamento do clube
    private static final LocalTime OPENING_TIME = LocalTime.of(6, 0);  // 06:00
    private static final LocalTime CLOSING_TIME = LocalTime.of(22, 0); // 22:00

    /**
     * Cria um novo agendamento
     * @param bookingDTO Dados do agendamento a ser criado
     * @return DTO do agendamento criado
     * @throws RuntimeException se houver conflito de horário ou dados inválidos
     */
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        // Busca a quadra
        Court court = courtRepository.findById(bookingDTO.getCourtId())
                .orElseThrow(() -> new RuntimeException("Quadra não encontrada com ID: " + bookingDTO.getCourtId()));

        // Busca o usuário
        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + bookingDTO.getUserId()));

        // Validações
        validateBookingTime(bookingDTO.getBookingDate(), bookingDTO.getStartTime(), bookingDTO.getEndTime());
        validateCourtAvailability(court, bookingDTO.getBookingDate(), bookingDTO.getStartTime(), bookingDTO.getEndTime(), null);

        // Calcula o preço total
        BigDecimal totalPrice = calculateTotalPrice(court, bookingDTO.getStartTime(), bookingDTO.getEndTime());

        // Cria o agendamento
        Booking booking = new Booking();
        booking.setCourt(court);
        booking.setUser(user);
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setStartTime(bookingDTO.getStartTime());
        booking.setEndTime(bookingDTO.getEndTime());
        booking.setTotalPrice(totalPrice);
        booking.setStatus(Booking.BookingStatus.PENDING);
        booking.setNotes(bookingDTO.getNotes());

        Booking savedBooking = bookingRepository.save(booking);
        return new BookingDTO(savedBooking);
    }

    /**
     * Busca todos os agendamentos
     * @return Lista de DTOs dos agendamentos
     */
    @Transactional(readOnly = true)
    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(BookingDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca agendamento por ID
     * @param id ID do agendamento
     * @return DTO do agendamento encontrado
     * @throws RuntimeException se o agendamento não for encontrado
     */
    @Transactional(readOnly = true)
    public BookingDTO getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado com ID: " + id));
        return new BookingDTO(booking);
    }

    /**
     * Busca agendamentos por usuário
     * @param userId ID do usuário
     * @return Lista de DTOs dos agendamentos do usuário
     */
    @Transactional(readOnly = true)
    public List<BookingDTO> getBookingsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));

        return bookingRepository.findByUserOrderByDateAndTime(user)
                .stream()
                .map(BookingDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca agendamentos por quadra
     * @param courtId ID da quadra
     * @return Lista de DTOs dos agendamentos da quadra
     */
    @Transactional(readOnly = true)
    public List<BookingDTO> getBookingsByCourt(Long courtId) {
        Court court = courtRepository.findById(courtId)
                .orElseThrow(() -> new RuntimeException("Quadra não encontrada com ID: " + courtId));

        return bookingRepository.findByCourt(court)
                .stream()
                .map(BookingDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca agendamentos por data
     * @param date Data dos agendamentos
     * @return Lista de DTOs dos agendamentos na data especificada
     */
    @Transactional(readOnly = true)
    public List<BookingDTO> getBookingsByDate(LocalDate date) {
        return bookingRepository.findByBookingDate(date)
                .stream()
                .map(BookingDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca agendamentos por status
     * @param status Status dos agendamentos
     * @return Lista de DTOs dos agendamentos com o status especificado
     */
    @Transactional(readOnly = true)
    public List<BookingDTO> getBookingsByStatus(Booking.BookingStatus status) {
        return bookingRepository.findByStatus(status)
                .stream()
                .map(BookingDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca agendamentos futuros por usuário
     * @param userId ID do usuário
     * @return Lista de DTOs dos agendamentos futuros do usuário
     */
    @Transactional(readOnly = true)
    public List<BookingDTO> getFutureBookingsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + userId));

        return bookingRepository.findFutureBookingsByUser(user, LocalDate.now())
                .stream()
                .map(BookingDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca agendamentos de hoje
     * @return Lista de DTOs dos agendamentos de hoje
     */
    @Transactional(readOnly = true)
    public List<BookingDTO> getTodayBookings() {
        return bookingRepository.findTodayBookings(LocalDate.now())
                .stream()
                .map(BookingDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca horários disponíveis para uma quadra em uma data específica
     * @param courtId ID da quadra
     * @param date Data para verificar disponibilidade
     * @return Lista de horários disponíveis
     */
    @Transactional(readOnly = true)
    public List<AvailableTimeSlotDTO> getAvailableTimeSlots(Long courtId, LocalDate date) {
        Court court = courtRepository.findById(courtId)
                .orElseThrow(() -> new RuntimeException("Quadra não encontrada com ID: " + courtId));

        List<Booking> existingBookings = bookingRepository.findByCourtAndBookingDate(court, date);
        List<AvailableTimeSlotDTO> availableSlots = new ArrayList<>();

        // Gera slots de 1 hora das 6h às 22h
        LocalTime currentTime = OPENING_TIME;
        while (currentTime.isBefore(CLOSING_TIME)) {
            LocalTime endTime = currentTime.plusHours(1);
            
            boolean isAvailable = existingBookings.stream()
                    .filter(booking -> booking.getStatus() == Booking.BookingStatus.PENDING || 
                                     booking.getStatus() == Booking.BookingStatus.CONFIRMED)
                    .noneMatch(booking -> 
                        (booking.getStartTime().isBefore(endTime) && booking.getEndTime().isAfter(currentTime))
                    );

            availableSlots.add(new AvailableTimeSlotDTO(currentTime, endTime, isAvailable));
            currentTime = endTime;
        }

        return availableSlots;
    }

    /**
     * Atualiza um agendamento existente
     * @param id ID do agendamento
     * @param bookingDTO Dados atualizados do agendamento
     * @return DTO do agendamento atualizado
     * @throws RuntimeException se o agendamento não for encontrado ou houver conflito
     */
    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado com ID: " + id));

        // Validações
        validateBookingTime(bookingDTO.getBookingDate(), bookingDTO.getStartTime(), bookingDTO.getEndTime());
        validateCourtAvailability(booking.getCourt(), bookingDTO.getBookingDate(), 
                                bookingDTO.getStartTime(), bookingDTO.getEndTime(), id);

        // Recalcula o preço se o horário mudou
        if (!booking.getStartTime().equals(bookingDTO.getStartTime()) || 
            !booking.getEndTime().equals(bookingDTO.getEndTime())) {
            BigDecimal newPrice = calculateTotalPrice(booking.getCourt(), 
                                                    bookingDTO.getStartTime(), bookingDTO.getEndTime());
            booking.setTotalPrice(newPrice);
        }

        // Atualiza os dados
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setStartTime(bookingDTO.getStartTime());
        booking.setEndTime(bookingDTO.getEndTime());
        booking.setNotes(bookingDTO.getNotes());

        Booking updatedBooking = bookingRepository.save(booking);
        return new BookingDTO(updatedBooking);
    }

    /**
     * Atualiza o status de um agendamento
     * @param id ID do agendamento
     * @param status Novo status
     * @return DTO do agendamento atualizado
     * @throws RuntimeException se o agendamento não for encontrado
     */
    public BookingDTO updateBookingStatus(Long id, Booking.BookingStatus status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado com ID: " + id));

        booking.setStatus(status);
        Booking updatedBooking = bookingRepository.save(booking);
        return new BookingDTO(updatedBooking);
    }

    /**
     * Cancela um agendamento
     * @param id ID do agendamento
     * @return DTO do agendamento cancelado
     * @throws RuntimeException se o agendamento não for encontrado
     */
    public BookingDTO cancelBooking(Long id) {
        return updateBookingStatus(id, Booking.BookingStatus.CANCELED);
    }

    /**
     * Confirma um agendamento
     * @param id ID do agendamento
     * @return DTO do agendamento confirmado
     * @throws RuntimeException se o agendamento não for encontrado
     */
    public BookingDTO confirmBooking(Long id) {
        return updateBookingStatus(id, Booking.BookingStatus.CONFIRMED);
    }

    /**
     * Exclui um agendamento
     * @param id ID do agendamento
     * @throws RuntimeException se o agendamento não for encontrado
     */
    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado com ID: " + id));

        bookingRepository.delete(booking);
    }

    /**
     * Conta agendamentos por status
     * @param status Status dos agendamentos
     * @return Número de agendamentos com o status especificado
     */
    @Transactional(readOnly = true)
    public long countBookingsByStatus(Booking.BookingStatus status) {
        return bookingRepository.countByStatus(status);
    }

    // Métodos privados de validação e cálculo

    private void validateBookingTime(LocalDate date, LocalTime startTime, LocalTime endTime) {
        // Verifica se a data não é no passado
        if (date.isBefore(LocalDate.now())) {
            throw new RuntimeException("Não é possível agendar para datas passadas");
        }

        // Verifica se o horário de início é antes do fim
        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new RuntimeException("Horário de início deve ser anterior ao horário de fim");
        }

        // Verifica se está dentro do horário de funcionamento
        if (startTime.isBefore(OPENING_TIME) || endTime.isAfter(CLOSING_TIME)) {
            throw new RuntimeException("Agendamento deve estar entre " + OPENING_TIME + " e " + CLOSING_TIME);
        }

        // Verifica duração mínima (1 hora)
        if (Duration.between(startTime, endTime).toHours() < 1) {
            throw new RuntimeException("Duração mínima do agendamento é de 1 hora");
        }
    }

    private void validateCourtAvailability(Court court, LocalDate date, LocalTime startTime, LocalTime endTime, Long excludeBookingId) {
        boolean hasConflict;
        
        if (excludeBookingId != null) {
            hasConflict = bookingRepository.existsConflictingBookingExcluding(court, date, startTime, endTime, excludeBookingId);
        } else {
            hasConflict = bookingRepository.existsConflictingBooking(court, date, startTime, endTime);
        }

        if (hasConflict) {
            throw new RuntimeException("Já existe um agendamento para este horário na quadra " + court.getName());
        }
    }

    private BigDecimal calculateTotalPrice(Court court, LocalTime startTime, LocalTime endTime) {
        long hours = Duration.between(startTime, endTime).toHours();
        return court.getPricePerHour().multiply(BigDecimal.valueOf(hours));
    }
}

