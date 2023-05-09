package com.alura.api.controller;

import com.alura.api.domain.curso.Curso;
import com.alura.api.domain.curso.CursoRepository;
import com.alura.api.domain.topico.DatosRegistroTopico;
import com.alura.api.domain.topico.DatosRespuestaTopico;
import com.alura.api.domain.topico.Topico;
import com.alura.api.domain.topico.TopicoRepository;
import com.alura.api.domain.usuario.Usuario;
import com.alura.api.domain.usuario.UsuarioRespository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    private TopicoRepository topicoRepository;
    private UsuarioRespository usuarioRespository;
    private CursoRepository cursoRepository;
    @Autowired
    public TopicoController(TopicoRepository topicoRepository, UsuarioRespository usuarioRespository, CursoRepository cursoRepository) {
        this.topicoRepository = topicoRepository;
        this.usuarioRespository = usuarioRespository;
        this.cursoRepository = cursoRepository;
    }

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@RequestBody @Valid
                                                                DatosRegistroTopico datosRegistroTopico,
                                                                UriComponentsBuilder uriComponentsBuilder){
    Usuario autor = usuarioRespository.findByNombre(datosRegistroTopico.autor());
    Curso curso = cursoRepository.findByNombre(datosRegistroTopico.curso());
    Topico topico = topicoRepository.save(new Topico(
            datosRegistroTopico.titulo(),
            datosRegistroTopico.mensaje(),
            autor,
            curso));
    DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(
            topico.getId(),
            topico.getTitulo(),
            topico.getMensaje(),
            topico.getAutor().getNombre(),
            topico.getCurso().getNombre());
    URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
    return ResponseEntity.created(url).body(datosRespuestaTopico);
    }
}
