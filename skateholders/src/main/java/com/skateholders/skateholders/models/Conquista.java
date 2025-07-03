package com.skateholders.skateholders.models;

import jakarta.persistence.*;

@Entity
@Table(name = "conquista")
public class Conquista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private String tipo; // Ex: "trick", "sesh", "atividade"

    private String parametro; // Ex: "kickflip", "qtd_sesh", etc

    private int quantidade;

    public Conquista() {}

    public Conquista(String nome, String descricao, String tipo, String parametro, int quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.parametro = parametro;
        this.quantidade = quantidade;
    }

    // Getters e Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getParametro() { return parametro; }
    public void setParametro(String parametro) { this.parametro = parametro; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}
