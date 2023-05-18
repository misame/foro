package com.alura.api.domain.topico;

public record DatosRespuestasPorTopico(
        Long id,
        String autor,
        String fecha_creacion,
        String mensaje,
        boolean solucion
) {
}
