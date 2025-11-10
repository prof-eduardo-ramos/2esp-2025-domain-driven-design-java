package com.goodman.api.controller.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.goodman.api.domain.exception.EntidadeNaoEncontradaException;
import com.goodman.api.domain.exception.NegocioException;

import lombok.RequiredArgsConstructor;

/**
 * Captura exceções globalmente e as traduz para respostas HTTP
 * com o corpo (Problema.java).
 */
@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    // Usado para internacionalização de mensagens de erro (opcional, mas boa prática)
    private final MessageSource messageSource;

    /**
     * Captura erros de validação (@Valid nos DTOs de entrada).
     * Retorna HTTP 400 (Bad Request).
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        // Constrói a lista de campos com erro
        List<ProblemDetails.Attribute> campos = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String name = (error instanceof FieldError) ? ((FieldError) error).getField() : error.getObjectName();
                    // Busca a mensagem de erro no messages.properties (internacionalização)
                    String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
                    return ProblemDetails.Attribute.builder().nome(name).mensagem(message).build();
                })
                .collect(Collectors.toList());

        ProblemDetails problema = ProblemDetails.builder()
                .status(status.value())
                .timestamp(OffsetDateTime.now())
                .title("Um ou mais campos estão inválidos")
                .detail("Faça o preenchimento correto e tente novamente.")
                .fields(campos)
                .build();

        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    /**
     * Captura nossas exceções de "não encontrado".
     * Retorna HTTP 404 (Not Found).
     */
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemDetails problema = ProblemDetails.builder()
                .status(status.value())
                .timestamp(OffsetDateTime.now())
                .title("Recurso não encontrado")
                .detail(ex.getMessage()) // Mensagem da exceção (ex: "Cliente com ID 5 não foi encontrado")
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    /**
     * Captura nossas exceções de "regra de negócio".
     * Retorna HTTP 400 (Bad Request).
     */
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemDetails problema = ProblemDetails.builder()
                .status(status.value())
                .timestamp(OffsetDateTime.now())
                .title("Violação de regra de negócio")
                .detail(ex.getMessage()) // (ex: "CPF/CNPJ já cadastrado")
                .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    /**
     * Captura qualquer outra exceção não tratada.
     * Retorna HTTP 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemDetails problema = ProblemDetails.builder()
                .status(status.value())
                .timestamp(OffsetDateTime.now())
                .title("Erro interno do servidor")
                .detail("Ocorreu um erro interno inesperado. Tente novamente mais tarde.")
                .build();

        // É importante logar o erro original no console/logs
        ex.printStackTrace();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }
}
