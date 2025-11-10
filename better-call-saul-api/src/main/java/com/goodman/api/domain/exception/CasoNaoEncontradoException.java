package com.goodman.api.domain.exception;

import java.util.UUID;

public class CasoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public CasoNaoEncontradoException(String message) {
        super(message);
    }

    public CasoNaoEncontradoException(UUID casoId) {
        this(String.format("Não foi possível encontrar o caso com o ID: %d", casoId));
    }
}
