package com.alura.api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRespository extends JpaRepository<Usuario,Long> {
    Usuario findByNombre(String nombre);
    UserDetails findByEmail(String email);

}
