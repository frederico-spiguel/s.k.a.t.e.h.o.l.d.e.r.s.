package com.skateholders.skateholders.DTOs;

public class TrickUsuarioInputDTO {
    private Long usuarioId;
    private Long trickId;
    private int nivel;
    private int acertos;
    private boolean proficiencia;

    // Getters e Setters
    public TrickUsuarioInputDTO(Long usuarioId, Long trickId, int nivel, int acertos, boolean proficiencia) {
        this.usuarioId = usuarioId;
        this.trickId = trickId;
        this.nivel = nivel;
        this.acertos = acertos;
        this.proficiencia = proficiencia;
    }

    public TrickUsuarioInputDTO() {}

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getTrickId() {
        return trickId;
    }

    public void setTrickId(Long trickId) {
        this.trickId = trickId;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getAcertos() {
        return acertos;
    }

    public void setAcertos(int acertos) {
        this.acertos = acertos;
    }

    public boolean isProficiencia() {
        return proficiencia;
    }

    public void setProficiencia(boolean proficiencia) {
        this.proficiencia = proficiencia;
    }
}
