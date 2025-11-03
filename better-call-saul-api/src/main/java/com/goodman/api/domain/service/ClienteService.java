package com.goodman.api.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.goodman.api.domain.model.Cliente;

@Service
public class ClienteService {

    public List<Cliente> listarTodos() {
        return
            List.of(
                new Cliente(
                    "Eduardo Ramos",
                    "123.456.789-10",
                    "profeduardo.ramos@fiap.com.br",
                    "+5521991968868"
                ),
                new Cliente(
                    "Gabriel Ramos",
                    "123.456.789-10",
                    "gabriel@gmail.com.br",
                    "+5521991968868"
                )
            );
    }

    public Cliente buscarPorId(UUID clienteId) {
        return new Cliente(
            "Saul Goodman",
            "123.456.789-10",
            "saul@goodman.com",
            "555 666 777"
        );
    }

}
