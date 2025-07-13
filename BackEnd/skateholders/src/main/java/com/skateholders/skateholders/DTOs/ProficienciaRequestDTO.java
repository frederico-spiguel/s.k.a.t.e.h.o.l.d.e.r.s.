package com.skateholders.skateholders.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProficienciaRequestDTO {

    @NotNull
    private Long trickId;

    @NotNull
    @Min(0)
    @Max(10)
    private Integer acertosReportados;

    // Getters e Setters
    public Long getTrickId() {
        return trickId;
    }

    public void setTrickId(Long trickId) {
        this.trickId = trickId;
    }

    public Integer getAcertosReportados() {
        return acertosReportados;
    }

    public void setAcertosReportados(Integer acertosReportados) {
        this.acertosReportados = acertosReportados;
    }
}