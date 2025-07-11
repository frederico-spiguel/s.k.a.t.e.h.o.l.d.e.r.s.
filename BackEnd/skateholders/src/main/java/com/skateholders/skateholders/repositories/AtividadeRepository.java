package com.skateholders.skateholders.repositories;

import com.skateholders.skateholders.models.Atividade;
import com.skateholders.skateholders.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

    long countBySesh_Usuario(Usuario usuario);

    /**
     * MTODO ATUALIZADO: Agora ele retorna os dados brutos da query
     * como uma Lista de Arrays de Objetos, para evitar o bug de instanciação do DTO.
     */
    @Query(nativeQuery = true, name = "Atividade.calcularEvolucao")
    List<Object[]> calcularEvolucaoAcertos(
            @Param("usuarioId") Long usuarioId,
            @Param("tipoFiltro") String tipoFiltro,
            @Param("valorFiltro") String valorFiltro
    );
}