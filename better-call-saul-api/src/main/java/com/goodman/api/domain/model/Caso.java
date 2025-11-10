package com.goodman.api.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_CASOS")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Caso {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Número único do processo no tribunal ou identificador interno.
     */
    @Column(nullable = false, unique = true, length = 30)
    private String numeroProcesso;

    @Column(length = 200)
    private String titulo;

    /**
     * Descrição detalhada do caso, pode ser um texto longo.
     */
    @Lob // Large Object, mapeia para um tipo TEXT no MySQL
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private LocalDate dataAbertura;

    private LocalDate dataFechamento;

    @Enumerated(EnumType.STRING) // Salva o nome do Enum ("ATIVO", "FECHADO") no banco
    @Column(nullable = false, length = 20)
    private StatusCaso status;

    // --- Relacionamentos ---

    // Lado "Muitos" da relação Cliente -> Caso
    // Um caso DEVE pertencer a um cliente.
    @ManyToOne(fetch = FetchType.LAZY) // Carrega o cliente apenas quando caso.getCliente() for chamado
    @JoinColumn(name = "cliente_id", nullable = false) // Define a coluna da chave estrangeira
    private Cliente cliente;

    // Lado "Um" da relação Caso -> Honorario
    @OneToMany(
            mappedBy = "caso",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Honorario> honorarios = new ArrayList<>();

    // Construtor principal
    public Caso(String numeroProcesso, String titulo, String descricao, Cliente cliente) {
        this.numeroProcesso = numeroProcesso;
        this.titulo = titulo;
        this.descricao = descricao;
        this.cliente = cliente;
        this.dataAbertura = LocalDate.now();
        this.status = StatusCaso.ATIVO; // Um novo caso sempre começa como ATIVO
    }

    // --- Métodos Helper ---
    public void adicionarHonorario(Honorario honorario) {
        this.honorarios.add(honorario);
        honorario.setCaso(this);
    }

    public void removerHonorario(Honorario honorario) {
        this.honorarios.remove(honorario);
        honorario.setCaso(null);
    }

    /**
     * Método de negócio para fechar um caso.
     */
    public void fecharCaso() {
        if (this.status != StatusCaso.FECHADO && this.status != StatusCaso.ARQUIVADO) {
            this.status = StatusCaso.FECHADO;
            this.dataFechamento = LocalDate.now();
        } else {
            // Lançaria uma exceção de negócio aqui (ex: CasoJaFechadoException)
            throw new IllegalStateException("O caso já se encontra fechado ou arquivado.");
        }
    }

}
