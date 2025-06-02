package com.skateholders.skateholders.repositories;

import com.skateholders.skateholders.models.UsuarioConquista;
import com.skateholders.skateholders.models.UsuarioConquistaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioConquistaRepository extends JpaRepository<UsuarioConquista, UsuarioConquistaId> {

}
