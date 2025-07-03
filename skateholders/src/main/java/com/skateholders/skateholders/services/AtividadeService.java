package com.skateholders.skateholders.services;

import com.skateholders.skateholders.DTOs.AtividadeInputDTO;
import com.skateholders.skateholders.DTOs.AtividadeOutputDTO;
import com.skateholders.skateholders.models.Atividade;
import com.skateholders.skateholders.repositories.AtividadeRepository;
import com.skateholders.skateholders.repositories.TrickRepository;
import com.skateholders.skateholders.repositories.SeshRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public AtividadeOutputDTO criar(AtividadeInputDTO atividadeInputDTO) {
        // Converte o input DTO para a entidade Atividade
        Atividade atividade = new Atividade();
        atividade.setTrick(trickRepository.findById(atividadeInputDTO.getTrickId()).orElseThrow());
        atividade.setSesh(seshRepository.findById(atividadeInputDTO.getSeshId()).orElseThrow());
        atividade.setHorario(atividadeInputDTO.getHorario());
        atividade.setObstaculo(atividadeInputDTO.getObstaculo() != null ? atividadeInputDTO.getObstaculo() : "Flat");

        // Salva a Atividade no banco
        Atividade savedAtividade = atividadeRepository.save(atividade);

        // Retorna o DTO de saída com os dados necessários
        return new AtividadeOutputDTO(
                savedAtividade.getId(),
                savedAtividade.getTrick().getNome(),
                savedAtividade.getTrick().getBase().getDescricao(),
                savedAtividade.getSesh().getData(),
                savedAtividade.getSesh().getUsuario().getLogin(),
                savedAtividade.getHorario(),
                savedAtividade.getObstaculo()
        );
    }

    public List<AtividadeOutputDTO> listarTodos() {
        return atividadeRepository.findAll().stream()
                .map(atividade -> new AtividadeOutputDTO(
                        atividade.getId(),
                        atividade.getTrick().getNome(),
                        atividade.getTrick().getBase().getDescricao(),
                        atividade.getSesh().getData(),
                        atividade.getSesh().getUsuario().getLogin(),
                        atividade.getHorario(),
                        atividade.getObstaculo()
                ))
                .collect(Collectors.toList());
    }

    public Optional<AtividadeOutputDTO> buscarPorId(Long id) {
        Optional<Atividade> atividadeOptional = atividadeRepository.findById(id);
        if (atividadeOptional.isPresent()) {
            Atividade atividade = atividadeOptional.get();
            return Optional.of(new AtividadeOutputDTO(
                    atividade.getId(),
                    atividade.getTrick().getNome(),
                    atividade.getTrick().getBase().getDescricao(),
                    atividade.getSesh().getData(),
                    atividade.getSesh().getUsuario().getLogin(),
                    atividade.getHorario(),
                    atividade.getObstaculo()
            ));
        }
        return Optional.empty();
    }

    public AtividadeOutputDTO atualizar(Long id, AtividadeInputDTO atividadeInputDTO) {
        Atividade existente = atividadeRepository.findById(id).orElseThrow();
        existente.setTrick(trickRepository.findById(atividadeInputDTO.getTrickId()).orElseThrow());
        existente.setSesh(seshRepository.findById(atividadeInputDTO.getSeshId()).orElseThrow());
        existente.setHorario(atividadeInputDTO.getHorario());
        existente.setObstaculo(atividadeInputDTO.getObstaculo() != null ? atividadeInputDTO.getObstaculo() : "Flat");

        Atividade updatedAtividade = atividadeRepository.save(existente);

        return new AtividadeOutputDTO(
                updatedAtividade.getId(),
                updatedAtividade.getTrick().getNome(),
                updatedAtividade.getTrick().getBase().getDescricao(),
                updatedAtividade.getSesh().getData(),
                updatedAtividade.getSesh().getUsuario().getLogin(),
                updatedAtividade.getHorario(),
                updatedAtividade.getObstaculo()
        );
    }

    public void deletar(Long id) {
        atividadeRepository.deleteById(id);
    }
}
