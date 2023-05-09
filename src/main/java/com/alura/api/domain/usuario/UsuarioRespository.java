package com.alura.api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRespository extends JpaRepository<Usuario,Long> {
    Usuario findByNombre(String nombre);
}
