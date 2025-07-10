package com.skateholders.skateholders.repositories;

import com.skateholders.skateholders.models.Atividade;
import com.skateholders.skateholders.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    long countBySesh_Usuario(Usuario usuario);

    // Aqui podemos adicionar consultas customizadas se necessário
}
