package com.skateholders.skateholders.services;

import com.skateholders.skateholders.DTOs.TrickDTO;
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

    public TrickDTO criar(Trick trick) {
        // Garante que a base associada existe
        Base base = baseRepository.findById(trick.getBase().getId())
                .orElseThrow(() -> new RuntimeException("Base não encontrada"));

        trick.setBase(base);
        Trick savedTrick = trickRepository.save(trick);

        return new TrickDTO(
                savedTrick.getId(),
                savedTrick.getNome(),
                savedTrick.getDificuldade(),
                savedTrick.getBase().getDescricao()
        );
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

    public TrickDTO atualizar(Long id, Trick trickAtualizado) {
        Trick existente = trickRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trick não encontrado"));

        Base base = baseRepository.findById(trickAtualizado.getBase().getId())
                .orElseThrow(() -> new RuntimeException("Base não encontrada"));

        existente.setNome(trickAtualizado.getNome());
        existente.setDificuldade(trickAtualizado.getDificuldade());
        existente.setBase(base);

        Trick updated = trickRepository.save(existente);

        return new TrickDTO(
                updated.getId(),
                updated.getNome(),
                updated.getDificuldade(),
                updated.getBase().getDescricao()
        );
    }

    public void deletar(Long id) {
        trickRepository.deleteById(id);
    }
}
