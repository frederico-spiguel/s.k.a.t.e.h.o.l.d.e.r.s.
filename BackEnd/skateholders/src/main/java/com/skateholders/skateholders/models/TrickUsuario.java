package com.skateholders.skateholders.models;

import jakarta.persistence.*;

@Entity
@Table(name = "trick_usuario")
public class TrickUsuario {

    @EmbeddedId
    private TrickUsuarioId id;

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @MapsId("trickId")
    @JoinColumn(name = "trick_id")
    private Trick trick;

    private int nivel;
    private int acertos;

    private boolean proficiencia = false;

    public TrickUsuario() {}

    public TrickUsuario(Usuario usuario, Trick trick, int nivel) {
        this.usuario = usuario;
        this.trick = trick;
        this.nivel = nivel;
        this.acertos = 0; // inicia zerado
        this.proficiencia = false;
        this.id = new TrickUsuarioId(usuario.getId(), trick.getId());
    }

    public TrickUsuarioId getId() {
        return id;
    }

    public void setId(TrickUsuarioId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Trick getTrick() {
        return trick;
    }

    public void setTrick(Trick trick) {
        this.trick = trick;
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
}
