package com.skateholders.skateholders.DTOs;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AtividadeOutputDTO {
    private Long id;
    private String trickNome;
    private String baseDescricao;
    private LocalDate seshData;  // Alterado para LocalDate
    private String usuarioLogin;
    private LocalDateTime horario;
    private String obstaculo;

    public AtividadeOutputDTO(Long id, String trickNome, String baseDescricao,
                              LocalDate seshData, String usuarioLogin,
                              LocalDateTime horario, String obstaculo) {
        this.id = id;
        this.trickNome = trickNome;
        this.baseDescricao = baseDescricao;
        this.seshData = seshData;
        this.usuarioLogin = usuarioLogin;
        this.horario = horario;
        this.obstaculo = obstaculo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrickNome() {
        return trickNome;
    }

    public void setTrickNome(String trickNome) {
        this.trickNome = trickNome;
    }

    public String getBaseDescricao() {
        return baseDescricao;
    }

    public void setBaseDescricao(String baseDescricao) {
        this.baseDescricao = baseDescricao;
    }

    public LocalDate getSeshData() {
        return seshData;
    }

    public void setSeshData(LocalDate seshData) {
        this.seshData = seshData;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
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
