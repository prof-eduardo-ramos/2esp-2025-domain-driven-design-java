package com.goodman.api.domain.repository;

import java.util.List;
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

    /**
     * Busca clientes cujo nome contenha o texto fornecido, ignorando maiúsculas/minúsculas.
     * Útil para campos de busca na UI.
     * @param nome O trecho do nome a ser buscado.
     * @return Uma lista de clientes que correspondem ao critério.
     */
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}
