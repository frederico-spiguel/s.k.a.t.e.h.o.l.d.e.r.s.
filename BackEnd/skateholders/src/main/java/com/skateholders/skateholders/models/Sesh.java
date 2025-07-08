package com.skateholders.skateholders.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sesh")
public class Sesh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Otimização: não carrega o usuário a menos que seja pedido
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDate data;

    // --- MUDANÇA PRINCIPAL ---
    // Adicionamos a lista de atividades com o mapeamento correto
    @OneToMany(
            mappedBy = "sesh", // Diz que a relação é gerenciada pelo campo "sesh" na entidade Atividade
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JsonManagedReference // Ajuda a evitar loops infinitos na serialização para JSON
    private List<Atividade> atividades = new ArrayList<>();


    // CONSTRUTORES
    public Sesh() {}

    public Sesh(Usuario usuario, LocalDate data) {
        this.usuario = usuario;
        this.data = data;
    }

    // GETTERS E SETTERS

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDate getData() { return data; }

    public void setData(LocalDate data) { this.data = data; }

    public List<Atividade> getAtividades() { return atividades; }

    public void setAtividades(List<Atividade> atividades) { this.atividades = atividades; }
}