package com.skateholders.skateholders.DTOs;

public class TrickStatusDTO {
    private Long trickId; // <-- CAMPO ADICIONADO
    private String nomeTrick;
    private int acertos;
    private String nivel;
    private boolean proficiencia;
    private boolean isAceso;

    // Getters e Setters para todos os campos
    public Long getTrickId() {
        return trickId;
    }

    public void setTrickId(Long trickId) {
        this.trickId = trickId;
    }

    public String getNomeTrick() {
        return nomeTrick;
    }

    public void setNomeTrick(String nomeTrick) {
        this.nomeTrick = nomeTrick;
    }

    public int getAcertos() {
        return acertos;
    }

    public void setAcertos(int acertos) {
        this.acertos = acertos;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public boolean isProficiencia() {
        return proficiencia;
    }

    public void setProficiencia(boolean proficiencia) {
        this.proficiencia = proficiencia;
    }

    public boolean isAceso() {
        return isAceso;
    }

    public void setAceso(boolean aceso) {
        isAceso = aceso;
    }
}