// UsuarioConquistaDocRepository.java

package com.skateholders.skateholders.mongoReps; // Verifique o pacote

import com.skateholders.skateholders.mongoDocs.UsuarioConquistaDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioConquistaDocRepository extends MongoRepository<UsuarioConquistaDoc, String> {

    // Mtodo para buscar todas as conquistas de um usuário específico
    List<UsuarioConquistaDoc> findByUsuarioId(Long usuarioId);

    // Metodo para verificar se um usuário já desbloqueou uma conquista específica
    Optional<UsuarioConquistaDoc> findByUsuarioIdAndConquistaId(Long usuarioId, String conquistaId);
}