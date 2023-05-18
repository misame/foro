package com.alura.api.domain.topico;

import com.alura.api.domain.respuesta.DatosRespuestaRespuesta;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        String autor,
        String curso
) {
}
