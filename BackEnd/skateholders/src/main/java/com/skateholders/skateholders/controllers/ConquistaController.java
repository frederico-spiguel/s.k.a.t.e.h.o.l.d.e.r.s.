// ConquistaController.java (COMPLETO)

package com.skateholders.skateholders.controllers;

import com.skateholders.skateholders.DTOs.ConquistaStatusDTO;
import com.skateholders.skateholders.services.ConquistaService; // Importe o nosso serviço principal
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/conquistas") // A rota que o seu front-end vai chamar
public class ConquistaController {

    @Autowired
    private ConquistaService conquistaService;

    /**
     * Endpoint para o front-end buscar a lista de todas as conquistas
     * e saber quais o usuário logado já desbloqueou.
     * O front-end usará essa resposta para renderizar as "cartas" viradas para cima ou para baixo.
     */
    @GetMapping
    public ResponseEntity<List<ConquistaStatusDTO>> getStatusTodasConquistas() {
        List<ConquistaStatusDTO> status = conquistaService.getStatusConquistasParaUsuarioLogado();
        return ResponseEntity.ok(status);
    }

    /**
     * Endpoint para o usuário tentar reivindicar uma conquista.
     * Será chamado pelo botão "Reivindicar Conquista".
     */
    @PostMapping("/{id}/reivindicar")
    public ResponseEntity<?> reivindicarConquista(@PathVariable String id) {
        try {
            boolean sucesso = conquistaService.reivindicarConquista(id);
            if (sucesso) {
                // Retorna uma mensagem de sucesso e o DTO da conquista desbloqueada
                return ResponseEntity.ok().body(Map.of("message", "Parabéns! Conquista desbloqueada!"));
            } else {
                // Retorna uma mensagem de erro amigável se os requisitos não foram atendidos
                return ResponseEntity.badRequest().body(Map.of("message", "Você ainda não atendeu aos requisitos para esta conquista."));
            }
        } catch (IllegalStateException e) {
            // Captura o erro caso a conquista já tenha sido desbloqueada e retorna um status de conflito
            return ResponseEntity.status(409).body(Map.of("message", e.getMessage())); // 409 Conflict
        } catch (RuntimeException e) {
            // Captura outros erros, como "conquista não encontrada"
            return ResponseEntity.notFound().build();
        }
    }
}