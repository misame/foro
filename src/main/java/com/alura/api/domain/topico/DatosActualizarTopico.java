package com.alura.api.domain.topico;

import com.alura.api.domain.curso.Curso;
import com.alura.api.domain.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull
        Long id,
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotNull
        String autor,
        @NotNull
        String curso
) {
}
