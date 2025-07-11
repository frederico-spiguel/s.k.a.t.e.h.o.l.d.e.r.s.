package com.skateholders.skateholders.DTOs;

import java.util.List;

public class PerfilDTO {
    private String nomeUsuario;
    private int pontosConquistas;
    private long totalSessoes;
    private long totalAtividades;
    private List<TrickStatusDTO> manobrasStatus;

    // Getters e Setters para todos os campos
    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
    public int getPontosConquistas() { return pontosConquistas; }
    public void setPontosConquistas(int pontosConquistas) { this.pontosConquistas = pontosConquistas; }
    public long getTotalSessoes() { return totalSessoes; }
    public void setTotalSessoes(long totalSessoes) { this.totalSessoes = totalSessoes; }
    public long getTotalAtividades() { return totalAtividades; }
    public void setTotalAtividades(long totalAtividades) { this.totalAtividades = totalAtividades; }
    public List<TrickStatusDTO> getManobrasStatus() { return manobrasStatus; }
    public void setManobrasStatus(List<TrickStatusDTO> manobrasStatus) { this.manobrasStatus = manobrasStatus; }
}