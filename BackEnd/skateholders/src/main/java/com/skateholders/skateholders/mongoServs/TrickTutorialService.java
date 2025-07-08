package com.skateholders.skateholders.mongoServs;

import com.skateholders.skateholders.mongoDocs.TrickTutorial;
import com.skateholders.skateholders.mongoReps.TrickTutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrickTutorialService {

    @Autowired
    private TrickTutorialRepository repository;

    public List<TrickTutorial> listarTodos() {
        return repository.findAll();
    }

    public List<TrickTutorial> listarPorTrickId(Long trickId) {
        return repository.findByTrickId(trickId);
    }

    public TrickTutorial salvar(TrickTutorial tutorial) {
        return repository.save(tutorial);
    }

    public Optional<TrickTutorial> buscarPorId(String id) {
        return repository.findById(id);
    }

    public void deletar(String id) {
        repository.deleteById(id);
    }
}
