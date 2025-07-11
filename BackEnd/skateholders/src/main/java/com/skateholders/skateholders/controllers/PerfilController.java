// PerfilController.java (COMPLETO)
package com.skateholders.skateholders.controllers;

import com.skateholders.skateholders.DTOs.PerfilDTO;
import com.skateholders.skateholders.services.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}