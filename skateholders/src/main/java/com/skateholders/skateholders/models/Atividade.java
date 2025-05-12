package com.skateholders.skateholders.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "atividade")
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "trick_id")
    private Trick trick;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sesh_id")
    private Sesh sesh;

    @Column(nullable = false)
    private LocalDateTime horario;

    @Column(nullable = false)
    private String obstaculo = "Flat";

    public Atividade() {}

    public Atividade(Trick trick, Sesh sesh, LocalDateTime horario, String obstaculo) {
        this.trick = trick;
        this.sesh = sesh;
        this.horario = horario;
        this.obstaculo = obstaculo;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public Trick getTrick() {
        return trick;
    }

    public void setTrick(Trick trick) {
        this.trick = trick;
    }

    public Sesh getSesh() {
        return sesh;
    }

    public void setSesh(Sesh sesh) {
        this.sesh = sesh;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public String getObstaculo() {
        return obstaculo;
    }

    public void setObstaculo(String obstaculo) {
        this.obstaculo = obstaculo;
    }
}
