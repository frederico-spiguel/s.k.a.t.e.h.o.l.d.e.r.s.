package com.skateholders.skateholders.repositories;

import com.skateholders.skateholders.models.TrickUsuario;
import com.skateholders.skateholders.models.TrickUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrickUsuarioRepository extends JpaRepository<TrickUsuario, TrickUsuarioId> {
}
