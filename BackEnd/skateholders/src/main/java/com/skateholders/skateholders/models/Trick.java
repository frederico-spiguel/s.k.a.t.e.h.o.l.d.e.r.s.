package com.skateholders.skateholders.models;

import jakarta.persistence.*;

@Entity
@Table(name = "trick")
public class Trick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private int dificuldade;

    // Relacionamento com a tabela Base
    @ManyToOne
    @JoinColumn(name = "base_id", nullable = false) // Referencia a chave estrangeira
    private Base base;

    public Trick() {}

    public Trick(String nome, int dificuldade, Base base) {
        this.nome = nome;
        this.dificuldade = dificuldade;
        this.base = base;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }
}
