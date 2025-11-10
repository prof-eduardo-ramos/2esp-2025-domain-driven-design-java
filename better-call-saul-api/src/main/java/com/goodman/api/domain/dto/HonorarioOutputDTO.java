package com.goodman.api.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record HonorarioOutputDTO(
    UUID id,
    String descricao,
    BigDecimal valor,
    LocalDate dataVencimento,
    boolean pago,
    LocalDate dataPagamento
) {

}
