package com.skateholders.skateholders.repositories;

import com.skateholders.skateholders.DTOs.GraficoOpcaoDTO;
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

    // Adicionado DISTINCT para garantir a consistência dos resultados com JOIN FETCH
    @Query("SELECT DISTINCT tu FROM TrickUsuario tu JOIN FETCH tu.trick WHERE tu.usuario = :usuario")
    List<TrickUsuario> findAllByUsuarioWithTrick(@Param("usuario") Usuario usuario);

    // --- QUERY CORRIGIDA COM DISTINCT ---
    @Query("SELECT DISTINCT tu FROM TrickUsuario tu JOIN FETCH tu.trick WHERE tu.usuario = :usuario AND tu.acertos > 0 ORDER BY tu.trick.nome ASC")
    List<TrickUsuario> findTricksComAcertosByUsuario(@Param("usuario") Usuario usuario);
}