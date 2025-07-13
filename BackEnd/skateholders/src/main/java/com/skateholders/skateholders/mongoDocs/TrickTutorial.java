package com.skateholders.skateholders.mongoDocs;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "trick_tutorials")
public class TrickTutorial {

    @Id
    private String id;

    private String titulo;        // Título do tutorial (ex: "Ollie - O Salto Fundamental")
    private String descricao;     // NOVO CAMPO: Um texto curto de introdução.
    private String videoUrl;      // Link do vídeo do YouTube
    private List<String> passos;  // Lista de instruções passo a passo

    // Getters e Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<String> getPassos() {
        return passos;
    }

    public void setPassos(List<String> passos) {
        this.passos = passos;
    }
}