package com.skateholders.skateholders.DTOs;

public class TrickUsuarioOutputDTO {
    private Long usuarioId;
    private String usuarioLogin;

    private Long trickId;
    private String trickNome;
    private String baseDescricao;

    private int nivel;
    private int acertos;
    private boolean proficiencia;


    public TrickUsuarioOutputDTO() {}

    public TrickUsuarioOutputDTO(Long usuarioId, String usuarioLogin,
                                 Long trickId, String trickNome, String baseDescricao,
                                 int nivel, int acertos, boolean proficiencia) {
        this.usuarioId = usuarioId;
        this.usuarioLogin = usuarioLogin;
        this.trickId = trickId;
        this.trickNome = trickNome;
        this.baseDescricao = baseDescricao;
        this.nivel = nivel;
        this.acertos = acertos;
        this.proficiencia = proficiencia;
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

    public String getBaseDescricao() {
        return baseDescricao;
    }

    public void setBaseDescricao(String baseDescricao) {
        this.baseDescricao = baseDescricao;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getAcertos() {
        return acertos;
    }

    public void setAcertos(int acertos) {
        this.acertos = acertos;
    }

    public boolean isProficiencia() {
        return proficiencia;
    }

    public void setProficiencia(boolean proficiencia) {
        this.proficiencia = proficiencia;
    }

    // Construtor, Getters e Setters
}

