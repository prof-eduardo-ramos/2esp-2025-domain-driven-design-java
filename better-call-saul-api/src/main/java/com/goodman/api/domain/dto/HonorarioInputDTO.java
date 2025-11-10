package com.goodman.api.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO para ENTRADA (Criação) de Honorários.
 */
public record HonorarioInputDTO(
    @NotBlank
    String descricao,

    @NotNull
    @Positive // Valor deve ser maior que zero
    BigDecimal valor,

    @NotNull
    @FutureOrPresent // Data de vencimento não pode ser no passado
    LocalDate dataVencimento
) {
}
