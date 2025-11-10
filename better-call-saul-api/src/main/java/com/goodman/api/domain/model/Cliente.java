package com.goodman.api.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CLIENTES")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nome;

    /**
     * Armazena CPF (11) ou CNPJ (14) sem formatação.
     * O length=18 é uma segurança para caso queira guardar formatado.
     */
    @Column(nullable = false, unique = true, length = 18)
    private String cpfCnpj;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String telefone;

    @OneToMany(
        mappedBy = "cliente",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY // Carrega os casos apenas quando cliente.getCasos() for chamado
    )
    private List<Caso> casos = new ArrayList<>();

    // Construtor principal
    public Cliente(String nome, String cpfCnpj, String email, String telefone) {
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.telefone = telefone;
    }

    public void adicionarCaso(Caso caso) {
        this.casos.add(caso);
        caso.setCliente(this);
    }

    public void removerCaso(Caso caso) {
        this.casos.remove(caso);
        caso.setCliente(null);
    }

}
