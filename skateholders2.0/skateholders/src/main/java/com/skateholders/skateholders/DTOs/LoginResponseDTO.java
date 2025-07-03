package com.skateholders.skateholders.DTOs;

public class LoginResponseDTO {
    private String token;
    private boolean fezTriagem;

    public LoginResponseDTO(String token) {
        this.token = token;

    }

    public LoginResponseDTO(String token, boolean fezTriagem) {
        this.token = token;
        this.fezTriagem = fezTriagem;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isFezTriagem() {
        return fezTriagem;
    }

    public void setFezTriagem(boolean fezTriagem) {
        this.fezTriagem = fezTriagem;
    }
}
