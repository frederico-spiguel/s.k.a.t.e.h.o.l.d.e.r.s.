package com.skateholders.skateholders.repositories;

import com.skateholders.skateholders.DTOs.GraficoPontoDTO; // <-- Import necessário
import com.skateholders.skateholders.models.Atividade;
import com.skateholders.skateholders.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // <-- Import necessário
import org.springframework.data.repository.query.Param; // <-- Import necessário
import org.springframework.stereotype.Repository;

import java.util.List; // <-- Import necessário

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

    long countBySesh_Usuario(Usuario usuario);

    /**
     * NOVO MÉTODO: Chama a query nativa nomeada 'Atividade.calcularEvolucao'
     * que definimos no arquivo jpa-named-queries.properties.
     * Esta é a query que faz todo o cálculo pesado de acertos acumulados.
     */
    @Query(nativeQuery = true, name = "Atividade.calcularEvolucao")
    List<GraficoPontoDTO> calcularEvolucaoAcertos(
            @Param("usuarioId") Long usuarioId,
            @Param("tipoFiltro") String tipoFiltro,
            @Param("valorFiltro") String valorFiltro
    );

}