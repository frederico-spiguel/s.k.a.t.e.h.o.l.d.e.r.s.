package com.skateholders.skateholders.mongoDocs;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "conquistas")
public class ConquistaDoc {

    @Id
    private String id;
    private String nome;
    private String descricao;
    private String tipo;
    private Map<String, Object> requisitos; // flexível
    private Integer pontos;

    // Dados da imagem
    private String imagemUrl;
    private String imagemFormato;
    private String imagemId;

    // Getters e Setters

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getImagemFormato() {
        return imagemFormato;
    }

    public void setImagemFormato(String imagemFormato) {
        this.imagemFormato = imagemFormato;
    }

    public String getImagemId() {
        return imagemId;
    }

    public void setImagemId(String imagemId) {
        this.imagemId = imagemId;
    }
}
