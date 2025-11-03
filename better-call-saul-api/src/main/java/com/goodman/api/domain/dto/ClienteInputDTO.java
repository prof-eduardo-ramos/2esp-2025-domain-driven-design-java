package com.goodman.api.domain.dto;

public record ClienteInputDTO(
    String nome,
    String cpfCnpj,
    String email,
    String telefone
) {

}
