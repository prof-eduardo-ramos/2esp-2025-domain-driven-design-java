package com.goodman.api.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.goodman.api.domain.model.Cliente;
import com.goodman.api.domain.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente obterPorId(UUID clienteId) {
        return clienteRepository.findById(clienteId)
            .orElseThrow()
        ;
    }

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void excluir(UUID clienteId) {
        clienteRepository.deleteById(clienteId);
    }

    public void excluir(Cliente cliente) {
        clienteRepository.delete(cliente);
    }

}
