package com.alura.api.domain.topico;

import com.alura.api.domain.curso.Curso;
import com.alura.api.domain.respuesta.Respuesta;
import com.alura.api.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String titulo;
    @NotBlank
    @Column(nullable = false)
    private String mensaje;
    @NotNull
    @CreatedDate
    @Column(nullable = false, name = "fecha_creacion")
    private LocalDateTime fecha_creacion;
    private Boolean activo;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EstatusTopico estatusTopico = EstatusTopico.NO_RESPONDIDO;
    @NotNull
    @ManyToOne
    private Usuario autor;
    @NotNull
    @ManyToOne
    private Curso curso;
    @NotNull
    @OneToMany(mappedBy = "topico")
    private List<Respuesta> respuestas = new ArrayList<>();

    public Topico(String titulo, String mensaje, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.activo = true;
        this.autor = autor;
        this.curso = curso;
    }
    @PrePersist
    public void prePersist() {
        fecha_creacion = LocalDateTime.now();
    }

    public void desactivarTopico() {
        this.activo=false;
    }

    public void actualizarDatos(DatosActualizarTopico datosActualizarTopico) {
        if (datosActualizarTopico.titulo() != null){
            this.titulo = datosActualizarTopico.titulo();
        }
        if (datosActualizarTopico.mensaje() != null){
            this.mensaje = datosActualizarTopico.mensaje();
        }

    }

    public void actualizarDatos(Long id, String titulo, String mensaje, Usuario autor, Curso curso) {
        this.id = id;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.autor = autor;
        this.curso = curso;
    }
}
