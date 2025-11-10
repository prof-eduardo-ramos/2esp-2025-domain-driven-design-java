package com.goodman.api.domain.exception;

/**
 * Classe base para exceções de "não encontrado".
 * Será tratada globalmente para retornar um HTTP 404 (Not Found).
 * Usar 'abstract' força a criação de exceções mais específicas.
 */
public class EntidadeNaoEncontradaException extends NegocioException {
    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }
}
