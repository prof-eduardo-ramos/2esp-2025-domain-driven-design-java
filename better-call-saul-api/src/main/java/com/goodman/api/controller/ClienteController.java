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

import com.goodman.api.domain.dto.CasoInputDTO;
import com.goodman.api.domain.dto.CasoOutputDTO;
import com.goodman.api.domain.dto.ClienteInputDTO;
import com.goodman.api.domain.dto.ClienteOutputDTO;
import com.goodman.api.domain.model.Caso;
import com.goodman.api.domain.model.Cliente;
import com.goodman.api.domain.service.CasoService;
import com.goodman.api.domain.service.ClienteService;
import com.goodman.api.mapper.CasoMapper;
import com.goodman.api.mapper.ClienteMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clientes") // Versionamento da API
public class ClienteController {
    private final ClienteService clienteService;
    private final CasoService casoService;
    private final ClienteMapper clienteAssembler;
    private final CasoMapper casoAssembler;

    @GetMapping
    public List<ClienteOutputDTO> listar() {
        return clienteAssembler.toOutputDTOList(clienteService.listarTodos());
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteOutputDTO> buscar(@PathVariable UUID clienteId) {
        Cliente cliente = clienteService.buscarPorId(clienteId); // Lança 404 se não achar
        return ResponseEntity.ok(clienteAssembler.toOutputDTO(cliente));
    }

    @PostMapping
    public ResponseEntity<ClienteOutputDTO> adicionar(@Valid @RequestBody ClienteInputDTO input) {
        Cliente novoCliente = clienteAssembler.toEntity(input);
        Cliente clienteSalvo = clienteService.salvar(novoCliente); // Lança 400 se CPF/CNPJ duplicado
        ClienteOutputDTO output = clienteAssembler.toOutputDTO(clienteSalvo);

        // Retorna 201 Created com o header Location
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(output.id()).toUri();

        return ResponseEntity.created(uri).body(output);
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<ClienteOutputDTO> atualizar(
            @PathVariable UUID clienteId,
            @Valid @RequestBody ClienteInputDTO input) {

        // Garante que o cliente existe (ou lança 404)
        Cliente clienteExistente = clienteService.buscarPorId(clienteId);

        // Copia os dados do DTO para a entidade
        clienteAssembler.copyToEntity(input, clienteExistente);

        // Salva (e revalida regras de negócio, como duplicidade)
        Cliente clienteSalvo = clienteService.salvar(clienteExistente);

        return ResponseEntity.ok(clienteAssembler.toOutputDTO(clienteSalvo));
    }

    // Endpoint 3: Listar casos de UM cliente
    @GetMapping("/{clienteId}/casos")
    public List<CasoOutputDTO> listarPorCliente(@PathVariable UUID clienteId) {
        return casoAssembler.toOutputDTOList(casoService.listarPorCliente(clienteId));
    }

    // Endpoint 4: Abrir um novo caso para um cliente
    @PostMapping("/{clienteId}/casos")
    public ResponseEntity<CasoOutputDTO> abrirCaso(
            @PathVariable UUID clienteId,
            @Valid @RequestBody CasoInputDTO input) {

        Caso novoCaso = casoAssembler.toEntity(input);
        Caso casoSalvo = casoService.abrirNovoCaso(clienteId, novoCaso);
        CasoOutputDTO output = casoAssembler.toOutputDTO(casoSalvo);

        // Retorna 201 Created com o header Location
        // A URI é /casos/{id} (o endpoint de busca)
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/casos/{id}")
                .buildAndExpand(output.id()).toUri();

        return ResponseEntity.created(uri).body(output);
    }

    @DeleteMapping("/{clienteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna 204 No Content
    public void excluir(@PathVariable UUID clienteId) {
        clienteService.excluir(clienteId);
    }
}
