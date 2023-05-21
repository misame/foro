package com.alura.api.controller;

import com.alura.api.domain.curso.Curso;
import com.alura.api.domain.curso.CursoRepository;
import com.alura.api.domain.respuesta.Respuesta;
import com.alura.api.domain.respuesta.RespuestaRepository;
import com.alura.api.domain.topico.*;
import com.alura.api.domain.usuario.Usuario;
import com.alura.api.domain.usuario.UsuarioRespository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    private TopicoRepository topicoRepository;
    private UsuarioRespository usuarioRespository;
    private CursoRepository cursoRepository;
    private RespuestaRepository respuestaRepository;

    public TopicoController(TopicoRepository topicoRepository, UsuarioRespository usuarioRespository, CursoRepository cursoRepository, RespuestaRepository respuestaRepository) {
        this.topicoRepository = topicoRepository;
        this.usuarioRespository = usuarioRespository;
        this.cursoRepository = cursoRepository;
        this.respuestaRepository = respuestaRepository;
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
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@PathVariable Long id,@RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
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
    public ResponseEntity<DatosRespuestasDeTopico> retornoTopicoRespuestas(@PathVariable Long id,@PageableDefault(size = 5) Pageable pageable){
        Topico topico = topicoRepository.getReferenceById(id);
        if (topico != null){
            Page<Respuesta> respuestas = respuestaRepository.findByTopicoId(id,pageable);
            Page<DatosRespuestasPorTopico> respuestaPage = respuestas.map(respuesta -> new DatosRespuestasPorTopico(respuesta.getId()
                            ,respuesta.getAutor().getNombre()
                            ,respuesta.getFecha_creacion().toString()
                            ,respuesta.getMensaje()
                            ,respuesta.isSolucion()) );

            return ResponseEntity.ok(new DatosRespuestasDeTopico(
                    topico.getId(),
                    topico.getTitulo(),
                    topico.getMensaje(),
                    topico.getAutor().getNombre(),
                    topico.getCurso().getNombre(),
                    respuestaPage.getContent()));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
