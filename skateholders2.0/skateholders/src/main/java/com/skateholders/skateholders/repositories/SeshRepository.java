package com.skateholders.skateholders.repositories;

import com.skateholders.skateholders.models.Sesh;
import com.skateholders.skateholders.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SeshRepository extends JpaRepository<Sesh, Long> {
    Optional<Sesh> findByUsuarioAndData(Usuario usuario, LocalDate data);
}
