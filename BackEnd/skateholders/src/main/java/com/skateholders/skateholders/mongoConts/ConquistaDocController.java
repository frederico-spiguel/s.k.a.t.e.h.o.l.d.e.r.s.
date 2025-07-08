package com.skateholders.skateholders.mongoConts;

import com.skateholders.skateholders.mongoDocs.ConquistaDoc;
import com.skateholders.skateholders.mongoServs.ConquistaDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mongo/conquistas")
public class ConquistaDocController {

    @Autowired
    private ConquistaDocService service;

    @GetMapping
    public List<ConquistaDoc> listarTodas() {
        return service.listarTodas();
    }

    @PostMapping
    public ConquistaDoc criar(@RequestBody ConquistaDoc doc) {
        return service.salvar(doc);
    }

    @GetMapping("/{id}")
    public ConquistaDoc buscarPorId(@PathVariable String id) {
        return service.buscarPorId(id).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable String id) {
        service.deletar(id);
    }
}
