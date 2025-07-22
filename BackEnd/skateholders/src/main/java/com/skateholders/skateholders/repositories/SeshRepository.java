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

    // --- VERSÃO ANTIGA (ANTES DA OTIMIZAÇÃO) ---
    // Optional<Sesh> findByUsuarioAndData(Usuario usuario, LocalDate data);

    @Query("SELECT s FROM Sesh s LEFT JOIN FETCH s.atividades a LEFT JOIN FETCH a.trick WHERE s.usuario = :usuario AND s.data = :data")
    Optional<Sesh> findByUsuarioAndData(@Param("usuario") Usuario usuario, @Param("data") LocalDate data);

    @Query("SELECT DISTINCT s.data FROM Sesh s WHERE s.usuario = :usuario ORDER BY s.data DESC")
    List<LocalDate> findDistinctSeshDatesByUsuario(@Param("usuario") Usuario usuario);

    long countByUsuario(Usuario usuario);
}