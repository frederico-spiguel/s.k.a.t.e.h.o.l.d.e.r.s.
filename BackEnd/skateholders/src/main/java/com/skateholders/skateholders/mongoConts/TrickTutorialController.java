package com.skateholders.skateholders.mongoConts;

import com.skateholders.skateholders.mongoDocs.TrickTutorial;
import com.skateholders.skateholders.mongoServs.TrickTutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mongo/trick-tutorials")
public class TrickTutorialController {

    @Autowired
    private TrickTutorialService service;

    @GetMapping
    public List<TrickTutorial> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/trick/{trickId}")
    public List<TrickTutorial> listarPorTrick(@PathVariable Long trickId) {
        return service.listarPorTrickId(trickId);
    }

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
