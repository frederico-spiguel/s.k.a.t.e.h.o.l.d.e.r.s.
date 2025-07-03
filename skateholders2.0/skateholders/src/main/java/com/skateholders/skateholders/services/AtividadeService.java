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

        // Usando o novo construtor do DTO
        return new AtividadeOutputDTO(savedAtividade);
    }

    public List<AtividadeOutputDTO> listarTodos() {
        return atividadeRepository.findAll().stream()
                .map(AtividadeOutputDTO::new) // Forma resumida de .map(atividade -> new AtividadeOutputDTO(atividade))
                .collect(Collectors.toList());
    }

    public Optional<AtividadeOutputDTO> buscarPorId(Long id) {
        return atividadeRepository.findById(id).map(AtividadeOutputDTO::new);
    }

    @Transactional
    public AtividadeOutputDTO atualizar(Long id, AtividadeInputDTO atividadeInputDTO) {
        Atividade existente = atividadeRepository.findById(id).orElseThrow();

        // Adapta a lógica de atualização
        Trick novaTrick = trickRepository.findById(atividadeInputDTO.getTrickId()).orElseThrow();
        existente.setTrick(novaTrick);
        existente.setObstaculo(atividadeInputDTO.getObstaculo());
        // Nota: A lógica de mudar a Sesh ou horário em uma atualização pode ser mais complexa
        // e foi mantida simples aqui.

        Atividade updatedAtividade = atividadeRepository.save(existente);

        return new AtividadeOutputDTO(updatedAtividade);
    }

    public void deletar(Long id) {
        atividadeRepository.deleteById(id);
    }
}