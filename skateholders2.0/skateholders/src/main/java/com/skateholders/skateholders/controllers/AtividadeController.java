package com.skateholders.skateholders.controllers;

import com.skateholders.skateholders.DTOs.AtividadeInputDTO;
import com.skateholders.skateholders.DTOs.AtividadeOutputDTO;
import com.skateholders.skateholders.services.AtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    @PostMapping
    public ResponseEntity<AtividadeOutputDTO> registrar(@RequestBody AtividadeInputDTO atividadeInputDTO) {
        // O nome do método no service também vai mudar para "registrar"
        AtividadeOutputDTO atividadeCriada = atividadeService.registrar(atividadeInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(atividadeCriada);
    }

    @GetMapping
    public List<AtividadeOutputDTO> listarTodos() {
        return atividadeService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtividadeOutputDTO> buscarPorId(@PathVariable Long id) {
        // Envolver em ResponseEntity é uma prática melhor
        return atividadeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtividadeOutputDTO> atualizar(@PathVariable Long id, @RequestBody AtividadeInputDTO atividadeInputDTO) {
        // Envolver em ResponseEntity é uma prática melhor
        try {
            return ResponseEntity.ok(atividadeService.atualizar(id, atividadeInputDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        atividadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}