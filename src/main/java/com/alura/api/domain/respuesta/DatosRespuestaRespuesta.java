package com.alura.api.domain.respuesta;


public record DatosRespuestaRespuesta(
        Long id,
        String autor,
        String fecha_creacion,
        String mensaje,
        boolean solucion
) {
    public DatosRespuestaRespuesta(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getAutor().getNombre(),
                respuesta.getFecha_creacion().toString(),
                respuesta.getMensaje(),
                respuesta.isSolucion());
    }
}
