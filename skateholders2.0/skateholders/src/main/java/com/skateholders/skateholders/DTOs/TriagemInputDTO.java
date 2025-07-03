package com.skateholders.skateholders.DTOs;

public class TriagemInputDTO {
        private Long trickId;
        private int nivel; // 1 para iniciante, 2 para interm., 3 para avançado

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
}
