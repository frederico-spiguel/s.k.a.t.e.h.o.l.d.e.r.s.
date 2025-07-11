package com.skateholders.skateholders.services;

import com.skateholders.skateholders.DTOs.PerfilDTO;
import com.skateholders.skateholders.DTOs.TrickStatusDTO;
import com.skateholders.skateholders.models.TrickUsuario;
import com.skateholders.skateholders.models.Usuario;
import com.skateholders.skateholders.mongoDocs.ConquistaDoc;
import com.skateholders.skateholders.mongoDocs.UsuarioConquistaDoc;
import com.skateholders.skateholders.mongoReps.ConquistaDocRepository;
import com.skateholders.skateholders.mongoReps.UsuarioConquistaDocRepository;
import com.skateholders.skateholders.repositories.AtividadeRepository;
import com.skateholders.skateholders.repositories.SeshRepository;
import com.skateholders.skateholders.repositories.TrickUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
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

    private TrickStatusDTO converterParaTrickStatusDTO(TrickUsuario tu) {
        TrickStatusDTO dto = new TrickStatusDTO();
        dto.setNomeTrick(tu.getTrick().getNome());
        dto.setAcertos(tu.getAcertos());
        dto.setProficiencia(tu.isProficiencia());

        // Lógica para o status "aceso" (continua a mesma)
        boolean isAceso = tu.getAcertos() > 0 || tu.getNivel() > 1;
        dto.setAceso(isAceso);

        // --- NOVA LÓGICA DE TRADUÇÃO DO NÍVEL ---
        dto.setNivel(converterNivelParaString(tu.getNivel()));

        return dto;
    }

    /**
     * NOVO MÉTODO PRIVADO: Converte o nível de int para String.
     */
    private String converterNivelParaString(int nivel) {
        switch (nivel) {
            case 1:
                return "Iniciante";
            case 2:
                return "Intermediário";
            case 3:
                return "Avançado";
            default:
                return "Indefinido";
        }
    }
}