package com.alura.api.domain.curso;

import com.alura.api.domain.topico.Topico;

public record DatosListadoCurso(Long id, String nombre, String categoria) {
    public DatosListadoCurso(Curso curso){
        this(curso.getId(), curso.getNombre(), curso.getCategoria());
    }

}
