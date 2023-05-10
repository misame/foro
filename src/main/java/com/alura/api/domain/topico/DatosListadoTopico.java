package com.alura.api.domain.topico;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        String fecha_creacion,
        String estatus_topico,
        String autor,
        String curso
) {
    public DatosListadoTopico(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha_creacion().toString(),
                topico.getEstatusTopico().toString(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre());
    }
}
