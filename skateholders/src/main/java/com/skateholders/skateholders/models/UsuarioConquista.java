package com.skateholders.skateholders.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "usuario_conquista")
public class UsuarioConquista {

    @EmbeddedId
    private UsuarioConquistaId id;

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @MapsId("conquistaId")
    @JoinColumn(name = "conquista_id")
    private Conquista conquista;

    private LocalDate dataConquista;

    public UsuarioConquista() {}

    public UsuarioConquista(Usuario usuario, Conquista conquista, LocalDate dataConquista) {
        this.usuario = usuario;
        this.conquista = conquista;
        this.dataConquista = dataConquista;
        this.id = new UsuarioConquistaId(usuario.getId(), conquista.getId());
    }

    // getters e setters

    public UsuarioConquistaId getId() {
        return id;
    }

    public void setId(UsuarioConquistaId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Conquista getConquista() {
        return conquista;
    }

    public void setConquista(Conquista conquista) {
        this.conquista = conquista;
    }

    public LocalDate getDataConquista() {
        return dataConquista;
    }

    public void setDataConquista(LocalDate dataConquista) {
        this.dataConquista = dataConquista;
    }
}
