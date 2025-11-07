package com.goodman.api.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Caso {

    private UUID id;

    private String numeroProcesso;

    private String titulo;

    private String descricao;

    private LocalDate dataAbertura;

    private LocalDate dataFechamento;

    private StatusCaso status;

    private Cliente cliente;

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

    public void adicionarHonorario(Honorario honorario) {
        getHonorarios().add(honorario);
        honorario.setCaso(this);
    }

    public void removerHonorario(Honorario honorario) {
        getHonorarios().remove(honorario);
        honorario.setCaso(null);
    }

    public void fecharCaso() {
        //TODO
    }

}
