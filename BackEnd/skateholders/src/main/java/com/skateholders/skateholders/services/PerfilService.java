package com.skateholders.skateholders.services;

import com.skateholders.skateholders.DTOs.AtividadeInputDTO;
import com.skateholders.skateholders.DTOs.PerfilDTO;
import com.skateholders.skateholders.DTOs.ProficienciaRequestDTO;
import com.skateholders.skateholders.DTOs.TrickStatusDTO;
import com.skateholders.skateholders.models.Trick;
import com.skateholders.skateholders.models.TrickUsuario;
import com.skateholders.skateholders.models.Usuario;
import com.skateholders.skateholders.mongoDocs.ConquistaDoc;
import com.skateholders.skateholders.mongoDocs.UsuarioConquistaDoc;
import com.skateholders.skateholders.mongoReps.ConquistaDocRepository;
import com.skateholders.skateholders.mongoReps.UsuarioConquistaDocRepository;
import com.skateholders.skateholders.repositories.AtividadeRepository;
import com.skateholders.skateholders.repositories.SeshRepository;
import com.skateholders.skateholders.repositories.TrickRepository;
import com.skateholders.skateholders.repositories.TrickUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PerfilService {

    @Autowired private AtividadeRepository atividadeRepository;
    @Autowired private SeshRepository seshRepository;
    @Autowired private TrickUsuarioRepository trickUsuarioRepository;
    @Autowired private UsuarioConquistaDocRepository usuarioConquistaRepository;
    @Autowired private ConquistaDocRepository conquistaRepository;
    @Autowired private AtividadeService atividadeService;
    @Autowired private TrickRepository trickRepository;

    public PerfilDTO getPerfilDoUsuarioLogado() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PerfilDTO perfilDTO = new PerfilDTO();
        perfilDTO.setNomeUsuario(usuario.getUsername());
        perfilDTO.setTotalSessoes(seshRepository.countByUsuario(usuario));
        perfilDTO.setTotalAtividades(atividadeRepository.countBySesh_Usuario(usuario));
        List<TrickUsuario> manobras = trickUsuarioRepository.findAllByUsuarioWithTrick(usuario);
        List<TrickStatusDTO> manobrasStatus = manobras.stream()
                .map(this::converterParaTrickStatusDTO)
                .sorted(Comparator.comparing(TrickStatusDTO::isAceso).reversed())
                .collect(Collectors.toList());
        perfilDTO.setManobrasStatus(manobrasStatus);
        List<UsuarioConquistaDoc> conquistasDesbloqueadas = usuarioConquistaRepository.findByUsuarioId(usuario.getId());
        int totalPontos = conquistasDesbloqueadas.stream()
                .map(uc -> conquistaRepository.findById(uc.getConquistaId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .mapToInt(ConquistaDoc::getPontos)
                .sum();
        perfilDTO.setPontosConquistas(totalPontos);
        return perfilDTO;
    }

    @Transactional
    public boolean solicitarProficiencia(ProficienciaRequestDTO dto) {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Trick trick = trickRepository.findById(dto.getTrickId())
                .orElseThrow(() -> new RuntimeException("Trick não encontrada com o ID: " + dto.getTrickId()));

        TrickUsuario trickUsuario = trickUsuarioRepository.findByUsuarioAndTrick(usuario, trick)
                .orElseThrow(() -> new IllegalStateException("Relação TrickUsuario não encontrada."));

        // 1. Validações iniciais (pré-requisitos)
        if (trickUsuario.isProficiencia()) {
            throw new IllegalStateException("Você já possui proficiência nesta manobra.");
        }
        if (trickUsuario.getNivel() < 2) { // Nível 2 = Intermediário
            throw new IllegalStateException("É necessário ser nível Intermediário para pedir proficiência.");
        }

        // 2. Registra os acertos reportados como atividades.
        // Isso agora acontece SEMPRE, independentemente do resultado.
        if (dto.getAcertosReportados() > 0) {
            for (int i = 0; i < dto.getAcertosReportados(); i++) {
                AtividadeInputDTO atividadeDTO = new AtividadeInputDTO();
                atividadeDTO.setTrickId(trick.getId());
                atividadeDTO.setObstaculo("flatground");
                // A chamada a este método já vai incrementar o contador e verificar o level up.
                atividadeService.registrar(atividadeDTO);
            }
        }

        // 3. Verifica se ele passou no teste de proficiência.
        boolean passouNoTeste = dto.getAcertosReportados() >= 7;

        if (passouNoTeste) {
            // Recarrega a entidade TrickUsuario para garantir que temos os acertos atualizados.
            TrickUsuario trickUsuarioAtualizado = trickUsuarioRepository.findByUsuarioAndTrick(usuario, trick)
                    .orElseThrow(() -> new IllegalStateException("Erro ao recarregar TrickUsuario após atualização de acertos."));

            trickUsuarioAtualizado.setProficiencia(true);
            trickUsuarioAtualizado.setNivel(3); // Garante a promoção para Avançado
            trickUsuarioRepository.save(trickUsuarioAtualizado);
            return true; // Sucesso!
        }

        return false; // Falha (não atingiu os 7 acertos)
    }

    private TrickStatusDTO converterParaTrickStatusDTO(TrickUsuario tu) {
        TrickStatusDTO dto = new TrickStatusDTO();
        dto.setTrickId(tu.getTrick().getId());
        dto.setNomeTrick(tu.getTrick().getNome());
        dto.setAcertos(tu.getAcertos());
        dto.setProficiencia(tu.isProficiencia());
        boolean isAceso = tu.getAcertos() > 0 || tu.getNivel() > 1;
        dto.setAceso(isAceso);
        dto.setNivel(converterNivelParaString(tu.getNivel()));
        return dto;
    }

    private String converterNivelParaString(int nivel) {
        switch (nivel) {
            case 1: return "Iniciante";
            case 2: return "Intermediário";
            case 3: return "Avançado";
            default: return "Indefinido";
        }
    }
}