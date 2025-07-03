package com.skateholders.skateholders.controllers;

import com.skateholders.skateholders.DTOs.ConquistaInputDTO;
import com.skateholders.skateholders.DTOs.ConquistaOutputDTO;
import com.skateholders.skateholders.services.ConquistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conquistas")
public class ConquistaController {

    @Autowired
    private ConquistaService conquistaService;

    @PostMapping
    public ConquistaOutputDTO criar(@RequestBody ConquistaInputDTO dto) {
        return conquistaService.criar(dto);
    }

    @GetMapping
    public List<ConquistaOutputDTO> listarTodas() {
        return conquistaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ConquistaOutputDTO buscarPorId(@PathVariable Long id) {
        return conquistaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ConquistaOutputDTO atualizar(@PathVariable Long id, @RequestBody ConquistaInputDTO dto) {
        return conquistaService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        conquistaService.deletar(id);
    }
}
