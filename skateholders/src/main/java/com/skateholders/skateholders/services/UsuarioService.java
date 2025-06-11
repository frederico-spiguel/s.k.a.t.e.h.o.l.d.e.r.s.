package com.skateholders.skateholders.services;

import com.skateholders.skateholders.models.Usuario;
import com.skateholders.skateholders.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario criar(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario buscarPorLogin(String login) {
        return usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com login: " + login));
    }


    public Usuario atualizar(Long id, Usuario novosDados) {
        return usuarioRepository.findById(id).map(usuarioExistente -> {
            usuarioExistente.setLogin(novosDados.getLogin());
            usuarioExistente.setSenha(novosDados.getSenha());
            return usuarioRepository.save(usuarioExistente);
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }


    public void deletarPorId(Long id) {
        usuarioRepository.deleteById(id);
    }

}
