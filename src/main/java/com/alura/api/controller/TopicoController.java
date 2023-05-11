package com.alura.api.controller;

import com.alura.api.domain.curso.Curso;
import com.alura.api.domain.curso.CursoRepository;
import com.alura.api.domain.curso.DatosListadoCurso;
import com.alura.api.domain.topico.*;
import com.alura.api.domain.usuario.Usuario;
import com.alura.api.domain.usuario.UsuarioRespository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

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

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listadoTopico(@PageableDefault(size = 10) Pageable paginacion){
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(DatosListadoTopico::new));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable Long id,@RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
        Usuario autor = usuarioRespository.findByNombre(datosActualizarTopico.autor());
        Curso curso = cursoRepository.findByNombre(datosActualizarTopico.curso());
        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.actualizarDatos(
                datosActualizarTopico.id(),
                datosActualizarTopico.titulo(),
                datosActualizarTopico.mensaje(),
                autor,
                curso);
        return ResponseEntity.ok(new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> retornaDatosTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        var datosTopico = new DatosRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre());
        return  ResponseEntity.ok(datosTopico);
    }
}
