package com.skateholders.skateholders.controllers;

import com.skateholders.skateholders.DTOs.*;
import com.skateholders.skateholders.models.Usuario;
import com.skateholders.skateholders.repositories.UsuarioRepository;
import com.skateholders.skateholders.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // <-- MUDANÇA AQUI: Importar o HttpStatus
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getSenha())
            );

            Usuario usuario = usuarioRepository.findByLogin(dto.getLogin()).orElseThrow();
            String token = tokenService.generateToken(dto.getLogin());

            // Assumindo que seu LoginResponseDTO tem um construtor (String token, boolean fezTriagem)
            return ResponseEntity.ok(new LoginResponseDTO(token, usuario.isFezTriagem()));
        } catch (BadCredentialsException e) {
            // Seria bom ter um construtor que aceita só a mensagem de erro também
            return ResponseEntity.status(401).build(); // Retornar 401 sem corpo é mais comum
        }
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody LoginRequestDTO dto) {
        if (usuarioRepository.findByLogin(dto.getLogin()).isPresent()) {
            // Apenas um corpo com a mensagem de erro é suficiente aqui.
            return ResponseEntity.badRequest().build();
        }

        Usuario novo = new Usuario();
        novo.setLogin(dto.getLogin());
        novo.setSenha(encoder.encode(dto.getSenha()));
        novo.setFezTriagem(false);

        usuarioRepository.save(novo);

        // --- INÍCIO DA MODIFICAÇÃO ---

        // 1. Geramos o token para o usuário recém-criado
        String token = tokenService.generateToken(novo.getLogin()); // <-- MUDANÇA AQUI

        // 2. Retornamos o token e o status da triagem (que é sempre false)
        // Usamos o status 201 CREATED, que é o correto para um registro bem-sucedido
        return ResponseEntity.status(HttpStatus.CREATED).body(new LoginResponseDTO(token, false)); // <-- MUDANÇA AQUI
    }
}