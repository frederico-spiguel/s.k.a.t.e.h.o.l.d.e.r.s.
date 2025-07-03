package com.skateholders.skateholders.DTOs;

import java.time.LocalDateTime;


public class AtividadeInputDTO {

    private Long trickId;
    private Long seshId;
    private LocalDateTime horario;
    private String obstaculo; // opcional

    public Long getTrickId() {
        return trickId;
    }

    public void setTrickId(Long trickId) {
        this.trickId = trickId;
    }

    public Long getSeshId() {
        return seshId;
    }

    public void setSeshId(Long seshId) {
        this.seshId = seshId;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public String getObstaculo() {
        return obstaculo;
    }

    public void setObstaculo(String obstaculo) {
        this.obstaculo = obstaculo;
    }

    // Getters e Setters
}
