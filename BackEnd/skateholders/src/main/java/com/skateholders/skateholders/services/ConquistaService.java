package com.skateholders.skateholders.services;

import com.skateholders.skateholders.DTOs.ConquistaInputDTO;
import com.skateholders.skateholders.DTOs.ConquistaOutputDTO;
import com.skateholders.skateholders.models.Conquista;
import com.skateholders.skateholders.repositories.ConquistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConquistaService {

    @Autowired
    private ConquistaRepository conquistaRepository;

    public ConquistaOutputDTO criar(ConquistaInputDTO dto) {
        Conquista nova = new Conquista();
        nova.setNome(dto.getNome());
        nova.setDescricao(dto.getDescricao());
        nova.setTipo(dto.getTipo());
        nova.setParametro(dto.getParametro());

        Conquista salva = conquistaRepository.save(nova);

        return new ConquistaOutputDTO(salva.getId(), salva.getNome(), salva.getDescricao(), salva.getTipo(), salva.getParametro());
    }

    public List<ConquistaOutputDTO> listarTodas() {
        return conquistaRepository.findAll().stream().map(c ->
                new ConquistaOutputDTO(c.getId(), c.getNome(), c.getDescricao(), c.getTipo(), c.getParametro())
        ).collect(Collectors.toList());
    }

    public ConquistaOutputDTO buscarPorId(Long id) {
        Conquista c = conquistaRepository.findById(id).orElseThrow();
        return new ConquistaOutputDTO(c.getId(), c.getNome(), c.getDescricao(), c.getTipo(), c.getParametro());
    }

    public ConquistaOutputDTO atualizar(Long id, ConquistaInputDTO dto) {
        Conquista c = conquistaRepository.findById(id).orElseThrow();
        c.setNome(dto.getNome());
        c.setDescricao(dto.getDescricao());
        c.setTipo(dto.getTipo());
        c.setParametro(dto.getParametro());

        Conquista atualizada = conquistaRepository.save(c);
        return new ConquistaOutputDTO(atualizada.getId(), atualizada.getNome(), atualizada.getDescricao(), atualizada.getTipo(), atualizada.getParametro());
    }

    public void deletar(Long id) {
        conquistaRepository.deleteById(id);
    }
}
