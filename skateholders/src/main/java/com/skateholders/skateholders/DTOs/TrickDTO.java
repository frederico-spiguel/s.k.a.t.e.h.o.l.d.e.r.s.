package com.skateholders.skateholders.DTOs;

public class TrickDTO {

    private Long id;
    private String nome;
    private int dificuldade;
    private String descricaoBase;

    // Construtores
    public TrickDTO(Long id, String nome, int dificuldade, String descricaoBase) {
        this.id = id;
        this.nome = nome;
        this.dificuldade = dificuldade;
        this.descricaoBase = descricaoBase;
    }

    public TrickDTO() {}

    // Getters e Setters
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

    public String getDescricaoBase() {
        return descricaoBase;
    }

    public void setDescricaoBase(String descricaoBase) {
        this.descricaoBase = descricaoBase;
    }
}
