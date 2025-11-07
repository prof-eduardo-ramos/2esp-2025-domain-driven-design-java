package com.goodman.api.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.goodman.api.domain.model.Cliente;

@Service
public class ClienteService {

    public List<Cliente> listarTodos() {
        return List.of(
            new Cliente(
                "Walter White",
                "123.456.789-10",
                "walter@white.com",
                "777 888 999"
            ),
            new Cliente(
                "Jesse Pinkman",
                "123.456.789-10",
                "jesse@pinkman.com",
                "777 888 999"
            )
        );
    }

    public Cliente obterPorId(UUID clienteId) {
        return new Cliente(
            "Walter White",
            "123.456.789-10",
            "walter@white.com",
            "777 888 999"
        );
    }

    public Cliente salvar(Cliente cliente) {
        return cliente;
    }

    public void excluir(UUID clienteId) {
    }

}
