package com.goodman.api.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    @ResponseStatus(code = HttpStatus.OK)
    public List<ClienteOutputDTO> listar() {
        return clienteMapper.tOutputDTOList(clienteService.listarTodos());
    }

    @GetMapping("/{clienteId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ClienteOutputDTO> buscar(@PathVariable UUID clienteId) {
        return ResponseEntity.ok(clienteMapper.toOutputDTO(clienteService.buscarPorId(clienteId)));
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<ClienteOutputDTO> adicionar(@RequestBody ClienteInputDTO input) {
        Cliente cliente = clienteMapper.toEntity(input);
        Cliente novoCliente = clienteService.criar(cliente);
        ClienteOutputDTO outputDTO = clienteMapper.toOutputDTO(novoCliente);
        
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{id}")
            .buildAndExpand(outputDTO.id())
            .toUri()
        ;

        return ResponseEntity.created(uri).body(outputDTO);
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<ClienteOutputDTO> atualizar(@PathVariable UUID clienteId, @RequestBody ClienteInputDTO input) {

        // return ResponseEntity.ok();
    }

    @GetMapping("/{clienteId}/casos")
    public void listarCasos(@PathVariable UUID clienteId) {}

    @PostMapping("/{clienteId}/casos")
    public void abrirCaso(@PathVariable UUID clienteId, @RequestBody Object input) {}

    @DeleteMapping("/{clienteId}")
    public void excluir(@PathVariable UUID clienteId) {}

}
