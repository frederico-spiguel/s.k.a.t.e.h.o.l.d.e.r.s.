package com.skateholders.skateholders.DTOs;

import java.time.LocalDate;

public class GraficoPontoDTO {
    private Long sessaoIndex;
    private Long valor;
    private LocalDate data;

    // Construtor vazio
    public GraficoPontoDTO() {}

    // Getters e Setters (sem alteração)
    public Long getSessaoIndex() { return sessaoIndex; }
    public void setSessaoIndex(Long sessaoIndex) { this.sessaoIndex = sessaoIndex; }
    public Long getValor() { return valor; }
    public void setValor(Long valor) { this.valor = valor; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
}