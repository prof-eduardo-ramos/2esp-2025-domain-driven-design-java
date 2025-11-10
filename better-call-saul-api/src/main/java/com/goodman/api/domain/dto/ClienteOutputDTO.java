package com.goodman.api.domain.dto;

import java.util.UUID;

public record ClienteOutputDTO(
    UUID id,
    String nome,
    String cpfCnpj,
    String email,
    String telefone
) {

}
