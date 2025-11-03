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

import com.goodman.api.domain.dto.ClienteInputDTO;
import com.goodman.api.domain.dto.ClienteOutputDTO;
import com.goodman.api.domain.model.Cliente;
import com.goodman.api.domain.service.ClienteService;
import com.goodman.api.mapper.ClienteMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    private final ClienteMapper clienteMapper;
    
    @GetMapping
    public List<ClienteOutputDTO> listar() {
        List<Cliente> clientes = clienteService.listarTodos();
        List<ClienteOutputDTO> outputDtoList = clienteMapper.toOutputDtoList(clientes);
        return outputDtoList;
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteOutputDTO> buscar(@PathVariable UUID clienteId) {
        return null;
    }

    @PostMapping
    public ResponseEntity<ClienteOutputDTO> adicionar(@RequestBody ClienteInputDTO input) {
        return null;
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<ClienteOutputDTO> atualizar(@PathVariable UUID clienteId, @RequestBody ClienteInputDTO input) {
        return null;
    }

    @DeleteMapping("/{clienteId}")
    public void excluir(@PathVariable UUID clienteId) {

    }
}
