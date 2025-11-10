package com.goodman.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.goodman.api.controller.dto.ClienteInputDTO;
import com.goodman.api.controller.dto.ClienteOutputDTO;
import com.goodman.api.controller.dto.ClienteResumoDTO;
import com.goodman.api.domain.model.Cliente;

/**
 * Converte a entidade Cliente para DTOs e vice-versa.
 */
@Component
public class ClienteMapper {
    /**
     * Converte um ClienteInputDTO para uma entidade Cliente.
     * (Usado ao Criar)
     */
    public Cliente toEntity(ClienteInputDTO input) {
        Cliente cliente = new Cliente();
        cliente.setNome(input.nome());
        cliente.setCpfCnpj(input.cpfCnpj());
        cliente.setEmail(input.email());
        cliente.setTelefone(input.telefone());
        return cliente;
    }

    /**
     * Converte uma entidade Cliente para um ClienteOutputDTO.
     * (Usado ao Ler)
     */
    public ClienteOutputDTO toOutputDTO(Cliente cliente) {
        return new ClienteOutputDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpfCnpj(),
                cliente.getEmail(),
                cliente.getTelefone()
        );
    }

    /**
     * Converte uma entidade Cliente para um ClienteResumoDTO (record).
     */
    public ClienteResumoDTO toResumoDTO(Cliente cliente) {
        return new ClienteResumoDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpfCnpj()
        );
    }

    /**
     * Converte uma lista de Clientes para uma lista de ClienteOutputDTOs.
     */
    public List<ClienteOutputDTO> toOutputDTOList(List<Cliente> clientes) {
        return clientes.stream()
                .map(this::toOutputDTO)
                .collect(Collectors.toList());
    }

    /**
     * Copia os dados de um DTO de entrada para uma entidade existente.
     * (Usado ao Atualizar)
     * @param input O DTO com os novos dados.
     * @param cliente A entidade (do banco) a ser atualizada.
     */
    public void copyToEntity(ClienteInputDTO input, Cliente cliente) {
        cliente.setNome(input.nome());
        cliente.setCpfCnpj(input.cpfCnpj());
        cliente.setEmail(input.email());
        cliente.setTelefone(input.telefone());
        // Não mexemos no ID ou nas associações
    }
}
