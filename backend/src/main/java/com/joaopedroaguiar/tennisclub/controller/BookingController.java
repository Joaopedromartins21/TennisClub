package com.joaopedroaguiar.tennisclub.controller;

import com.joaopedroaguiar.tennisclub.dto.AvailableTimeSlotDTO;
import com.joaopedroaguiar.tennisclub.dto.BookingDTO;
import com.joaopedroaguiar.tennisclub.model.Booking;
import com.joaopedroaguiar.tennisclub.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Controlador BookingController - API REST para operações com agendamentos
 * Sistema TennisClub - Desenvolvido por: João Pedro Aguiar
 * 
 * Este controlador fornece endpoints para gerenciamento de agendamentos de quadras,
 * incluindo operações CRUD, consultas específicas e verificação de disponibilidade.
 */
@RestController
@RequestMapping("/bookings")
@Tag(name = "Agendamentos", description = "API para gerenciamento de agendamentos de quadras - João Pedro Aguiar TennisClub")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Operation(summary = "Criar novo agendamento", 
               description = "Cria um novo agendamento de quadra no sistema - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Agendamento criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou conflito de horário"),
        @ApiResponse(responseCode = "404", description = "Quadra ou usuário não encontrado")
    })
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@Valid @RequestBody BookingDTO bookingDTO) {
        try {
            BookingDTO createdBooking = bookingService.createBooking(bookingDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Listar todos os agendamentos", 
               description = "Retorna lista de todos os agendamentos cadastrados - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @Operation(summary = "Buscar agendamento por ID", 
               description = "Retorna um agendamento específico pelo ID - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamento encontrado"),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(
            @Parameter(description = "ID do agendamento") @PathVariable Long id) {
        try {
            BookingDTO booking = bookingService.getBookingById(id);
            return ResponseEntity.ok(booking);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar agendamentos por usuário", 
               description = "Retorna agendamentos de um usuário específico - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByUser(
            @Parameter(description = "ID do usuário") @PathVariable Long userId) {
        try {
            List<BookingDTO> bookings = bookingService.getBookingsByUser(userId);
            return ResponseEntity.ok(bookings);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar agendamentos por quadra", 
               description = "Retorna agendamentos de uma quadra específica - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Quadra não encontrada")
    })
    @GetMapping("/court/{courtId}")
    public ResponseEntity<List<BookingDTO>> getBookingsByCourt(
            @Parameter(description = "ID da quadra") @PathVariable Long courtId) {
        try {
            List<BookingDTO> bookings = bookingService.getBookingsByCourt(courtId);
            return ResponseEntity.ok(bookings);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar agendamentos por data", 
               description = "Retorna agendamentos de uma data específica - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso")
    @GetMapping("/date/{date}")
    public ResponseEntity<List<BookingDTO>> getBookingsByDate(
            @Parameter(description = "Data dos agendamentos (formato: yyyy-MM-dd)") 
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<BookingDTO> bookings = bookingService.getBookingsByDate(date);
        return ResponseEntity.ok(bookings);
    }

    @Operation(summary = "Buscar agendamentos por status", 
               description = "Retorna agendamentos com status específico - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Lista de agendamentos retornada com sucesso")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<BookingDTO>> getBookingsByStatus(
            @Parameter(description = "Status do agendamento") @PathVariable Booking.BookingStatus status) {
        List<BookingDTO> bookings = bookingService.getBookingsByStatus(status);
        return ResponseEntity.ok(bookings);
    }

    @Operation(summary = "Buscar agendamentos futuros por usuário", 
               description = "Retorna agendamentos futuros de um usuário específico - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de agendamentos futuros retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/user/{userId}/future")
    public ResponseEntity<List<BookingDTO>> getFutureBookingsByUser(
            @Parameter(description = "ID do usuário") @PathVariable Long userId) {
        try {
            List<BookingDTO> bookings = bookingService.getFutureBookingsByUser(userId);
            return ResponseEntity.ok(bookings);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar agendamentos de hoje", 
               description = "Retorna todos os agendamentos do dia atual - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Lista de agendamentos de hoje retornada com sucesso")
    @GetMapping("/today")
    public ResponseEntity<List<BookingDTO>> getTodayBookings() {
        List<BookingDTO> bookings = bookingService.getTodayBookings();
        return ResponseEntity.ok(bookings);
    }

    @Operation(summary = "Verificar horários disponíveis", 
               description = "Retorna horários disponíveis para uma quadra em uma data específica - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de horários disponíveis retornada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Quadra não encontrada")
    })
    @GetMapping("/available-times")
    public ResponseEntity<List<AvailableTimeSlotDTO>> getAvailableTimeSlots(
            @Parameter(description = "ID da quadra") @RequestParam Long courtId,
            @Parameter(description = "Data para verificar disponibilidade (formato: yyyy-MM-dd)") 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<AvailableTimeSlotDTO> availableSlots = bookingService.getAvailableTimeSlots(courtId, date);
            return ResponseEntity.ok(availableSlots);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Atualizar agendamento", 
               description = "Atualiza os dados de um agendamento existente - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou conflito de horário")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookingDTO> updateBooking(
            @Parameter(description = "ID do agendamento") @PathVariable Long id,
            @Valid @RequestBody BookingDTO bookingDTO) {
        try {
            BookingDTO updatedBooking = bookingService.updateBooking(id, bookingDTO);
            return ResponseEntity.ok(updatedBooking);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Atualizar status do agendamento", 
               description = "Altera o status de um agendamento - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status do agendamento alterado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(
            @Parameter(description = "ID do agendamento") @PathVariable Long id,
            @RequestBody Map<String, Booking.BookingStatus> statusRequest) {
        try {
            Booking.BookingStatus status = statusRequest.get("status");
            BookingDTO updatedBooking = bookingService.updateBookingStatus(id, status);
            return ResponseEntity.ok(updatedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Cancelar agendamento", 
               description = "Cancela um agendamento específico - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamento cancelado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingDTO> cancelBooking(
            @Parameter(description = "ID do agendamento") @PathVariable Long id) {
        try {
            BookingDTO canceledBooking = bookingService.cancelBooking(id);
            return ResponseEntity.ok(canceledBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Confirmar agendamento", 
               description = "Confirma um agendamento específico - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Agendamento confirmado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    @PatchMapping("/{id}/confirm")
    public ResponseEntity<BookingDTO> confirmBooking(
            @Parameter(description = "ID do agendamento") @PathVariable Long id) {
        try {
            BookingDTO confirmedBooking = bookingService.confirmBooking(id);
            return ResponseEntity.ok(confirmedBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Excluir agendamento", 
               description = "Remove um agendamento do sistema - João Pedro Aguiar TennisClub")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Agendamento excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(
            @Parameter(description = "ID do agendamento") @PathVariable Long id) {
        try {
            bookingService.deleteBooking(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Contar agendamentos por status", 
               description = "Retorna o número de agendamentos com status específico - João Pedro Aguiar TennisClub")
    @ApiResponse(responseCode = "200", description = "Contagem retornada com sucesso")
    @GetMapping("/count/status/{status}")
    public ResponseEntity<Map<String, Long>> countBookingsByStatus(
            @Parameter(description = "Status do agendamento") @PathVariable Booking.BookingStatus status) {
        long count = bookingService.countBookingsByStatus(status);
        return ResponseEntity.ok(Map.of("count", count));
    }
}

