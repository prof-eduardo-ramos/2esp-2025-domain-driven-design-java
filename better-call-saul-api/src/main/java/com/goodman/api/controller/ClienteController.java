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
        List<Cliente> clientes = clienteService.listarTodos();
        List<ClienteOutputDTO> outputDtoList = clienteMapper.toOutputDtoList(clientes);
        return outputDtoList;
    }

    @GetMapping("/{clienteId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ClienteOutputDTO> buscar(@PathVariable UUID clienteId) {
        Cliente cliente = clienteService.obterPorId(clienteId);
        ClienteOutputDTO outputDto = clienteMapper.toOutputDto(cliente);
        return ResponseEntity.ok(outputDto);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<ClienteOutputDTO> adicionar(@RequestBody ClienteInputDTO input) {
        Cliente cliente = clienteMapper.toEntity(input);
        Cliente novoCliente = clienteService.salvar(cliente);
        ClienteOutputDTO outputDto = clienteMapper.toOutputDto(novoCliente);
        
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(outputDto.id())
            .toUri()
        ;

        return ResponseEntity.created(uri).body(outputDto);
    }

    @PutMapping("/{clienteId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<ClienteOutputDTO> atualizar(@PathVariable UUID clienteId, @RequestBody ClienteInputDTO input) {
        Cliente cliente = clienteMapper.toEntity(input);
        Cliente clienteAtualizado = clienteService.salvar(cliente);
        ClienteOutputDTO outputDto = clienteMapper.toOutputDto(clienteAtualizado);
        return ResponseEntity.ok(outputDto);
    }

    @DeleteMapping("/{clienteId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable UUID clienteId) {
        clienteService.excluir(clienteId);
    }
}
