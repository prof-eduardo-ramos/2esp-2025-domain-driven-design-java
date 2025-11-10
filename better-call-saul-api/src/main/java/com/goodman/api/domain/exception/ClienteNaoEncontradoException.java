package com.goodman.api.domain.exception;

import java.util.UUID;

public class ClienteNaoEncontradoException extends EntidadeNaoEncontradaException {
    public ClienteNaoEncontradoException(String message) {
        super(message);
    }

    public ClienteNaoEncontradoException(UUID clienteId) {
        this(String.format("Não foi possível encontrar o cliente com o ID: %d", clienteId));
    }
}
