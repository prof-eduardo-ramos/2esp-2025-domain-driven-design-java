package com.goodman.api.controller.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

/**
 * Classe padrão para corpos de erro da API, seguindo o padrão RFC 7807 (Problem Details).
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // Não inclui campos nulos no JSON
@Getter
@Builder // Padrão de projeto para facilitar a construção
public class ProblemDetails {
    private Integer status; // O status HTTP (ex: 400, 404)
    private OffsetDateTime timestamp; // Quando ocorreu
    private String type; // URI que identifica o tipo do erro (opcional)
    private String title; // Título legível (ex: "Entidade não encontrada")
    private String detail; // Mensagem específica (ex: "Cliente com ID 5 não foi encontrado")
    private List<Attribute> fields; // Lista de campos com erro (para validação)

    @Getter
    @Builder
    public static class Attribute {
        private String nome; // O nome do campo (ex: "cpfCnpj")
        private String mensagem; // A mensagem de erro (ex: "não deve estar em branco")
    }
}
