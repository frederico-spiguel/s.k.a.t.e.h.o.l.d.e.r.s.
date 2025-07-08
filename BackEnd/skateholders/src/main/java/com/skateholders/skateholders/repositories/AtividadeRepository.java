package com.skateholders.skateholders.repositories;

import com.skateholders.skateholders.models.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    // Aqui podemos adicionar consultas customizadas se necessário
}
