package com.skateholders.skateholders.services;

import com.skateholders.skateholders.DTOs.UsuarioConquistaInputDTO;
import com.skateholders.skateholders.DTOs.UsuarioConquistaOutputDTO;
import com.skateholders.skateholders.models.*;
import com.skateholders.skateholders.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioConquistaService {

    @Autowired
    private UsuarioConquistaRepository usuarioConquistaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConquistaRepository conquistaRepository;

    public UsuarioConquistaOutputDTO criar(UsuarioConquistaInputDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Conquista conquista = conquistaRepository.findById(dto.getConquistaId()).orElseThrow(() -> new RuntimeException("Conquista não encontrada"));

        UsuarioConquista entidade = new UsuarioConquista(usuario, conquista, dto.getDataConquista());
        usuarioConquistaRepository.save(entidade);

        return new UsuarioConquistaOutputDTO(
                usuario.getId(),
                usuario.getLogin(),
                conquista.getId(),
                conquista.getNome(),
                dto.getDataConquista()
        );
    }

    public List<UsuarioConquistaOutputDTO> listarTodos() {
        return usuarioConquistaRepository.findAll().stream().map(uc ->
                new UsuarioConquistaOutputDTO(
                        uc.getUsuario().getId(),
                        uc.getUsuario().getLogin(),
                        uc.getConquista().getId(),
                        uc.getConquista().getNome(),
                        uc.getDataConquista()
                )
        ).collect(Collectors.toList());
    }

    public UsuarioConquistaOutputDTO buscarPorId(UsuarioConquistaId id) {
        UsuarioConquista uc = usuarioConquistaRepository.findById(id).orElseThrow(() -> new RuntimeException("UsuarioConquista não encontrado"));
        return new UsuarioConquistaOutputDTO(
                uc.getUsuario().getId(),
                uc.getUsuario().getLogin(),
                uc.getConquista().getId(),
                uc.getConquista().getNome(),
                uc.getDataConquista()
        );
    }

    public UsuarioConquistaOutputDTO atualizar(UsuarioConquistaId id, UsuarioConquistaInputDTO dto) {
        UsuarioConquista entidade = usuarioConquistaRepository.findById(id).orElseThrow(() -> new RuntimeException("UsuarioConquista não encontrado"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Conquista conquista = conquistaRepository.findById(dto.getConquistaId()).orElseThrow(() -> new RuntimeException("Conquista não encontrada"));

        entidade.setUsuario(usuario);
        entidade.setConquista(conquista);
        entidade.setDataConquista(dto.getDataConquista());

        UsuarioConquista atualizada = usuarioConquistaRepository.save(entidade);

        return new UsuarioConquistaOutputDTO(
                atualizada.getUsuario().getId(),
                atualizada.getUsuario().getLogin(),
                atualizada.getConquista().getId(),
                atualizada.getConquista().getNome(),
                atualizada.getDataConquista()
        );
    }

    public void deletar(UsuarioConquistaId id) {
        UsuarioConquista entidade = usuarioConquistaRepository.findById(id).orElseThrow(() -> new RuntimeException("UsuarioConquista não encontrado"));
        usuarioConquistaRepository.delete(entidade);
    }
}
