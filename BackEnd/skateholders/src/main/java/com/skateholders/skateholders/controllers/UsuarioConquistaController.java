package com.skateholders.skateholders.controllers;

import com.skateholders.skateholders.DTOs.UsuarioConquistaInputDTO;
import com.skateholders.skateholders.DTOs.UsuarioConquistaOutputDTO;
import com.skateholders.skateholders.models.UsuarioConquistaId;
import com.skateholders.skateholders.services.UsuarioConquistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario-conquistas")
public class UsuarioConquistaController {

    @Autowired
    private UsuarioConquistaService usuarioConquistaService;

    @PostMapping
    public UsuarioConquistaOutputDTO criar(@RequestBody UsuarioConquistaInputDTO dto) {
        return usuarioConquistaService.criar(dto);
    }

    @GetMapping
    public List<UsuarioConquistaOutputDTO> listarTodos() {
        return usuarioConquistaService.listarTodos();
    }

    @GetMapping("/{usuarioId}/{conquistaId}")
    public UsuarioConquistaOutputDTO buscarPorId(@PathVariable Long usuarioId, @PathVariable Long conquistaId) {
        return usuarioConquistaService.buscarPorId(new UsuarioConquistaId(usuarioId, conquistaId));
    }

    @PutMapping("/{usuarioId}/{conquistaId}")
    public UsuarioConquistaOutputDTO atualizar(@PathVariable Long usuarioId, @PathVariable Long conquistaId, @RequestBody UsuarioConquistaInputDTO dto) {
        return usuarioConquistaService.atualizar(new UsuarioConquistaId(usuarioId, conquistaId), dto);
    }

    @DeleteMapping("/{usuarioId}/{conquistaId}")
    public void deletar(@PathVariable Long usuarioId, @PathVariable Long conquistaId) {
        usuarioConquistaService.deletar(new UsuarioConquistaId(usuarioId, conquistaId));
    }
}
