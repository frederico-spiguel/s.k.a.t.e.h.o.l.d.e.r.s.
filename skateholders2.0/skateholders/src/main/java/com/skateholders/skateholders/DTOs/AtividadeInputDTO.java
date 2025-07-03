package com.skateholders.skateholders.DTOs;

// Se você usar anotações de validação no futuro, os imports virão aqui
// Ex: import jakarta.validation.constraints.NotNull;

public class AtividadeInputDTO {

    // @NotNull // Exemplo de validação futura
    private Long trickId;

    private String obstaculo;

    // Construtores
    public AtividadeInputDTO() {
    }

    public AtividadeInputDTO(Long trickId, String obstaculo) {
        this.trickId = trickId;
        this.obstaculo = obstaculo;
    }

    // Getters e Setters
    public Long getTrickId() {
        return trickId;
    }

    public void setTrickId(Long trickId) {
        this.trickId = trickId;
    }

    public String getObstaculo() {
        return obstaculo;
    }

    public void setObstaculo(String obstaculo) {
        this.obstaculo = obstaculo;
    }
}