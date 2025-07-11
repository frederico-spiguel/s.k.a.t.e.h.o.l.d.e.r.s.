package com.skateholders.skateholders.controllers;

import com.skateholders.skateholders.DTOs.GraficoOpcaoDTO;
import com.skateholders.skateholders.DTOs.GraficoPontoDTO;
import com.skateholders.skateholders.services.GraficoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller responsável por expor os endpoints da API
 * para a funcionalidade de gráficos de evolução.
 */
@RestController
@RequestMapping("/api/graficos")
public class GraficoController {

    @Autowired
    private GraficoService graficoService;

    /**
     * Endpoint principal para buscar os dados de evolução para um gráfico.
     * @param tipo O tipo de filtro (ex: "trick", "base", "dificuldade", "todos").
     * @param valor O valor do filtro (ex: ID da trick/base ou "facil", "media", "dificil").
     * @return Uma lista de pontos para o gráfico.
     */
    @GetMapping("/evolucao")
    public ResponseEntity<List<GraficoPontoDTO>> getDadosEvolucao(
            @RequestParam String tipo,
            @RequestParam(required = false, defaultValue = "0") String valor
    ) {
        List<GraficoPontoDTO> dados = graficoService.getDadosEvolucao(tipo, valor);
        return ResponseEntity.ok(dados);
    }

    /**
     * Endpoint para o front-end buscar a lista de manobras que podem ser
     * usadas como filtro.
     */
    @GetMapping("/opcoes/tricks")
    public ResponseEntity<List<GraficoOpcaoDTO>> getOpcoesTricks() {
        List<GraficoOpcaoDTO> opcoes = graficoService.getOpcoesDeTricks();
        return ResponseEntity.ok(opcoes);
    }

    /**
     * Endpoint para o front-end buscar a lista de bases de skate
     * que podem ser usadas como filtro.
     */
    @GetMapping("/opcoes/bases")
    public ResponseEntity<List<GraficoOpcaoDTO>> getOpcoesBases() {
        List<GraficoOpcaoDTO> opcoes = graficoService.getOpcoesDeBases();
        return ResponseEntity.ok(opcoes);
    }
}