package com.skateholders.skateholders.controllers;

import com.skateholders.skateholders.DTOs.SeshInputDTO;
import com.skateholders.skateholders.DTOs.SeshOutputDTO;
import com.skateholders.skateholders.services.SeshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/seshs")
public class SeshController {

    @Autowired
    private SeshService seshService;

    @PostMapping
    public SeshOutputDTO criar(@RequestBody SeshInputDTO dto) {
        return seshService.criar(dto);
    }

    @GetMapping
    public List<SeshOutputDTO> listarTodos() {
        return seshService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<SeshOutputDTO> buscarPorId(@PathVariable Long id) {
        return seshService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        seshService.deletar(id);
    }
}
