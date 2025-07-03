package com.skateholders.skateholders.services;

import com.skateholders.skateholders.DTOs.TriagemInputDTO;
import com.skateholders.skateholders.models.Trick;
import com.skateholders.skateholders.models.TrickUsuario;
import com.skateholders.skateholders.models.Usuario;
import com.skateholders.skateholders.repositories.TrickRepository;
import com.skateholders.skateholders.repositories.TrickUsuarioRepository;
import com.skateholders.skateholders.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TriagemService {

    @Autowired
    private TrickRepository trickRepository;

    @Autowired
    private TrickUsuarioRepository trickUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public void salvarRespostas(Usuario usuario, List<TriagemInputDTO> respostas) {
        for (TriagemInputDTO respostaDTO : respostas) {
            Trick trick = trickRepository.findById(respostaDTO.getTrickId())
                    .orElseThrow(() -> new RuntimeException("Trick não encontrada com ID: " + respostaDTO.getTrickId()));

            // --- A MUDANÇA ESTÁ AQUI ---
            // Usando o construtor da sua entidade que já lida com a chave composta
            TrickUsuario novaHabilidade = new TrickUsuario(usuario, trick, respostaDTO.getNivel());

            trickUsuarioRepository.save(novaHabilidade);
        }

        usuario.setFezTriagem(true);
        usuarioRepository.save(usuario);
    }
}