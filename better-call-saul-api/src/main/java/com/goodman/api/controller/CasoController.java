package com.goodman.api.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.goodman.api.controller.dto.CasoInputDTO;
import com.goodman.api.controller.dto.CasoOutputDTO;
import com.goodman.api.controller.dto.HonorarioInputDTO;
import com.goodman.api.controller.dto.HonorarioOutputDTO;
import com.goodman.api.domain.model.Caso;
import com.goodman.api.domain.model.Honorario;
import com.goodman.api.domain.service.CasoService;
import com.goodman.api.domain.service.HonorarioService;
import com.goodman.api.mapper.CasoMapper;
import com.goodman.api.mapper.HonorarioMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/casos") // Mapeamento na raiz da API V1
public class CasoController {
    private final CasoService casoService;
    private final HonorarioService honorarioService;
    private final CasoMapper casoAssembler;
    private final HonorarioMapper honorarioAssembler;

    // Endpoint 1: Listar todos os casos (geral)
    @GetMapping
    public List<CasoOutputDTO> listarTodos() {
        return casoAssembler.toOutputDTOList(casoService.listarTodos());
    }

    // Endpoint 2: Buscar um caso específico
    @GetMapping("/{casoId}")
    public ResponseEntity<CasoOutputDTO> buscar(@PathVariable UUID casoId) {
        Caso caso = casoService.buscarPorId(casoId);
        return ResponseEntity.ok(casoAssembler.toOutputDTO(caso));
    }

    // Endpoint 5: Atualizar dados de um caso (ex: título, descrição)
    @PutMapping("/{casoId}")
    public ResponseEntity<CasoOutputDTO> atualizar(
            @PathVariable UUID casoId,
            @Valid @RequestBody CasoInputDTO input) {

        Caso casoExistente = casoService.buscarPorId(casoId);
        casoAssembler.copyToEntity(input, casoExistente);
        Caso casoSalvo = casoService.atualizarCaso(casoExistente).orElseThrow(); // Atualização simples, sem regra de negócio complexa

        return ResponseEntity.ok(casoAssembler.toOutputDTO(casoSalvo));
    }

    // Endpoint 6: Ação de "Fechar" um caso
    @PostMapping("/{casoId}/fechamento")
    public ResponseEntity<CasoOutputDTO> fecharCaso(@PathVariable UUID casoId) {
        // Lança 400 (NegocioException) se tiver honorários pendentes
        Caso casoFechado = casoService.fecharCaso(casoId);
        return ResponseEntity.ok(casoAssembler.toOutputDTO(casoFechado));
    }

    // Endpoint 1: Listar honorários de UM caso
    @GetMapping("/{casoId}/honorarios")
    public List<HonorarioOutputDTO> listarPorCaso(@PathVariable UUID casoId) {
        return honorarioAssembler.toOutputDTOList(honorarioService.buscarPorCasoId(casoId));
    }

    // Endpoint 2: Adicionar um novo honorário a um caso
    @PostMapping("/{casoId}/honorarios")
    public ResponseEntity<HonorarioOutputDTO> adicionar(
            @PathVariable UUID casoId,
            @Valid @RequestBody HonorarioInputDTO input) {

        Honorario novoHonorario = honorarioAssembler.toEntity(input);
        // O CasoService trata a regra (não adicionar em caso fechado)
        Honorario honorarioSalvo = casoService.adicionarHonorario(casoId, novoHonorario);
        HonorarioOutputDTO output = honorarioAssembler.toOutputDTO(honorarioSalvo);

        // Retorna 201 Created com o header Location
        // A URI é /honorarios/{id} (criei um endpoint de busca abaixo)
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/honorarios/{id}")
                .buildAndExpand(output.id()).toUri();

        return ResponseEntity.created(uri).body(output);
    }
}
