package com.tennisclub.tennisclub.dto;

import java.time.LocalDateTime;

public record ReservaDTO(
    Long quadraId,
    Long jogadorId,
    LocalDateTime dataHora
) {}
