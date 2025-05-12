package com.skateholders.skateholders.controllers;

import com.skateholders.skateholders.DTOs.AtividadeInputDTO;
import com.skateholders.skateholders.DTOs.AtividadeOutputDTO;
import com.skateholders.skateholders.services.AtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    @PostMapping
    public AtividadeOutputDTO criar(@RequestBody AtividadeInputDTO atividadeInputDTO) {
        return atividadeService.criar(atividadeInputDTO);
    }

    @GetMapping
    public List<AtividadeOutputDTO> listarTodos() {
        return atividadeService.listarTodos();
    }

    @GetMapping("/{id}")
    public AtividadeOutputDTO buscarPorId(@PathVariable Long id) {
        return atividadeService.buscarPorId(id).orElse(null);
    }

    @PutMapping("/{id}")
    public AtividadeOutputDTO atualizar(@PathVariable Long id, @RequestBody AtividadeInputDTO atividadeInputDTO) {
        return atividadeService.atualizar(id, atividadeInputDTO);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        atividadeService.deletar(id);
    }
}
