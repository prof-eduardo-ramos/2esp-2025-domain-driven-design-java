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
        // return List.of(
        //     new Cliente(
        //         "Walter White",
        //         "123.456.789-10",
        //         "walter@white.com",
        //         "777 888 999"
        //     ),
        //     new Cliente(
        //         "Jesse Pinkman",
        //         "123.456.789-10",
        //         "jesse@pinkman.com",
        //         "777 888 999"
        //     )
        // );
    }

    public Cliente obterPorId(UUID clienteId) {
        return clienteRepository.findById(clienteId)
            .orElseThrow()
        ;
        // return new Cliente(
        //     "Walter White",
        //     "123.456.789-10",
        //     "walter@white.com",
        //     "777 888 999"
        // );
    }

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void excluir(UUID clienteId) {
        clienteRepository.deleteById(clienteId);;
    }

}
