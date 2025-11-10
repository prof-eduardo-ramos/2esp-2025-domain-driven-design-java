package com.goodman.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.goodman.api.domain.dto.HonorarioInputDTO;
import com.goodman.api.domain.dto.HonorarioOutputDTO;
import com.goodman.api.domain.model.Honorario;

@Component
public class HonorarioMapper {
    public Honorario toEntity(HonorarioInputDTO input) {
        Honorario honorario = new Honorario();
        honorario.setDescricao(input.descricao());
        honorario.setValor(input.valor());
        honorario.setDataVencimento(input.dataVencimento());
        // O Caso será definido no Service
        return honorario;
    }

    public HonorarioOutputDTO toOutputDTO(Honorario honorario) {
        return new HonorarioOutputDTO(
                honorario.getId(),
                honorario.getDescricao(),
                honorario.getValor(),
                honorario.getDataVencimento(),
                honorario.isPago(),
                honorario.getDataPagamento()
        );
    }

    public List<HonorarioOutputDTO> toOutputDTOList(List<Honorario> honorarios) {
        return honorarios.stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }
}
