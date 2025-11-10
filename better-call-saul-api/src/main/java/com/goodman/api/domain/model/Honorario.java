package com.goodman.api.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_HONORARIOS")
@Getter 
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Honorario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String descricao;

    /**
     * Sempre usar BigDecimal para valores monetários.
     * precision = 10 (total de dígitos)
     * scale = 2 (dígitos após a vírgula)
     * Ex: 12345678.90
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDate dataVencimento;

    @Column(nullable = false)
    private boolean pago = false; // Um novo lançamento nunca começa pago

    private LocalDate dataPagamento;

    // Lado "Muitos" da relação Caso -> Honorario
    // Um honorário DEVE pertencer a um caso.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caso_id", nullable = false)
    private Caso caso;

    // Construtor principal
    public Honorario(String descricao, BigDecimal valor, LocalDate dataVencimento, Caso caso) {
        this.descricao = descricao;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.caso = caso;
    }

    // --- Métodos de Negócio ---
    public void marcarComoPago() {
        if (!this.pago) {
            this.pago = true;
            this.dataPagamento = LocalDate.now();
        }
    }

}
