// UsuarioConquistaDoc.java

package com.skateholders.skateholders.mongoDocs; // Verifique se o pacote está correto

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "conquistas_desbloqueadas")
public class UsuarioConquistaDoc {

    @Id
    private String id;

    private Long usuarioId; // Referência ao ID do usuário no PostgreSQL
    private String conquistaId; // Referência ao ID do documento de conquista no MongoDB

    private LocalDateTime dataDesbloqueio;

    // Construtores
    public UsuarioConquistaDoc() {}

    public UsuarioConquistaDoc(Long usuarioId, String conquistaId) {
        this.usuarioId = usuarioId;
        this.conquistaId = conquistaId;
        this.dataDesbloqueio = LocalDateTime.now();
    }

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public String getConquistaId() { return conquistaId; }
    public void setConquistaId(String conquistaId) { this.conquistaId = conquistaId; }
    public LocalDateTime getDataDesbloqueio() { return dataDesbloqueio; }
    public void setDataDesbloqueio(LocalDateTime dataDesbloqueio) { this.dataDesbloqueio = dataDesbloqueio; }
}