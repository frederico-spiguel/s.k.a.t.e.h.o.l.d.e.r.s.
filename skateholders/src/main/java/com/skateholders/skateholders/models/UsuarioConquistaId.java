package com.skateholders.skateholders.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UsuarioConquistaId implements Serializable {

    private Long usuarioId;
    private Long conquistaId;

    public UsuarioConquistaId() {}

    public UsuarioConquistaId(Long usuarioId, Long conquistaId) {
        this.usuarioId = usuarioId;
        this.conquistaId = conquistaId;
    }

    // getters e setters
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

    // equals e hashCode (muito importantes para chave composta)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsuarioConquistaId)) return false;
        UsuarioConquistaId that = (UsuarioConquistaId) o;
        return Objects.equals(getUsuarioId(), that.getUsuarioId()) &&
                Objects.equals(getConquistaId(), that.getConquistaId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsuarioId(), getConquistaId());
    }
}
