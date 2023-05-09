package com.alura.api.controller;

import com.alura.api.domain.usuario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private UsuarioRespository usuarioRespository;
    @Autowired
    public UsuarioController(UsuarioRespository usuarioRespository) {
        this.usuarioRespository = usuarioRespository;
    }

    @PostMapping
    public ResponseEntity<DastosRespuestaUsuario> registrarUsuario(@RequestBody @Valid
                                                                   DatosRegistroUsuario datosRegistroUsuario,
                                                                   UriComponentsBuilder uriComponentsBuilder){
        Usuario usuario = usuarioRespository.save(new Usuario(datosRegistroUsuario));
        DastosRespuestaUsuario dastosRespuestaUsuario = new DastosRespuestaUsuario(
                usuario.getNombre(), usuario.getEmail()
        );
        URI url = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(dastosRespuestaUsuario);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoUsuario>> listadoUsuarios(@PageableDefault(size = 5)Pageable paginacion){
        return ResponseEntity.ok(usuarioRespository.findAll(paginacion).map(DatosListadoUsuario::new));
    }
}
