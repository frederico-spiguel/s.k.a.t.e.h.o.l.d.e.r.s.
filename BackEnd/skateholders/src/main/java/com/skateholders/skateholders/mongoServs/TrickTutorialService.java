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

    // Retorna todos os tutoriais para a nossa página no front-end
    public List<TrickTutorial> listarTodos() {
        return repository.findAll();
    }

    // Os métodos abaixo são ótimos para você gerenciar os tutoriais (via Postman, por exemplo)
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