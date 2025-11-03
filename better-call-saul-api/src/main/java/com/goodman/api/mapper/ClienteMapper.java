package com.goodman.api.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.goodman.api.domain.dto.ClienteInputDTO;
import com.goodman.api.domain.dto.ClienteOutputDTO;
import com.goodman.api.domain.model.Cliente;

@Component
public class ClienteMapper {

    public ClienteOutputDTO toOutputDto(Cliente cliente) {
        return
            new ClienteOutputDTO(
                UUID.randomUUID(),
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

    public Cliente toEntity(ClienteInputDTO input) {
        return
            new Cliente(
                input.nome(),
                input.cpfCnpj(),
                input.email(),
                input.telefone()
            )
        ;
    }

}
