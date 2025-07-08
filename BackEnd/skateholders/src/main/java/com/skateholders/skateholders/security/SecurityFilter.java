package com.skateholders.skateholders.security;

import com.skateholders.skateholders.models.Usuario;
import com.skateholders.skateholders.repositories.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) { // Não precisamos checar "Bearer " aqui, o proximo if faz isso
            String token = authHeader.replace("Bearer ", "");
            String login = tokenService.validateToken(token);
            Usuario usuario = usuarioRepository.findByLogin(login).orElse(null);

            if (usuario != null) {
                // Se o usuário foi encontrado, o colocamos no contexto de segurança
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }

    // --- A LÓGICA MAIS IMPORTANTE ESTÁ AQUI ---
    // Este mtodo diz ao Spring em quais requisições este filtro NÃO DEVE RODAR.
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        // O filtro será ignorado para qualquer rota que comece com /auth/
        return path.startsWith("/auth/");
    }
}