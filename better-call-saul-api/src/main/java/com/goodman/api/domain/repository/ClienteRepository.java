package com.goodman.api.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goodman.api.domain.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    /**
     * Busca um cliente pelo CPF ou CNPJ, que é um campo único.
     * @param cpfCnpj O CPF ou CNPJ (sem formatação)
     * @return Um Optional contendo o cliente, se encontrado.
     */
    Optional<Cliente> findByCpfCnpj(String cpfCnpj);
}
