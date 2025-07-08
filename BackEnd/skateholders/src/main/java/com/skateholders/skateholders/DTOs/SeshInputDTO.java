package com.skateholders.skateholders.DTOs;

import java.time.LocalDate;

public class SeshInputDTO {
    private Long usuarioId;
    private LocalDate data;

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
}
