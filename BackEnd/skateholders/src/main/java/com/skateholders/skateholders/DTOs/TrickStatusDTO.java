package com.skateholders.skateholders.DTOs;

public class TrickStatusDTO {
    private String nomeTrick;
    private int acertos;
    private String nivel; // <-- ALTERADO DE int PARA String
    private boolean proficiencia;
    private boolean isAceso;

    // Getters e Setters
    public String getNomeTrick() { return nomeTrick; }
    public void setNomeTrick(String nomeTrick) { this.nomeTrick = nomeTrick; }
    public int getAcertos() { return acertos; }
    public void setAcertos(int acertos) { this.acertos = acertos; }
    public String getNivel() { return nivel; } // <-- Retorna String
    public void setNivel(String nivel) { this.nivel = nivel; } // <-- Recebe String
    public boolean isProficiencia() { return proficiencia; }
    public void setProficiencia(boolean proficiencia) { this.proficiencia = proficiencia; }
    public boolean isAceso() { return isAceso; }
    public void setAceso(boolean aceso) { isAceso = aceso; }
}