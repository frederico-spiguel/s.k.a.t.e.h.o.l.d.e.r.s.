// ConquistaStatusDTO.java

package com.skateholders.skateholders.DTOs;

import com.skateholders.skateholders.mongoDocs.ConquistaDoc;
import java.util.Map;

public class ConquistaStatusDTO {

    private String id;
    private String nome;
    private String descricao;
    private Map<String, Object> requisitos;
    private Integer pontos;
    private String imagemUrl;
    private boolean desbloqueada; // O campo chave!

    // Construtor que recebe a conquista do banco e o status
    public ConquistaStatusDTO(ConquistaDoc conquista, boolean desbloqueada) {
        this.id = conquista.getId();
        this.nome = conquista.getNome();
        this.descricao = conquista.getDescricao();
        this.requisitos = conquista.getRequisitos(); // Podemos remover se o front não precisar
        this.pontos = conquista.getPontos();
        this.imagemUrl = conquista.getImagemUrl();
        this.desbloqueada = desbloqueada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Map<String, Object> getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(Map<String, Object> requisitos) {
        this.requisitos = requisitos;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public boolean isDesbloqueada() {
        return desbloqueada;
    }

    public void setDesbloqueada(boolean desbloqueada) {
        this.desbloqueada = desbloqueada;
    }
// Getters e Setters para todos os campos...
    // ...
}