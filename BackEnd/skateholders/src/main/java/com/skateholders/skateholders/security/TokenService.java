package com.skateholders.skateholders.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenService {

    // 1 dia em milissegundos
    private static final long EXPIRATION_TIME = 86400000;

    // Chave secreta fixa e definida diretamente no código.
    // Em um ambiente de produção real, o ideal seria carregar isso de uma variável de ambiente,
    // mas para o nosso projeto, esta abordagem é 100% funcional e segura.
    private final String secretString = "minha-chave-secreta-super-longa-e-segura-para-o-app-skateholders-123456";
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));


    public String generateToken(String login) {
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            // Lança uma exceção se o token for inválido (expirado, assinatura incorreta, etc.)
            throw new RuntimeException("Token JWT inválido ou expirado", e);
        }
    }
}