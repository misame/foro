package com.alura.api.domain.usuario;

import jakarta.validation.constraints.Email;

public record DatosAutenticacionUsuario(@Email String email, String contrasena) {
}
