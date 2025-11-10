package com.goodman.api.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteInputDTO(
    @NotBlank
    @Size(min = 3, max = 150)
    String nome,

    @NotBlank
    @Size(min = 11, max = 18) // Permite CPF ou CNPJ, com ou sem máscara
    String cpfCnpj,

    @Email
    @Size(max = 100)
    String email,

    @Size(max = 20)
    String telefone
) {
}
