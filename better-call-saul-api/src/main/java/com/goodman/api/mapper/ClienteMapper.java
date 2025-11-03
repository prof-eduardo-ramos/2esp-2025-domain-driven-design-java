package com.goodman.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.goodman.api.domain.dto.ClienteOutputDTO;
import com.goodman.api.domain.model.Cliente;

@Component
public class ClienteMapper {

    public ClienteOutputDTO toOutputDto(Cliente cliente) {
        return
            new ClienteOutputDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpfCnpj(),
                cliente.getEmail(),
                cliente.getTelefone()
            )
        ;
    }

    public List<ClienteOutputDTO> toOutputDtoList(List<Cliente> clientes) {
        return
            clientes.stream()
                .map(this::toOutputDto)
                .collect(Collectors.toList())
        ;
    }

}
