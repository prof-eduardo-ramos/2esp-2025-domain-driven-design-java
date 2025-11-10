package com.goodman.api.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goodman.api.domain.model.Honorario;

@Repository
public interface HonorarioRepository extends JpaRepository<Honorario,UUID>{
    /**
     * Lista todos os lançamentos de honorários para um caso específico.
     * @param casoId O ID do caso.
     * @return Uma lista de honorários.
     */
    List<Honorario> findByCasoId(UUID casoId);

    /**
     * Lista todos os honorários de um caso filtrando por status de pagamento.
     * @param casoId O ID do caso.
     * @param pago true para pagos, false para pendentes.
     * @return Uma lista de honorários.
     */
    List<Honorario> findByCasoIdAndPago(UUID casoId, boolean pago);

    /**
     * Busca todos os honorários não pagos que venceram antes ou na data especificada.
     * Útil para relatórios de cobrança.
     * @param dataLimite A data máxima de vencimento.
     * @param pago Status de pagamento (normalmente 'false' para esta consulta).
     * @return Uma lista de honorários vencidos.
     */
    List<Honorario> findByDataVencimentoBeforeAndPago(LocalDate dataLimite, boolean pago);
}
