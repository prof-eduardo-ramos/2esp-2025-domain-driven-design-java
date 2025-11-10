package com.goodman.api.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goodman.api.domain.exception.HonorarioNaoEncontradoException;
import com.goodman.api.domain.model.Caso;
import com.goodman.api.domain.model.Honorario;
import com.goodman.api.domain.repository.HonorarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HonorarioService {

    private final HonorarioRepository honorarioRepository;

    /**
     * Busca um honorário pelo ID.
     * @param honorarioId O ID.
     * @return A entidade Honorario.
     * @throws HonorarioNaoEncontradoException se não for encontrado.
     */
    public Honorario buscarPorId(UUID honorarioId) {
        return honorarioRepository.findById(honorarioId)
                .orElseThrow(() -> new HonorarioNaoEncontradoException(honorarioId));
    }

    /**
     * Obtém uma lista de honorários de um caso
     * @param casoId O ID do Caso
     * @return Lista de Honorarios
     */
    public List<Honorario> buscarPorCasoId(UUID casoId) {
        return honorarioRepository.findByCasoId(casoId);
    }

    /**
     * Salva (l lança) um novo honorário no sistema, associando-o a um caso.
     * @param caso O caso ao qual o honorário pertence.
     * @param honorario A entidade Honorario (sem ID e sem caso).
     * @return O honorário salvo.
     */
    @Transactional
    public Honorario lancarNovo(Caso caso, Honorario honorario) {
        // A validação (ex: se o caso está fechado) é feita no CasoService
        honorario.setCaso(caso);
        return honorarioRepository.save(honorario);
    }

    /**
     * Marca um honorário como pago.
     * @param honorarioId O ID do honorário.
     * @return O honorário atualizado.
     */
    @Transactional
    public Honorario marcarComoPago(UUID honorarioId) {
        Honorario honorario = buscarPorId(honorarioId);

        // Delega a regra de negócio para a própria entidade
        honorario.marcarComoPago();

        // O Spring/JPA gerencia a transação e salva a alteração
        return honorario;
    }

    /**
     * Busca honorários pendentes (não pagos) de um caso.
     * @param casoId O ID do caso.
     * @return Lista de honorários pendentes.
     */
    public List<Honorario> buscarPendentesPorCaso(UUID casoId) {
        return honorarioRepository.findByCasoIdAndPago(casoId, false);
    }

    /**
     * Exclui um lançamento de honorário.
     * @param honorarioId O ID do honorário.
     */
    @Transactional
    public void excluir(UUID honorarioId) {
        Honorario honorario = buscarPorId(honorarioId);
        honorarioRepository.delete(honorario);
    }
}
