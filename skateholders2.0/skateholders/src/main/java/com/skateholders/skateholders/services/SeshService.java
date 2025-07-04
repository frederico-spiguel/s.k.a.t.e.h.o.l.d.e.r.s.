package com.skateholders.skateholders.services;

import com.skateholders.skateholders.DTOs.SeshInputDTO;
import com.skateholders.skateholders.DTOs.SeshOutputDTO;
import com.skateholders.skateholders.models.Sesh;
import com.skateholders.skateholders.models.Usuario;
import com.skateholders.skateholders.repositories.SeshRepository;
import com.skateholders.skateholders.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeshService {

    @Autowired
    private SeshRepository seshRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // --- MÉTODOS CRUD EXISTENTES ---

    public SeshOutputDTO criar(SeshInputDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Sesh sesh = new Sesh(usuario, dto.getData());
        sesh = seshRepository.save(sesh);

        return new SeshOutputDTO(sesh);
    }

    public List<SeshOutputDTO> listarTodos() {
        return seshRepository.findAll().stream()
                .map(SeshOutputDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<SeshOutputDTO> buscarPorId(Long id) {
        return seshRepository.findById(id)
                .map(SeshOutputDTO::new);
    }

    public void deletar(Long id) {
        seshRepository.deleteById(id);
    }


    // --- NOVOS MÉTODOS PARA A FUNCIONALIDADE DE SESSÕES ---

    /**
     * Busca todas as datas únicas que possuem sessões para o usuário atualmente logado.
     * @return Uma lista de datas.
     */
    public List<LocalDate> buscarDatasSessoesDoUsuarioLogado() {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return seshRepository.findDistinctSeshDatesByUsuario(usuarioLogado);
    }

    /**
     * Busca os detalhes completos de uma sessão em uma data específica para o usuário logado.
     * @param data A data da sessão a ser buscada.
     * @return Um Optional contendo o DTO da sessão, ou vazio se não for encontrada.
     */
    public Optional<SeshOutputDTO> buscarSessaoPorDataDoUsuarioLogado(LocalDate data) {
        Usuario usuarioLogado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return seshRepository.findByUsuarioAndData(usuarioLogado, data)
                .map(SeshOutputDTO::new); // Converte a Sesh encontrada para o DTO de saída
    }
}