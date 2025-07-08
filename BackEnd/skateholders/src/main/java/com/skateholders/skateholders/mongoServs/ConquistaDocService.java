package com.skateholders.skateholders.mongoServs;

import com.skateholders.skateholders.mongoDocs.ConquistaDoc;
import com.skateholders.skateholders.mongoReps.ConquistaDocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConquistaDocService {

    @Autowired
    private ConquistaDocRepository repo;

    public List<ConquistaDoc> listarTodas() {
        return repo.findAll();
    }

    public ConquistaDoc salvar(ConquistaDoc doc) {
        return repo.save(doc);
    }

    public Optional<ConquistaDoc> buscarPorId(String id) {
        return repo.findById(id);
    }

    public void deletar(String id) {
        repo.deleteById(id);
    }
}
