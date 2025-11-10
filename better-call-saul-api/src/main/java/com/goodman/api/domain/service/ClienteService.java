package com.goodman.api.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goodman.api.domain.exception.ClienteNaoEncontradoException;
import com.goodman.api.domain.exception.NegocioException;
import com.goodman.api.domain.model.Cliente;
import com.goodman.api.domain.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Cria um construtor com 'final ClienteRepository'
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Busca um cliente pelo ID. Lança exceção se não encontrar.
     * @param clienteId O ID do cliente.
     * @return A entidade Cliente.
     * @throws ClienteNaoEncontradoException se o cliente não for encontrado.
     */
    public Cliente buscarPorId(UUID clienteId) {
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNaoEncontradoException(clienteId));
    }

    /**
     * Lista todos os clientes cadastrados.
     * @return Lista de Clientes.
     */
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    /**
     * Salva ou atualiza um cliente, validando regras de negócio.
     * @param cliente A entidade Cliente a ser salva.
     * @return O cliente salvo (com ID).
     * @throws NegocioException se o CPF/CNPJ já estiver em uso por outro cliente.
     */
    @Transactional
    public Cliente salvar(Cliente cliente) {
        // Regra de Negócio: CPF/CNPJ deve ser único
        Optional<Cliente> clienteExistente = clienteRepository.findByCpfCnpj(cliente.getCpfCnpj());

        if (clienteExistente.isPresent() && !clienteExistente.get().equals(cliente)) {
            // Se o cliente existe E não é o mesmo que estamos tentando atualizar
            throw new NegocioException("Já existe um cliente cadastrado com este CPF/CNPJ.");
        }

        return clienteRepository.save(cliente);
    }

    /**
     * Exclui um cliente do banco de dados.
     * @param clienteId O ID do cliente a ser excluído.
     * @throws ClienteNaoEncontradoException se o cliente não for encontrado.
     */
    @Transactional
    public void excluir(UUID clienteId) {
        // Primeiro, busca para garantir que existe (e lançar 404 se não)
        Cliente cliente = buscarPorId(clienteId);

        // Se existir, deleta.
        // A relação @OneToMany com CascadeType.ALL em Cliente irá remover
        // os Casos e Honorários associados.
        clienteRepository.delete(cliente);
    }

}
