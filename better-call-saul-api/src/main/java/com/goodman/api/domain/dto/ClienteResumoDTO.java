package com.goodman.api.domain.dto;

import java.util.UUID;

public record ClienteResumoDTO(
    UUID id,
    String nome,
    String cpfCnpj
) {

}
