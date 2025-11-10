package com.goodman.api.domain.exception;

import java.util.UUID;

/**
 * Classe base para exceções de "caso já fechado".
 * Será tratada globalmente para retornar um HTTP 400 (Bad Request).
 * Usar 'abstract' força a criação de exceções mais específicas.
 */
public abstract class CasoJaFechadoException extends NegocioException {
    public CasoJaFechadoException (String message) {
        super(message);
    }

    public CasoJaFechadoException(UUID casoId) {
        this(String.format("O Caso de ID %d está fechado e não pode ser alterado", casoId));
    }
}
