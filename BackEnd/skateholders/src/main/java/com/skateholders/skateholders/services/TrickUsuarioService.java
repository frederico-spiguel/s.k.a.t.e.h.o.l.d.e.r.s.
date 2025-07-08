package com.skateholders.skateholders.services;

import com.skateholders.skateholders.DTOs.TrickUsuarioInputDTO;
import com.skateholders.skateholders.DTOs.TrickUsuarioOutputDTO;
import com.skateholders.skateholders.models.Trick;
import com.skateholders.skateholders.models.TrickUsuario;
import com.skateholders.skateholders.models.TrickUsuarioId;
import com.skateholders.skateholders.models.Usuario;
import com.skateholders.skateholders.repositories.TrickRepository;
import com.skateholders.skateholders.repositories.TrickUsuarioRepository;
import com.skateholders.skateholders.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrickUsuarioService {

    @Autowired
    private TrickUsuarioRepository trickUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TrickRepository trickRepository;

    public TrickUsuarioOutputDTO criar(TrickUsuarioInputDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElseThrow();
        Trick trick = trickRepository.findById(dto.getTrickId()).orElseThrow();

        TrickUsuario entidade = new TrickUsuario(
                usuario,
                trick,
                dto.getNivel()
        );

        trickUsuarioRepository.save(entidade);

        return new TrickUsuarioOutputDTO(
                usuario.getId(),
                usuario.getLogin(),
                trick.getId(),
                trick.getNome(),
                trick.getBase().getDescricao(),
                entidade.getNivel(),
                entidade.getAcertos(),
                entidade.isProficiencia()
        );
    }

    public List<TrickUsuarioOutputDTO> listarTodos() {
        return trickUsuarioRepository.findAll().stream().map(entidade ->
                new TrickUsuarioOutputDTO(
                        entidade.getUsuario().getId(),
                        entidade.getUsuario().getLogin(),
                        entidade.getTrick().getId(),
                        entidade.getTrick().getNome(),
                        entidade.getTrick().getBase().getDescricao(),
                        entidade.getNivel(),
                        entidade.getAcertos(),
                        entidade.isProficiencia()
                )
        ).collect(Collectors.toList());
    }

    public TrickUsuarioOutputDTO buscarPorId(TrickUsuarioId id) {
        TrickUsuario entidade = trickUsuarioRepository.findById(id).orElseThrow();
        return new TrickUsuarioOutputDTO(
                entidade.getUsuario().getId(),
                entidade.getUsuario().getLogin(),
                entidade.getTrick().getId(),
                entidade.getTrick().getNome(),
                entidade.getTrick().getBase().getDescricao(),
                entidade.getNivel(),
                entidade.getAcertos(),
                entidade.isProficiencia()
        );
    }

    public TrickUsuarioOutputDTO atualizar(TrickUsuarioId id, TrickUsuarioInputDTO dto) {
        TrickUsuario entidade = trickUsuarioRepository.findById(id).orElseThrow();

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId()).orElseThrow();
        Trick trick = trickRepository.findById(dto.getTrickId()).orElseThrow();

        entidade.setUsuario(usuario);
        entidade.setTrick(trick);
        entidade.setNivel(dto.getNivel());
        entidade.setAcertos(dto.getAcertos());
        entidade.setProficiencia(dto.isProficiencia());

        trickUsuarioRepository.save(entidade);

        return new TrickUsuarioOutputDTO(
                usuario.getId(),
                usuario.getLogin(),
                trick.getId(),
                trick.getNome(),
                trick.getBase().getDescricao(),
                entidade.getNivel(),
                entidade.getAcertos(),
                entidade.isProficiencia()
        );
    }

    public void deletar(TrickUsuarioId id) {
        TrickUsuario entidade = trickUsuarioRepository.findById(id).orElseThrow();
        trickUsuarioRepository.delete(entidade);
    }

    @Transactional
    public void processarTriagem(Long usuarioId, List<TrickUsuarioInputDTO> respostas) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (usuario.isFezTriagem()) {
            throw new RuntimeException("Usuário já passou pela triagem.");
        }

        int somaNiveis = 0;

        for (TrickUsuarioInputDTO dto : respostas) {
            Trick trick = trickRepository.findById(dto.getTrickId())
                    .orElseThrow(() -> new RuntimeException("Trick não encontrada"));

            TrickUsuario entidade = new TrickUsuario(usuario, trick, dto.getNivel());
            trickUsuarioRepository.save(entidade);
            somaNiveis += dto.getNivel();
        }

        int media = somaNiveis / respostas.size();
        usuario.setFezTriagem(true);
        usuarioRepository.save(usuario);
    }
}
