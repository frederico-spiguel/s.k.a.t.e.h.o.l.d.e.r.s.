package com.skateholders.skateholders.DTOs;

import java.time.LocalDate;

public class UsuarioConquistaOutputDTO {

    private Long usuarioId;
    private String usuarioLogin;

    private Long conquistaId;
    private String conquistaNome;

    private LocalDate dataConquista;

    public UsuarioConquistaOutputDTO() {}

    public UsuarioConquistaOutputDTO(Long usuarioId, String usuarioLogin, Long conquistaId, String conquistaNome, LocalDate dataConquista) {
        this.usuarioId = usuarioId;
        this.usuarioLogin = usuarioLogin;
        this.conquistaId = conquistaId;
        this.conquistaNome = conquistaNome;
        this.dataConquista = dataConquista;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public Long getConquistaId() {
        return conquistaId;
    }

    public void setConquistaId(Long conquistaId) {
        this.conquistaId = conquistaId;
    }

    public String getConquistaNome() {
        return conquistaNome;
    }

    public void setConquistaNome(String conquistaNome) {
        this.conquistaNome = conquistaNome;
    }

    public LocalDate getDataConquista() {
        return dataConquista;
    }

    public void setDataConquista(LocalDate dataConquista) {
        this.dataConquista = dataConquista;
    }
}
