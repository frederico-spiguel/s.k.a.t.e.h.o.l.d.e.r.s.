package com.skateholders.skateholders.DTOs;

import com.skateholders.skateholders.models.Atividade; // Importe a entidade
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AtividadeOutputDTO {

    // Dados da própria Atividade
    private Long id;
    private LocalDateTime horario;
    private String obstaculo;

    // Dados da Trick relacionada
    private Long trickId;
    private String trickNome;

    // Dados da Sesh relacionada
    private Long seshId;
    private LocalDate seshData;

    // Construtor principal que aceita a entidade
    public AtividadeOutputDTO(Atividade atividade) {
        this.id = atividade.getId();
        this.horario = atividade.getHorario();
        this.obstaculo = atividade.getObstaculo();
        this.trickId = atividade.getTrick().getId();
        this.trickNome = atividade.getTrick().getNome();
        this.seshId = atividade.getSesh().getId();
        this.seshData = atividade.getSesh().getData();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getTrickId() {
        return trickId;
    }

    public void setTrickId(Long trickId) {
        this.trickId = trickId;
    }

    public String getTrickNome() {
        return trickNome;
    }

    public void setTrickNome(String trickNome) {
        this.trickNome = trickNome;
    }

    public Long getSeshId() {
        return seshId;
    }

    public void setSeshId(Long seshId) {
        this.seshId = seshId;
    }

    public LocalDate getSeshData() {
        return seshData;
    }

    public void setSeshData(LocalDate seshData) {
        this.seshData = seshData;
    }
    // Getters e Setters para todos os campos...
}