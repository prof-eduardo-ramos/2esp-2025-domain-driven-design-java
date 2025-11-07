package com.goodman.api.domain.model;

public enum StatusCaso {
    /**
     * O caso está em andamento.
     */
    ATIVO,

    /**
     * O caso está em fase de análise ou consulta inicial.
     */
    CONSULTORIA,

    /**
     * O caso foi concluído e está aguardando arquivamento.
     */
    FECHADO,

    /**
     * O caso foi concluído e arquivado.
     */
    ARQUIVADO
}
