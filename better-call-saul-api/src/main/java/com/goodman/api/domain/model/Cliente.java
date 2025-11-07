package com.goodman.api.domain.model;

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
public class Cliente {

    private UUID id;

    private String nome;

    private String cpfCnpj;

    private String email;

    private String telefone;

    private List<Caso> casos = new ArrayList<>();

    public Cliente(String nome, String cpfCnpj, String email, String telefone) {
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.telefone = telefone;
    }

    public void adicionarCaso(Caso caso) {
        getCasos().add(caso);
        caso.setCliente(this);
    }

    public void removerCaso(Caso caso) {
        getCasos().remove(caso);
        caso.setCliente(null);
    }

}
