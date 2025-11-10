package com.goodman.api.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.goodman.api.domain.dto.HonorarioOutputDTO;
import com.goodman.api.domain.model.Honorario;
import com.goodman.api.domain.service.HonorarioService;
import com.goodman.api.mapper.HonorarioMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/honorarios") // Mapeamento na raiz da API V1
public class HonorarioController {
    private final HonorarioService honorarioService;
    private final HonorarioMapper honorarioAssembler;
    
    // Endpoint 3: Buscar um honorário específico
    @GetMapping("/{honorarioId}")
    public ResponseEntity<HonorarioOutputDTO> buscar(@PathVariable UUID honorarioId) {
        Honorario honorario = honorarioService.buscarPorId(honorarioId);
        return ResponseEntity.ok(honorarioAssembler.toOutputDTO(honorario));
    }

    // Endpoint 4: Ação de "Pagar" um honorário
    @PostMapping("/{honorarioId}/pagamento")
    public ResponseEntity<HonorarioOutputDTO> marcarComoPago(@PathVariable UUID honorarioId) {
        Honorario honorarioPago = honorarioService.marcarComoPago(honorarioId);
        return ResponseEntity.ok(honorarioAssembler.toOutputDTO(honorarioPago));
    }

    // Endpoint 5: Excluir um honorário
    @DeleteMapping("/{honorarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna 204 No Content
    public void excluir(@PathVariable UUID honorarioId) {
        honorarioService.excluir(honorarioId);
    }
}
