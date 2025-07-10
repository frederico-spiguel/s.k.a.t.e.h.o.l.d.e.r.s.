// TrickUsuarioRepository.java (COMPLETO E ATUALIZADO)

package com.skateholders.skateholders.repositories;

import com.skateholders.skateholders.models.Trick;
import com.skateholders.skateholders.models.TrickUsuario;
import com.skateholders.skateholders.models.TrickUsuarioId;
import com.skateholders.skateholders.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional; // Não se esqueça de importar o Optional

public interface TrickUsuarioRepository extends JpaRepository<TrickUsuario, TrickUsuarioId> {

    /**
     * NOVO MTODO: Encontra a relação TrickUsuario específica para uma combinação
     * de usuário e trick. Será a nossa principal ferramenta para atualizar os acertos.
     */
    Optional<TrickUsuario> findByUsuarioAndTrick(Usuario usuario, Trick trick);

}