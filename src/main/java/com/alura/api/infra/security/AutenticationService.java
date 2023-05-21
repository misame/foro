package com.alura.api.infra.security;

import com.alura.api.domain.usuario.UsuarioRespository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticationService implements UserDetailsService {
    private UsuarioRespository usuarioRespository;

    public AutenticationService(UsuarioRespository usuarioRespository) {
        this.usuarioRespository = usuarioRespository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRespository.findByEmail(email);
    }
}
