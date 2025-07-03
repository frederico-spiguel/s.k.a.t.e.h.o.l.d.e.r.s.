package com.skateholders.skateholders.services;

import com.skateholders.skateholders.models.Base;
import com.skateholders.skateholders.repositories.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BaseService {

    @Autowired
    private BaseRepository baseRepository;

    public List<Base> listarTodos() {
        return baseRepository.findAll();
    }

    public Optional<Base> buscarPorId(Long id) {
        return baseRepository.findById(id);
    }

    public Base criar(Base base) {
        return baseRepository.save(base);
    }

    public Base atualizar(Long id, Base novaBase) {
        return baseRepository.findById(id).map(b -> {
            b.setDescricao(novaBase.getDescricao());
            return baseRepository.save(b);
        }).orElseThrow(() -> new RuntimeException("Base não encontrada com ID: " + id));
    }

    public void deletar(Long id) {
        baseRepository.deleteById(id);
    }
}
