package com.goodman.api.controller.dto;

import java.util.UUID;

public record ClienteResumoDTO(
    UUID id,
    String nome,
    String cpfCnpj
) {

}
