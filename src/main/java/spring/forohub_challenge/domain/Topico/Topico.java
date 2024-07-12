package spring.forohub_challenge.domain.Topico;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import spring.forohub_challenge.domain.Cursos.Cursos;
import spring.forohub_challenge.domain.Respuesta.Respuesta;
import spring.forohub_challenge.domain.Usuario.Usuario;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topico")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;

    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoTopico estado;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "usuario_id")
private Usuario usuario;

@OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
private List<Respuesta> respuesta = new ArrayList<>();

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "curso_id")
private Cursos cursos;

private Boolean borrado;
public Topico(
        String titulo,
        String mensaje,
        Usuario usuario,
        Cursos cursos){
    this.titulo = titulo;
    this.mensaje = mensaje;
    this.fechaCreacion = LocalDateTime.now();
    this.estado = EstadoTopico.SIN_RESPONDER;
    this.usuario = usuario;
    this.cursos = cursos;
    this.borrado = false;
}
public void setEstado(EstadoTopico estado){
    this.estado = estado;
}
public void agregarRespuesta(DatosActualizarTopico actualizarTopico){
    if (actualizarTopico.titulo()!= null){
        this.titulo = actualizarTopico.titulo();
    }
    if (actualizarTopico.mensaje() != null){
        this.mensaje = actualizarTopico.mensaje();
    }
}
public void topicoBorrado(){
    this.borrado = true;
}
}
