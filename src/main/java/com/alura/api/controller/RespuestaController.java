package com.alura.api.controller;

import com.alura.api.domain.respuesta.DatosRegistroRespuesta;
import com.alura.api.domain.respuesta.DatosRespuestaRespuesta;
import com.alura.api.domain.respuesta.Respuesta;
import com.alura.api.domain.respuesta.RespuestaRepository;
import com.alura.api.domain.topico.Topico;
import com.alura.api.domain.topico.TopicoRepository;
import com.alura.api.domain.usuario.Usuario;
import com.alura.api.domain.usuario.UsuarioRespository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {
    private RespuestaRepository respuestaRepository;
    private TopicoRepository topicoRepository;
    private UsuarioRespository usuarioRespository;

    public RespuestaController(RespuestaRepository respuestaRepository, TopicoRepository topicoRepository, UsuarioRespository usuarioRespository) {
        this.respuestaRepository = respuestaRepository;
        this.topicoRepository = topicoRepository;
        this.usuarioRespository = usuarioRespository;
    }
    @PostMapping
    public ResponseEntity<DatosRespuestaRespuesta> registroRespuesta(@RequestBody @Valid
                                                                    DatosRegistroRespuesta datosRegistroRespuesta,
                                                                    UriComponentsBuilder uriComponentsBuilder){
        Usuario autor = usuarioRespository.findById(datosRegistroRespuesta.autor_id()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Topico topico = topicoRepository.findById(datosRegistroRespuesta.topico_id()).orElseThrow(() -> new RuntimeException("Topico no encontrado"));
        Respuesta respuesta = respuestaRepository.save(new Respuesta(
                datosRegistroRespuesta.mensaje(),
                topico,
                autor));
        DatosRespuestaRespuesta datosRespuestaRespuesta = new DatosRespuestaRespuesta(
                respuesta.getId(),
                respuesta.getAutor().getNombre(),
                respuesta.getFecha_creacion().toString(),
                respuesta.getMensaje(),
                respuesta.isSolucion());
        URI url = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaRespuesta);
    }
}
