package com.skateholders.skateholders.services;

import com.skateholders.skateholders.DTOs.SeshInputDTO;
import com.skateholders.skateholders.DTOs.SeshOutputDTO;
import com.skateholders.skateholders.models.Sesh;
import com.skateholders.skateholders.models.Usuario;
import com.skateholders.skateholders.repositories.SeshRepository;
import com.skateholders.skateholders.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeshService {

    @Autowired
    private SeshRepository seshRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public SeshOutputDTO criar(SeshInputDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Sesh sesh = new Sesh(usuario, dto.getData());
        sesh = seshRepository.save(sesh);

        return new SeshOutputDTO(
                sesh.getId(),
                usuario.getLogin(),
                sesh.getData(),
                sesh.isAberta(),
                sesh.isEditavel()
        );
    }

    public List<SeshOutputDTO> listarTodos() {
        return seshRepository.findAll().stream()
                .map(sesh -> new SeshOutputDTO(
                        sesh.getId(),
                        sesh.getUsuario().getLogin(),
                        sesh.getData(),
                        sesh.isAberta(),
                        sesh.isEditavel()
                ))
                .collect(Collectors.toList());
    }

    public Optional<SeshOutputDTO> buscarPorId(Long id) {
        return seshRepository.findById(id)
                .map(sesh -> new SeshOutputDTO(
                        sesh.getId(),
                        sesh.getUsuario().getLogin(),
                        sesh.getData(),
                        sesh.isAberta(),
                        sesh.isEditavel()
                ));
    }

    public void deletar(Long id) {
        seshRepository.deleteById(id);
    }
}
