package com.goodman.api.domain.exception;

import java.util.UUID;

public class HonorarioNaoEncontradoException extends EntidadeNaoEncontradaException {
    public HonorarioNaoEncontradoException(String message) {
        super(message);
    }

    public HonorarioNaoEncontradoException(UUID honorarioId) {
        this(String.format("Não foi possível encontrar o honorário com o ID: %d", honorarioId));
    }
}
