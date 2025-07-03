package com.skateholders.skateholders.controllers;

import com.skateholders.skateholders.DTOs.TrickDTO;
import com.skateholders.skateholders.DTOs.TrickInputDTO;
import com.skateholders.skateholders.services.TrickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tricks")
public class TrickController {

    @Autowired
    private TrickService trickService;

    @PostMapping
    public TrickDTO criar(@RequestBody TrickInputDTO input) {
        return trickService.criar(input);
    }

    @GetMapping
    public List<TrickDTO> listarTodos() {
        return trickService.listarTodos();
    }

    @GetMapping("/{id}")
    public TrickDTO buscarPorId(@PathVariable Long id) {
        return trickService.buscarPorId(id).orElse(null);
    }

    @PutMapping("/{id}")
    public TrickDTO atualizar(@PathVariable Long id, @RequestBody TrickInputDTO input) {
        return trickService.atualizar(id, input);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        trickService.deletar(id);
    }
}
