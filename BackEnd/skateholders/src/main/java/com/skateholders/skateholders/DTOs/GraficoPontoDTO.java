package com.skateholders.skateholders.DTOs;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;

public class GraficoPontoDTO {
    private Long sessaoIndex; // Eixo X: O número da sessão (1, 2, 3...)
    private Long valor;        // Eixo Y: O valor acumulado de acertos
    private LocalDate data;    // Metadado: A data real da sessão (para o front-end usar como label)

    // Construtor que a nossa nova query vai usar
    public GraficoPontoDTO(BigInteger sessaoIndex, Long valor, Date data) {
        this.sessaoIndex = sessaoIndex.longValue();
        this.valor = valor;
        this.data = data.toLocalDate();
    }

    // Getters e Setters
    public Long getSessaoIndex() { return sessaoIndex; }
    public void setSessaoIndex(Long sessaoIndex) { this.sessaoIndex = sessaoIndex; }
    public Long getValor() { return valor; }
    public void setValor(Long valor) { this.valor = valor; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
}