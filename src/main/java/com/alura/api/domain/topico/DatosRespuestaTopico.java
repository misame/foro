package com.alura.api.domain.topico;


public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        String autor,
        String curso
) {
}
