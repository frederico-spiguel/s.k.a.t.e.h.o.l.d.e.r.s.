package com.skateholders.skateholders.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "sesh")
public class Sesh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private boolean aberta = true;

    @Column(nullable = false)
    private boolean editavel = true;

    public Sesh() {}

    public Sesh(Usuario usuario, LocalDate data) {
        this.usuario = usuario;
        this.data = data;
    }

    // Getters e Setters

    public Long getId() { return id; }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDate getData() { return data; }

    public void setData(LocalDate data) { this.data = data; }

    public boolean isAberta() { return aberta; }

    public void setAberta(boolean aberta) { this.aberta = aberta; }

    public boolean isEditavel() { return editavel; }

    public void setEditavel(boolean editavel) { this.editavel = editavel; }
}
