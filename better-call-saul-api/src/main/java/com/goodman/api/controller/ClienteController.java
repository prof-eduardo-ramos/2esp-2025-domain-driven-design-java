package com.goodman.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goodman.api.domain.dto.ClienteOutputDTO;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @GetMapping
    public List<ClienteOutputDTO> listar() {
        return List.of(
            new ClienteOutputDTO(
                UUID.randomUUID(),
                "Eduardo Ramos",
                "123.456.789-10",
                "profeduardo.ramos@fiap.com.br",
                "+55 21 99196-8868"
            ),
            new ClienteOutputDTO(
                UUID.randomUUID(),
                "Gabriel Ramos",
                "123.456.789-10",
                "gabriel.ramos@mail.com.br",
                "+55 21 99196-8868"
            )
        );
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteOutputDTO> buscar(@PathVariable UUID clienteId) {
        return null;
    }

    @PostMapping
    public void adicionar(@RequestBody Object input) {

    }

    @PutMapping("/{clienteId}")
    public void atualizar(@PathVariable UUID clienteId, @RequestBody Object input) {}

    @GetMapping("/{clienteId}/casos")
    public void listarCasos(@PathVariable UUID clienteId) {}

    @PostMapping("/{clienteId}/casos")
    public void abrirCaso(@PathVariable UUID clienteId, @RequestBody Object input) {}

    @DeleteMapping("/{clienteId}")
    public void excluir(@PathVariable UUID clienteId) {}

}
