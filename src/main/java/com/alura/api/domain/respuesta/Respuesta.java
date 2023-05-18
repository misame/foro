package com.alura.api.domain.respuesta;

import com.alura.api.domain.topico.Topico;
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

@Entity(name = "Respuesta")
@Table(name = "respuestas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String mensaje;
    @ManyToOne
    private Topico topico;
    @CreatedDate
    @NotNull
    @Column(name = "fecha_creacion")
    private LocalDateTime fecha_creacion;
    @ManyToOne
    private Usuario autor;
    @NotNull
    private boolean solucion = false;

    public Respuesta(String mensaje, Topico topico, Usuario autor) {
        this.mensaje = mensaje;
        this.topico = topico;
        this.autor = autor;
    }

    @PrePersist
    public void prePersist(){
        fecha_creacion = LocalDateTime.now();
    }
}
