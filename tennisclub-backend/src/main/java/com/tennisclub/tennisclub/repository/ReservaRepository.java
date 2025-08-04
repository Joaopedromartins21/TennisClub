package com.tennisclub.tennisclub.repository;

import com.tennisclub.tennisclub.model.Reserva;
import com.tennisclub.tennisclub.model.Quadra;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByQuadraAndDataHoraAndStatus(Quadra quadra, LocalDateTime dataHora, String status);
    List<Reserva> findByQuadraId(Long quadraId);
    List<Reserva> findByJogadoresId(Long jogadorId);

}
