package com.goodman.api.domain.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.goodman.api.domain.model.StatusCaso;

public record CasoOutputDTO(
    UUID id,
    String numeroProcesso,
    String titulo,
    String descricao,
    LocalDate dataAbertura,
    LocalDate dataFechamento,
    StatusCaso status,
    ClienteResumoDTO cliente // Usa o DTO de resumo
) {

}
