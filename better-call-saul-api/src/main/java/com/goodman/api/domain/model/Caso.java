package com.goodman.api.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CASOS")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Caso {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = true, length = 30, unique = true)
    private String numeroProcesso;

    @Column(length = 200)
    private String titulo;

    @Lob
    @Column(columnDefinition = "TEXT") // Large Object, mapeia para um tipo TEXT no MySQL
    private String descricao;

    @Column(nullable = false)
    private LocalDate dataAbertura;

    @Column
    private LocalDate dataFechamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusCaso status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // private List<Honorario> honorarios = new ArrayList<>();

    // Construtor principal
    public Caso(String numeroProcesso, String titulo, String descricao, Cliente cliente) {
        this.numeroProcesso = numeroProcesso;
        this.titulo = titulo;
        this.descricao = descricao;
        this.cliente = cliente;
        this.dataAbertura = LocalDate.now();
        this.status = StatusCaso.ATIVO; // Um novo caso sempre começa como ATIVO
    }

    public void adicionarHonorario(Honorario honorario) {
        // getHonorarios().add(honorario);
        honorario.setCaso(this);
    }

    public void removerHonorario(Honorario honorario) {
        // getHonorarios().remove(honorario);
        honorario.setCaso(null);
    }

    public void fecharCaso() {
        //TODO
    }

}
