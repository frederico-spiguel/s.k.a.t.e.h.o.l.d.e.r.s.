package com.skateholders.skateholders.DTOs;

public class GraficoOpcaoDTO {
    private Long id;
    private String nome;

    public GraficoOpcaoDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}