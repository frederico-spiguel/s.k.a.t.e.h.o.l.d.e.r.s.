package com.skateholders.skateholders.DTOs;

import java.time.LocalDate;

public class UsuarioConquistaInputDTO {

    private Long usuarioId;
    private Long conquistaId;
    private LocalDate dataConquista;

    public UsuarioConquistaInputDTO() {}

    public UsuarioConquistaInputDTO(Long usuarioId, Long conquistaId, LocalDate dataConquista) {
        this.usuarioId = usuarioId;
        this.conquistaId = conquistaId;
        this.dataConquista = dataConquista;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getConquistaId() {
        return conquistaId;
    }

    public void setConquistaId(Long conquistaId) {
        this.conquistaId = conquistaId;
    }

    public LocalDate getDataConquista() {
        return dataConquista;
    }

    public void setDataConquista(LocalDate dataConquista) {
        this.dataConquista = dataConquista;
    }
}
