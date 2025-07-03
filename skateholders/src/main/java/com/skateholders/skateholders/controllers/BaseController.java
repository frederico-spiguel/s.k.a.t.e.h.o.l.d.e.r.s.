package com.skateholders.skateholders.controllers;

import com.skateholders.skateholders.models.Base;
import com.skateholders.skateholders.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bases")
public class BaseController {

    @Autowired
    private BaseService baseService;

    @GetMapping
    public List<Base> listarTodos() {
        return baseService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Base> buscarPorId(@PathVariable Long id) {
        return baseService.buscarPorId(id);
    }

    @PostMapping
    public Base criar(@RequestBody Base base) {
        return baseService.criar(base);
    }

    @PutMapping("/{id}")
    public Base atualizar(@PathVariable Long id, @RequestBody Base base) {
        return baseService.atualizar(id, base);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        baseService.deletar(id);
    }
}
