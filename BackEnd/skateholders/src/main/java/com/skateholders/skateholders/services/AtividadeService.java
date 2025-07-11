package com.skateholders.skateholders.services;

import com.skateholders.skateholders.DTOs.AtividadeInputDTO;
import com.skateholders.skateholders.DTOs.AtividadeOutputDTO;
import com.skateholders.skateholders.models.Atividade;
import com.skateholders.skateholders.models.Sesh;
import com.skateholders.skateholders.models.Trick;
import com.skateholders.skateholders.models.TrickUsuario;
import com.skateholders.skateholders.models.Usuario;
import com.skateholders.skateholders.repositories.AtividadeRepository;
import com.skateholders.skateholders.repositories.SeshRepository;
import com.skateholders.skateholders.repositories.TrickRepository;
import com.skateholders.skateholders.repositories.TrickUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;
    @Autowired
    private TrickRepository trickRepository;
    @Autowired
    private SeshRepository seshRepository;
    @Autowired
    private TrickUsuarioRepository trickUsuarioRepository;

    @Transactional
    public AtividadeOutputDTO registrar(AtividadeInputDTO atividadeInputDTO) {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LocalDate hoje = LocalDate.now();

        Sesh seshDoDia = seshRepository.findByUsuarioAndData(usuarioLogado, hoje)
                .orElseGet(() -> {
                    Sesh novaSesh = new Sesh();
                    novaSesh.setUsuario(usuarioLogado);
                    novaSesh.setData(hoje);
                    return seshRepository.save(novaSesh);
                });

        Trick trickRealizada = trickRepository.findById(atividadeInputDTO.getTrickId())
                .orElseThrow(() -> new RuntimeException("Trick não encontrada!"));

        Atividade atividade = new Atividade();
        atividade.setSesh(seshDoDia);
        atividade.setTrick(trickRealizada);
        atividade.setHorario(LocalDateTime.now());
        atividade.setObstaculo(atividadeInputDTO.getObstaculo());
        Atividade savedAtividade = atividadeRepository.save(atividade);

        atualizarContadorDeAcertos(usuarioLogado, trickRealizada, true);

        return new AtividadeOutputDTO(savedAtividade);
    }

    @Transactional
    public AtividadeOutputDTO adicionarAtividadeEmSessaoExistente(Long seshId, AtividadeInputDTO atividadeInputDTO) {
        Sesh seshExistente = seshRepository.findById(seshId)
                .orElseThrow(() -> new RuntimeException("Sessão com ID " + seshId + " não encontrada."));

        Trick trickRealizada = trickRepository.findById(atividadeInputDTO.getTrickId())
                .orElseThrow(() -> new RuntimeException("Trick não encontrada!"));

        Atividade novaAtividade = new Atividade();
        novaAtividade.setSesh(seshExistente);
        novaAtividade.setTrick(trickRealizada);
        novaAtividade.setHorario(seshExistente.getData().atTime(23, 59));
        novaAtividade.setObstaculo(atividadeInputDTO.getObstaculo());
        Atividade savedAtividade = atividadeRepository.save(novaAtividade);

        Usuario usuarioDaSessao = seshExistente.getUsuario();
        atualizarContadorDeAcertos(usuarioDaSessao, trickRealizada, true);

        return new AtividadeOutputDTO(savedAtividade);
    }

    @Transactional
    public AtividadeOutputDTO atualizar(Long id, AtividadeInputDTO atividadeInputDTO) {
        Atividade atividadeExistente = atividadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada!"));

        Trick trickAntiga = atividadeExistente.getTrick();
        Trick trickNova = trickRepository.findById(atividadeInputDTO.getTrickId())
                .orElseThrow(() -> new RuntimeException("Trick não encontrada!"));

        Usuario usuario = atividadeExistente.getSesh().getUsuario();

        if (!trickAntiga.getId().equals(trickNova.getId())) {
            atualizarContadorDeAcertos(usuario, trickAntiga, false);
            atualizarContadorDeAcertos(usuario, trickNova, true);
        }

        atividadeExistente.setTrick(trickNova);
        atividadeExistente.setObstaculo(atividadeInputDTO.getObstaculo());
        Atividade updatedAtividade = atividadeRepository.save(atividadeExistente);

        return new AtividadeOutputDTO(updatedAtividade);
    }

    @Transactional
    public void deletar(Long id) {
        Atividade atividadeParaDeletar = atividadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atividade com ID " + id + " não encontrada para exclusão."));

        Usuario usuario = atividadeParaDeletar.getSesh().getUsuario();
        Trick trick = atividadeParaDeletar.getTrick();

        atualizarContadorDeAcertos(usuario, trick, false);

        atividadeRepository.delete(atividadeParaDeletar);
    }

    public List<AtividadeOutputDTO> listarTodos() {
        return atividadeRepository.findAll().stream()
                .map(AtividadeOutputDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<AtividadeOutputDTO> buscarPorId(Long id) {
        return atividadeRepository.findById(id).map(AtividadeOutputDTO::new);
    }

    private void atualizarContadorDeAcertos(Usuario usuario, Trick trick, boolean incrementar) {
        TrickUsuario trickUsuario = trickUsuarioRepository.findByUsuarioAndTrick(usuario, trick)
                .orElseThrow(() -> new IllegalStateException(
                        "Relação TrickUsuario não encontrada para o usuário " + usuario.getLogin() +
                                " e a trick " + trick.getNome() + ". A triagem pode não ter sido feita."
                ));

        if (incrementar) {
            trickUsuario.incrementarAcertos();
            // --- NOVA LÓGICA DE LEVEL UP AUTOMÁTICO ---
            verificarEAtualizarNivel(trickUsuario);
        } else {
            trickUsuario.decrementarAcertos();
        }

        trickUsuarioRepository.save(trickUsuario);
    }

    /**
     * NOVA LÓGICA: Verifica se o número de acertos atingiu um novo patamar
     * e atualiza o nível do usuário para aquela trick.
     */
    private void verificarEAtualizarNivel(TrickUsuario trickUsuario) {
        int acertos = trickUsuario.getAcertos();
        int nivelAtual = trickUsuario.getNivel();

        // Nível 1 = Iniciante, 2 = Intermediário, 3 = Avançado
        if (acertos >= 50 && nivelAtual < 3) {
            trickUsuario.setNivel(3); // Avançado
        } else if (acertos >= 10 && nivelAtual < 2) {
            trickUsuario.setNivel(2); // Intermediário
        }
    }
}