package com.skateholders.skateholders.services;

import com.skateholders.skateholders.DTOs.TrickDTO;
import com.skateholders.skateholders.DTOs.TrickInputDTO;
import com.skateholders.skateholders.models.Base;
import com.skateholders.skateholders.models.Trick;
import com.skateholders.skateholders.repositories.BaseRepository;
import com.skateholders.skateholders.repositories.TrickRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrickService {

    @Autowired
    private TrickRepository trickRepository;

    @Autowired
    private BaseRepository baseRepository;

    public TrickDTO criar(TrickInputDTO dto) {
        Base base = baseRepository.findById(dto.getBaseId())
                .orElseThrow(() -> new RuntimeException("Base não encontrada"));

        Trick trick = new Trick();
        trick.setNome(dto.getNome());
        trick.setDificuldade(dto.getDificuldade());
        trick.setBase(base);

        Trick saved = trickRepository.save(trick);

        return new TrickDTO(saved.getId(), saved.getNome(), saved.getDificuldade(), base.getDescricao());
    }

    public List<TrickDTO> listarTodos() {
        return trickRepository.findAll().stream()
                .map(trick -> new TrickDTO(
                        trick.getId(),
                        trick.getNome(),
                        trick.getDificuldade(),
                        trick.getBase().getDescricao()
                ))
                .collect(Collectors.toList());
    }

    public Optional<TrickDTO> buscarPorId(Long id) {
        return trickRepository.findById(id)
                .map(trick -> new TrickDTO(
                        trick.getId(),
                        trick.getNome(),
                        trick.getDificuldade(),
                        trick.getBase().getDescricao()
                ));
    }

    public TrickDTO atualizar(Long id, TrickInputDTO dto) {
        Trick existente = trickRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trick não encontrada"));

        Base base = baseRepository.findById(dto.getBaseId())
                .orElseThrow(() -> new RuntimeException("Base não encontrada"));

        existente.setNome(dto.getNome());
        existente.setDificuldade(dto.getDificuldade());
        existente.setBase(base);

        Trick updated = trickRepository.save(existente);

        return new TrickDTO(updated.getId(), updated.getNome(), updated.getDificuldade(), base.getDescricao());
    }


    public void deletar(Long id) {
        trickRepository.deleteById(id);
    }
}
