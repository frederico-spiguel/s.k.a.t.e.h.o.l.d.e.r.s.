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

    Optional<Sesh> findByUsuarioAndData(Usuario usuario, LocalDate data);

    // --- NOVO MÉTODO ADICIONADO ---
    // Busca apenas as datas distintas das sessões de um usuário, ordenadas da mais recente para a mais antiga.
    @Query("SELECT DISTINCT s.data FROM Sesh s WHERE s.usuario = :usuario ORDER BY s.data DESC")
    List<LocalDate> findDistinctSeshDatesByUsuario(@Param("usuario") Usuario usuario);
}