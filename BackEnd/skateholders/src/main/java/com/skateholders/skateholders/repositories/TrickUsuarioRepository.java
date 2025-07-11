package com.skateholders.skateholders.repositories;

import com.skateholders.skateholders.models.Trick;
import com.skateholders.skateholders.models.TrickUsuario;
import com.skateholders.skateholders.models.TrickUsuarioId;
import com.skateholders.skateholders.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TrickUsuarioRepository extends JpaRepository<TrickUsuario, TrickUsuarioId> {

    Optional<TrickUsuario> findByUsuarioAndTrick(Usuario usuario, Trick trick);

    // --- NOVO MTODO PARA A TELA DE PERFIL ---
    // Busca todas as relações de um usuário, já trazendo os dados da trick junto para otimizar.
    @Query("SELECT tu FROM TrickUsuario tu JOIN FETCH tu.trick WHERE tu.usuario = :usuario")
    List<TrickUsuario> findAllByUsuarioWithTrick(@Param("usuario") Usuario usuario);

}