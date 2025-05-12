package com.skateholders.skateholders.repositories;
import com.skateholders.skateholders.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Usuario findByLogin(String login);
}
