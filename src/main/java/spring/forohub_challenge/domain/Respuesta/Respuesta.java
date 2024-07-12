package spring.forohub_challenge.domain.Respuesta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.forohub_challenge.domain.Topico.Topico;
import spring.forohub_challenge.domain.Usuario.Usuario;

import java.time.LocalDateTime;

@Table(name = "respuesta")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    private Boolean solved;
    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private Boolean borrado;

    public Respuesta(String mensaje, Boolean borrado, Topico topico, Usuario usuario){
        this.mensaje = mensaje;
        this.solved = borrado != null ? borrado : false;
        this.fechaCreacion = LocalDateTime.now();
        this.topico = topico;
        this.usuario = usuario;
        this.borrado = false;
    }
     public void agregarDatos(DatosActualizarRespuesta datos){
        if (datos.mensaje() != null){
            this.mensaje = datos.mensaje();
            this.fechaCreacion = LocalDateTime.now();
        }
     }
     public void respuestaBorrada(){
        this.borrado = true;
     }

}
