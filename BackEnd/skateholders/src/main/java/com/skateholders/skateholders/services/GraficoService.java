package com.skateholders.skateholders.services;

import com.skateholders.skateholders.DTOs.GraficoOpcaoDTO;
import com.skateholders.skateholders.DTOs.GraficoPontoDTO;
import com.skateholders.skateholders.models.Usuario;
import com.skateholders.skateholders.repositories.AtividadeRepository;
import com.skateholders.skateholders.repositories.BaseRepository;
import com.skateholders.skateholders.repositories.TrickUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GraficoService {

    @Autowired private AtividadeRepository atividadeRepository;
    @Autowired private TrickUsuarioRepository trickUsuarioRepository;
    @Autowired private BaseRepository baseRepository;

    public List<GraficoPontoDTO> getDadosEvolucao(String tipo, String valor) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String tipoFiltro = tipo;
        if ("dificuldade".equals(tipo)) {
            switch (valor) {
                case "facil": tipoFiltro = "dificuldade_facil"; break;
                case "media": tipoFiltro = "dificuldade_media"; break;
                case "dificil": tipoFiltro = "dificuldade_dificil"; break;
                default: tipoFiltro = "todos"; break;
            }
            valor = "0";
        }

        List<Object[]> resultadosBrutos = atividadeRepository.calcularEvolucaoAcertos(usuario.getId(), tipoFiltro, valor);

        List<GraficoPontoDTO> dadosGrafico = new ArrayList<>();

        for (Object[] resultado : resultadosBrutos) {
            GraficoPontoDTO ponto = new GraficoPontoDTO();

            // --- AQUI ESTÁ A CORREÇÃO FINAL ---
            // Agora tratamos TODOS os resultados numéricos como 'Number'
            // para máxima compatibilidade.
            ponto.setSessaoIndex(((Number) resultado[0]).longValue()); // sessaoIndex
            ponto.setValor(((Number) resultado[1]).longValue());       // valor acumulado
            ponto.setData(((Date) resultado[2]).toLocalDate());      // data

            dadosGrafico.add(ponto);
        }

        return dadosGrafico;
    }

    public List<GraficoOpcaoDTO> getOpcoesDeTricks() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return trickUsuarioRepository.findTricksComAcertosByUsuario(usuario)
                .stream()
                .map(trickUsuario -> new GraficoOpcaoDTO(
                        trickUsuario.getTrick().getId(),
                        trickUsuario.getTrick().getNome()
                ))
                .collect(Collectors.toList());
    }

    public List<GraficoOpcaoDTO> getOpcoesDeBases() {
        return baseRepository.findAll().stream()
                .map(base -> new GraficoOpcaoDTO(base.getId(), base.getDescricao()))
                .collect(Collectors.toList());
    }
}