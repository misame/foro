package com.alura.api.controller;

import com.alura.api.domain.usuario.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private UsuarioRespository usuarioRespository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsuarioController(UsuarioRespository usuarioRespository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioRespository = usuarioRespository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping
    public ResponseEntity<DastosRespuestaUsuario> registrarUsuario(@RequestBody @Valid
                                                                   DatosRegistroUsuario datosRegistroUsuario,
                                                                   UriComponentsBuilder uriComponentsBuilder){
        String contrasenaCodificada= bCryptPasswordEncoder.encode(datosRegistroUsuario.contrasena());
        Usuario usuario = usuarioRespository.save(new Usuario(
                datosRegistroUsuario.nombre(),
                datosRegistroUsuario.email(),
                contrasenaCodificada));
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
