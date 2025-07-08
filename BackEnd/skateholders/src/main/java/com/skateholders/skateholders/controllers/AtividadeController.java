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
// Movi a definição da rota para dentro de cada método para maior clareza,
// já que agora temos rotas com estruturas diferentes (/atividades e /seshs/...).
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    /**
     * ENDPOINT PARA O BOTÃO PRINCIPAL (REGISTRO AO VIVO)
     * Rota: POST /atividades
     */
    @PostMapping("/atividades")
    public ResponseEntity<AtividadeOutputDTO> registrarAtividadeAoVivo(@RequestBody AtividadeInputDTO atividadeInputDTO) {
        AtividadeOutputDTO atividadeCriada = atividadeService.registrar(atividadeInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(atividadeCriada);
    }

    /**
     * NOVO ENDPOINT PARA O BOTÃO DE EDIÇÃO DE SESSÃO
     * Rota: POST /seshs/{seshId}/atividades
     */
    @PostMapping("/seshs/{seshId}/atividades")
    public ResponseEntity<AtividadeOutputDTO> adicionarAtividadeSessao(
            @PathVariable Long seshId,
            @RequestBody AtividadeInputDTO atividadeInputDTO) {
        AtividadeOutputDTO atividadeCriada = atividadeService.adicionarAtividadeEmSessaoExistente(seshId, atividadeInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(atividadeCriada);
    }

    // --- MÉTODOS EXISTENTES (Mantidos para não quebrar outras funcionalidades) ---

    @GetMapping("/atividades")
    public List<AtividadeOutputDTO> listarTodos() {
        return atividadeService.listarTodos();
    }

    @GetMapping("/atividades/{id}")
    public ResponseEntity<AtividadeOutputDTO> buscarPorId(@PathVariable Long id) {
        return atividadeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/atividades/{id}")
    public ResponseEntity<AtividadeOutputDTO> atualizar(@PathVariable Long id, @RequestBody AtividadeInputDTO atividadeInputDTO) {
        try {
            return ResponseEntity.ok(atividadeService.atualizar(id, atividadeInputDTO));
        } catch (RuntimeException e) {
            // É uma boa prática logar o erro aqui
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/atividades/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            atividadeService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // É uma boa prática logar o erro aqui
            return ResponseEntity.notFound().build();
        }
    }
}
//teste