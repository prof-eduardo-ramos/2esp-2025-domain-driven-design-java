package com.goodman.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.goodman.api.controller.dto.CasoInputDTO;
import com.goodman.api.controller.dto.CasoOutputDTO;
import com.goodman.api.controller.dto.ClienteResumoDTO;
import com.goodman.api.domain.model.Caso;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CasoMapper {
    private final ClienteMapper clienteAssembler;

    public Caso toEntity(CasoInputDTO input) {
        Caso caso = new Caso();
        caso.setNumeroProcesso(input.numeroProcesso());
        caso.setTitulo(input.titulo());
        caso.setDescricao(input.descricao());
        // O Cliente e o Status serão definidos no Service
        return caso;
    }

    public CasoOutputDTO toOutputDTO(Caso caso) {
        // Converte o Cliente associado para o DTO de Resumo
        ClienteResumoDTO clienteResumo = (caso.getCliente() != null)
                ? clienteAssembler.toResumoDTO(caso.getCliente())
                : null;

        return new CasoOutputDTO(
                caso.getId(),
                caso.getNumeroProcesso(),
                caso.getTitulo(),
                caso.getDescricao(),
                caso.getDataAbertura(),
                caso.getDataFechamento(),
                caso.getStatus(),
                clienteResumo
        );
    }

    public List<CasoOutputDTO> toOutputDTOList(List<Caso> casos) {
        return casos.stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    public void copyToEntity(CasoInputDTO input, Caso caso) {
        caso.setNumeroProcesso(input.numeroProcesso());
        caso.setTitulo(input.titulo());
        caso.setDescricao(input.descricao());
        // Não mexemos no ID, Cliente, Status, etc.
    }
}
