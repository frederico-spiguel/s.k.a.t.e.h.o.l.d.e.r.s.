package com.skateholders.skateholders.DTOs;

import java.time.LocalDate;

public class SeshOutputDTO {
    private Long id;
    private String usuarioLogin;
    private LocalDate data;
    private boolean aberta;
    private boolean editavel;

    public SeshOutputDTO(Long id, String usuarioLogin, LocalDate data, boolean aberta, boolean editavel) {
        this.id = id;
        this.usuarioLogin = usuarioLogin;
        this.data = data;
        this.aberta = aberta;
        this.editavel = editavel;
    }

    // Getters e Setters

    public Long getId() { return id; }
    public String getUsuarioLogin() { return usuarioLogin; }
    public LocalDate getData() { return data; }
    public boolean isAberta() { return aberta; }
    public boolean isEditavel() { return editavel; }
}
