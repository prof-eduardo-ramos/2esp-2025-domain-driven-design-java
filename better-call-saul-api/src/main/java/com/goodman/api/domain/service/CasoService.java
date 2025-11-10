package com.goodman.api.domain.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goodman.api.domain.exception.CasoNaoEncontradoException;
import com.goodman.api.domain.exception.NegocioException;
import com.goodman.api.domain.model.Caso;
import com.goodman.api.domain.model.Cliente;
import com.goodman.api.domain.model.Honorario;
import com.goodman.api.domain.model.StatusCaso;
import com.goodman.api.domain.repository.CasoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CasoService {
    private final CasoRepository casoRepository;
    private final ClienteService clienteService; // Injeta outro serviço
    private final HonorarioService honorarioService; // Injeta outro serviço

    /**
     * Busca um caso pelo ID.
     * @param casoId O ID.
     * @return A entidade Caso.
     * @throws CasoNaoEncontradoException se não for encontrado.
     */
    public Caso buscarPorId(UUID casoId) {
        return casoRepository.findById(casoId)
                .orElseThrow(() -> new CasoNaoEncontradoException(casoId));
    }

    /**
     * Lista todos os casos existentes
     */
    public List<Caso> listarTodos() {
        return casoRepository.findAll();
    }

    /**
     * Lista todos os casos de um cliente específico.
     * @param clienteId O ID do cliente.
     * @return Lista de casos.
     */
    public List<Caso> listarPorCliente(UUID clienteId) {
        // Valida se o cliente existe (lança 404 se não)
        clienteService.buscarPorId(clienteId);
        return casoRepository.findByClienteId(clienteId);
    }

    /**
     * Abre um novo caso jurídico para um cliente.
     * @param clienteId O ID do cliente.
     * @param caso A entidade Caso (sem ID e sem cliente).
     * @return O caso salvo.
     * @throws NegocioException se o número do processo já existir.
     */
    @Transactional
    public Caso abrirNovoCaso(UUID clienteId, Caso caso) {
        // 1. Busca o cliente (ou falha com 404)
        Cliente cliente = clienteService.buscarPorId(clienteId);

        caso.setStatus(StatusCaso.ATIVO);
        caso.setDataAbertura(LocalDate.now());

        // 2. Regra de Negócio: Valida se o número do processo já existe
        Optional<Caso> casoExistente = casoRepository.findByNumeroProcesso(caso.getNumeroProcesso());
        if (casoExistente.isPresent()) {
            throw new NegocioException("Já existe um caso cadastrado com este número de processo.");
        }

        // 3. Associa as entidades
        // (O construtor do Caso já define status ATIVO e data de abertura)
        caso.setCliente(cliente);

        return casoRepository.save(caso);
    }

    /**
     * 
     */
    public Optional<Caso> atualizarCaso(Caso casoExistente) {
        return Optional.of(casoRepository.save(casoExistente));
    }

    /**
     * Fecha um caso jurídico.
     * @param casoId O ID do caso.
     * @return O caso atualizado.
     * @throws NegocioException se o caso tiver honorários pendentes.
     */
    @Transactional
    public Caso fecharCaso(UUID casoId) {
        // 1. Regra de Negócio: Não se pode fechar caso com pendências
        List<Honorario> pendentes = honorarioService.buscarPendentesPorCaso(casoId);
        if (!pendentes.isEmpty()) {
            throw new NegocioException("Não é possível fechar o caso. Existem "
                    + pendentes.size() + " honorário(s) pendente(s).");
        }

        // 2. Busca o caso
        Caso caso = buscarPorId(casoId);

        // 3. Delega a regra de negócio para a entidade
        caso.fecharCaso(); // (A entidade já trata o IllegalStateException se já estiver fechado)

        return caso; // A transação será "commitada" pelo Spring
    }

    /**
     * Adiciona um novo lançamento de honorário a um caso.
     * @param casoId O ID do caso.
     * @param honorario A entidade Honorario (sem ID e sem caso).
     * @return O honorário salvo.
     * @throws NegocioException se o caso estiver fechado ou arquivado.
     */
    @Transactional
    public Honorario adicionarHonorario(UUID casoId, Honorario honorario) {
        Caso caso = buscarPorId(casoId);

        // 1. Regra de Negócio: Não se pode adicionar honorários a casos encerrados.
        if (caso.getStatus() == StatusCaso.FECHADO || caso.getStatus() == StatusCaso.ARQUIVADO) {
            throw new NegocioException("Não é possível adicionar honorários a um caso que está "
                    + caso.getStatus().name().toLowerCase() + ".");
        }

        // 2. Delega a criação para o serviço de honorários
        return honorarioService.lancarNovo(caso, honorario);
    }

}
