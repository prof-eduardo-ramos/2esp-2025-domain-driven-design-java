package com.goodman.api.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CasoInputDTO(
    @NotBlank
    @Size(max = 30)
    String numeroProcesso,

    @Size(max = 200)
    String titulo,

    String descricao
) {

}
