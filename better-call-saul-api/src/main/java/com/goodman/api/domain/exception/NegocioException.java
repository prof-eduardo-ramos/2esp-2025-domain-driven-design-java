package com.goodman.api.domain.exception;

/**
 * Exceção lançada quando uma regra de negócio é violada.
 * Será tratada globalmente para retornar um HTTP 400 (Bad Request).
 */
public class NegocioException extends RuntimeException {
    public NegocioException(String message) {
        super(message);
    }

    public NegocioException(String message, Throwable cause) {
        super(message, cause);
    }
}
