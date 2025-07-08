package com.skateholders.skateholders.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TrickUsuarioId implements Serializable {

    private Long usuarioId;
    private Long trickId;

    public TrickUsuarioId() {}

    public TrickUsuarioId(Long usuarioId, Long trickId) {
        this.usuarioId = usuarioId;
        this.trickId = trickId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getTrickId() {
        return trickId;
    }

    public void setTrickId(Long trickId) {
        this.trickId = trickId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrickUsuarioId that)) return false;
        return Objects.equals(usuarioId, that.usuarioId) && Objects.equals(trickId, that.trickId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, trickId);
    }
}
