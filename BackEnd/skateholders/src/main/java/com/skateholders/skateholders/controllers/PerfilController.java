package com.skateholders.skateholders.controllers;

import com.skateholders.skateholders.DTOs.PerfilDTO;
import com.skateholders.skateholders.DTOs.ProficienciaRequestDTO; // <-- NOVO IMPORT
import com.skateholders.skateholders.services.PerfilService;
import jakarta.validation.Valid; // <-- NOVO IMPORT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map; // <-- NOVO IMPORT

@RestController
@RequestMapping("/api/perfil")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    /**
     * Endpoint para o front-end buscar todos os dados agregados
     * do perfil do usuário atualmente logado.
     */
    @GetMapping
    public ResponseEntity<PerfilDTO> getPerfil() {
        PerfilDTO perfil = perfilService.getPerfilDoUsuarioLogado();
        return ResponseEntity.ok(perfil);
    }

    /**
     * NOVO ENDPOINT: Recebe a solicitação de proficiência do front-end.
     */
    @PostMapping("/proficiencia")
    public ResponseEntity<?> solicitarProficiencia(@RequestBody @Valid ProficienciaRequestDTO dto) {
        try {
            boolean sucesso = perfilService.solicitarProficiencia(dto);
            if (sucesso) {
                return ResponseEntity.ok(Map.of("message", "Proficiência alcançada! Você está NA BASE!"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("message", "Não foi dessa vez! Continue treinando para atingir os requisitos."));
            }
        } catch (RuntimeException e) {
            // Captura erros como "Trick não encontrada" ou "Relação TrickUsuario não existe"
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}