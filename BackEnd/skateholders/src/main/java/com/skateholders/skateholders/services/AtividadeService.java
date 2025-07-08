package com.skateholders.skateholders.services;

import com.skateholders.skateholders.DTOs.AtividadeInputDTO;
import com.skateholders.skateholders.DTOs.AtividadeOutputDTO;
import com.skateholders.skateholders.models.Atividade;
import com.skateholders.skateholders.models.Sesh;
import com.skateholders.skateholders.models.Trick;
import com.skateholders.skateholders.models.Usuario;
import com.skateholders.skateholders.repositories.AtividadeRepository;
import com.skateholders.skateholders.repositories.SeshRepository;
import com.skateholders.skateholders.repositories.TrickRepository;
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

    /**
     * MÉTODO PARA O BOTÃO PRINCIPAL (REGISTRO AO VIVO).
     * Cria ou utiliza a sessão do dia atual e registra a atividade com o horário atual.
     * NÃO MODIFICAR.
     */
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
        return new AtividadeOutputDTO(savedAtividade);
    }

    /**
     * NOVO MÉTODO PARA O BOTÃO DE EDIÇÃO DE SESSÃO.
     * Adiciona uma atividade a uma sessão específica (passada pelo ID) com horário fixo de 23:59.
     */
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
        return new AtividadeOutputDTO(savedAtividade);
    }

    // --- MÉTODOS EXISTENTES (Mantidos para não quebrar outras funcionalidades) ---

    public List<AtividadeOutputDTO> listarTodos() {
        return atividadeRepository.findAll().stream()
                .map(AtividadeOutputDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<AtividadeOutputDTO> buscarPorId(Long id) {
        return atividadeRepository.findById(id).map(AtividadeOutputDTO::new);
    }

    @Transactional
    public AtividadeOutputDTO atualizar(Long id, AtividadeInputDTO atividadeInputDTO) {
        Atividade existente = atividadeRepository.findById(id).orElseThrow(() -> new RuntimeException("Atividade não encontrada!"));
        Trick novaTrick = trickRepository.findById(atividadeInputDTO.getTrickId()).orElseThrow(() -> new RuntimeException("Trick não encontrada!"));

        existente.setTrick(novaTrick);
        existente.setObstaculo(atividadeInputDTO.getObstaculo());

        Atividade updatedAtividade = atividadeRepository.save(existente);
        return new AtividadeOutputDTO(updatedAtividade);
    }

    public void deletar(Long id) {
        // Adicionada verificação de existência para boa prática
        if (!atividadeRepository.existsById(id)) {
            throw new RuntimeException("Atividade com ID " + id + " não encontrada para exclusão.");
        }
        atividadeRepository.deleteById(id);
    }
}