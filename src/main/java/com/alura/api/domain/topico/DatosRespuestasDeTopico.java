package com.alura.api.domain.topico;

import java.util.List;

public record DatosRespuestasDeTopico(
        Long id,
        String titulo,
        String mensaje,
        String autor,
        String curso,
        List<DatosRespuestasPorTopico> respuestas) {

}
