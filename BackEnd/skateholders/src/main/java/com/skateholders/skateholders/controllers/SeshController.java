package com.skateholders.skateholders.controllers;

import com.skateholders.skateholders.DTOs.SeshInputDTO;
import com.skateholders.skateholders.DTOs.SeshOutputDTO;
import com.skateholders.skateholders.services.SeshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/seshs") // A URL base para todos os endpoints neste controller será /seshs
public class SeshController {

    @Autowired
    private SeshService seshService;

    // --- MÉTODOS CRUD ORIGINAIS (MELHORADOS COM ResponseEntity) ---

    @PostMapping
    public ResponseEntity<SeshOutputDTO> criar(@RequestBody SeshInputDTO dto) {
        SeshOutputDTO seshCriada = seshService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(seshCriada);
    }

    @GetMapping
    public ResponseEntity<List<SeshOutputDTO>> listarTodos() {
        return ResponseEntity.ok(seshService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeshOutputDTO> buscarPorId(@PathVariable Long id) {
        return seshService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        seshService.deletar(id);
        return ResponseEntity.noContent().build();
    }


    // --- NOVOS ENDPOINTS PARA A FUNCIONALIDADE DE SESSÕES (FASE 1) ---

    /**
     * Endpoint para o calendário saber quais dias destacar.
     * Retorna uma lista de datas (ex: ["2025-07-03", "2025-07-01"])
     */
    @GetMapping("/datas")
    public ResponseEntity<List<LocalDate>> buscarDatasSessoes() {
        List<LocalDate> datas = seshService.buscarDatasSessoesDoUsuarioLogado();
        return ResponseEntity.ok(datas);
    }

    /**
     * Endpoint para buscar os detalhes de uma sessão em uma data específica.
     * Exemplo de chamada pelo frontend: GET /seshs/por-data?data=2025-07-03
     */
    @GetMapping("/por-data")
    public ResponseEntity<SeshOutputDTO> buscarSessaoPorData(
            @RequestParam("data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
    ) {
        return seshService.buscarSessaoPorDataDoUsuarioLogado(data)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}