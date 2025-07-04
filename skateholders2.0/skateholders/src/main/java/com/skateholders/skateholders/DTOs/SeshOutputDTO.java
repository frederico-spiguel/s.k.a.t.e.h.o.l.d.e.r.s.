package com.skateholders.skateholders.DTOs;

import com.skateholders.skateholders.models.Sesh;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class SeshOutputDTO {
    private Long id;
    private LocalDate data;
    private boolean editavel;
    private List<AtividadeOutputDTO> atividades;

    // Construtor vazio
    public SeshOutputDTO() {}

    // Construtor principal que faz toda a mágica
    public SeshOutputDTO(Sesh sesh) {
        this.id = sesh.getId();
        this.data = sesh.getData();

        // Lógica dos 3 dias para definir se é editável
        long diasDesdeASessao = ChronoUnit.DAYS.between(sesh.getData(), LocalDate.now());
        this.editavel = diasDesdeASessao <= 3;

        // Mapeia a lista de entidades 'Atividade' para uma lista de 'AtividadeOutputDTO'
        if (sesh.getAtividades() != null) {
            this.atividades = sesh.getAtividades().stream()
                    .map(AtividadeOutputDTO::new) // Usando o construtor do AtividadeOutputDTO
                    .collect(Collectors.toList());
        }
    }

    // --- GETTERS E SETTERS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isEditavel() {
        return editavel;
    }

    public void setEditavel(boolean editavel) {
        this.editavel = editavel;
    }

    public List<AtividadeOutputDTO> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<AtividadeOutputDTO> atividades) {
        this.atividades = atividades;
    }
}