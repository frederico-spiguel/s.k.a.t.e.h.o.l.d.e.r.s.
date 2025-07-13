package com.skateholders.skateholders.mongoConts;

import com.skateholders.skateholders.mongoDocs.TrickTutorial;
import com.skateholders.skateholders.mongoServs.TrickTutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutoriais") // Rota mais limpa para o front-end consumir
public class TrickTutorialController {

    @Autowired
    private TrickTutorialService service;

    /**
     * Este será o endpoint principal que seu front-end vai chamar
     * para obter a lista de todos os tutoriais a serem exibidos.
     */
    @GetMapping
    public List<TrickTutorial> listarTodos() {
        return service.listarTodos();
    }

    // Os endpoints abaixo podem ser mantidos para fins administrativos

    @PostMapping
    public TrickTutorial criar(@RequestBody TrickTutorial tutorial) {
        return service.salvar(tutorial);
    }

    @GetMapping("/{id}")
    public TrickTutorial buscarPorId(@PathVariable String id) {
        return service.buscarPorId(id).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String id) {
        service.deletar(id);
    }
}