package com.goodman.api.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goodman.api.domain.model.Caso;
import com.goodman.api.domain.model.StatusCaso;

@Repository
public interface CasoRepository extends JpaRepository<Caso,UUID> {
    /**
     * Busca um caso pelo seu número de processo (único).
     * @param numeroProcesso O número do processo.
     * @return Um Optional contendo o caso, se encontrado.
     */
    Optional<Caso> findByNumeroProcesso(String numeroProcesso);

    /**
     * Lista todos os casos associados a um ID de cliente específico.
     * @param clienteId O ID do cliente.
     * @return Uma lista de casos do cliente.
     */
    List<Caso> findByClienteId(UUID clienteId);

    /**
     * Lista todos os casos que estão com um status específico.
     * @param status O Status (ATIVO, FECHADO, etc.)
     * @return Uma lista de casos.
     */
    List<Caso> findByStatus(StatusCaso status);

    /**
     * Lista todos os casos de um cliente que estão com um status específico.
     * @param clienteId O ID do cliente.
     * @param status O Status a filtrar.
     * @return Uma lista de casos.
     */ 
    List<Caso> findByClienteIdAndStatus(UUID clienteId, StatusCaso status);
}
