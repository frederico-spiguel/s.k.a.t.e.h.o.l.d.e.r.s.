package com.skateholders.skateholders.repositories;

import com.skateholders.skateholders.models.Sesh;
import com.skateholders.skateholders.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SeshRepository extends JpaRepository<Sesh, Long> {

    /**
     * MODO OTIMIZADO PARA RESOLVER O PROBLEMA N+1.
     * Esta query customizada busca a Sesh e, ao mesmo tempo, já carrega (FETCH)
     * a lista de 'atividades' e, para cada atividade, também carrega a 'trick' associada.
     * Isso transforma as N+1 queries em uma única e eficiente consulta ao banco de dados.
     * @param usuario O usuário dono da sessão.
     * @param data A data da sessão.
     * @return Um Optional contendo a Sesh com todas as suas associações já carregadas.
     */
    @Query("SELECT s FROM Sesh s LEFT JOIN FETCH s.atividades a LEFT JOIN FETCH a.trick WHERE s.usuario = :usuario AND s.data = :data")
    Optional<Sesh> findByUsuarioAndData(@Param("usuario") Usuario usuario, @Param("data") LocalDate data);


    // --- SEU OUTRO MTODO (PERMANECE INTACTO) ---
    @Query("SELECT DISTINCT s.data FROM Sesh s WHERE s.usuario = :usuario ORDER BY s.data DESC")
    List<LocalDate> findDistinctSeshDatesByUsuario(@Param("usuario") Usuario usuario);
}