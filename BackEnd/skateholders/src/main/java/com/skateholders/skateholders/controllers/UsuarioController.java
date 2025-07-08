package com.skateholders.skateholders.controllers;

import com.skateholders.skateholders.DTOs.TriagemInputDTO;
import com.skateholders.skateholders.models.Usuario;
import com.skateholders.skateholders.services.TriagemService;
import com.skateholders.skateholders.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TriagemService triagemService;

    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario){
        Usuario criado = usuarioService.criar(usuario);
        return ResponseEntity.ok(criado);
    }

    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario atualizado = usuarioService.atualizar(id, usuario);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    // --- NOSSO NOVO MeTODO DE TRIAGEM ---
    @PostMapping("/triagem")
    public ResponseEntity<Void> salvarTriagem(
            @RequestBody List<TriagemInputDTO> respostas,
            @AuthenticationPrincipal Usuario usuario
    ) {
        // A lógica complexa agora vive no Service. O controller só delega.
        triagemService.salvarRespostas(usuario, respostas);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}