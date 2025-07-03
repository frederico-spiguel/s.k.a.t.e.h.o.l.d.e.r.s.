package com.skateholders.skateholders.controllers;

import com.skateholders.skateholders.DTOs.*;
import com.skateholders.skateholders.models.Usuario;
import com.skateholders.skateholders.repositories.UsuarioRepository;
import com.skateholders.skateholders.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
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

            String token = tokenService.generateToken(dto.getLogin());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(new LoginResponseDTO("Credenciais inválidas"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@RequestBody LoginRequestDTO dto) {
        if (usuarioRepository.findByLogin(dto.getLogin()).isPresent()) {
            return ResponseEntity.badRequest().body(new LoginResponseDTO("Login já em uso"));
        }
        Usuario novo = new Usuario();
        novo.setLogin(dto.getLogin());
        novo.setSenha(encoder.encode(dto.getSenha()));
        usuarioRepository.save(novo);
        return ResponseEntity.ok(new LoginResponseDTO("Usuário registrado com sucesso"));
    }
}
