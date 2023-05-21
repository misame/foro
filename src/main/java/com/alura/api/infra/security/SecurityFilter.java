package com.alura.api.infra.security;

import com.alura.api.domain.usuario.UsuarioRespository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UsuarioRespository usuarioRespository;

    public SecurityFilter(TokenService tokenService, UsuarioRespository usuarioRespository) {
        this.tokenService = tokenService;
        this.usuarioRespository = usuarioRespository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if (authHeader != null){
            var token  = authHeader.replace("Bearer ","");
            var email = tokenService.getSubject(token);
            System.out.println("tokenservice: "+email);
            if (email != null){
                var usuario = usuarioRespository.findByEmail(email);
                var authentication = new UsernamePasswordAuthenticationToken(email,null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
