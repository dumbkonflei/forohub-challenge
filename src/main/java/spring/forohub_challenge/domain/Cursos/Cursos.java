package spring.forohub_challenge.domain.Cursos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import spring.forohub_challenge.domain.Topico.Topico;

import java.util.List;

@Table(name = "cursos")
@Entity(name = "Cursos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cursos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @Column(name = "categoria")
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @OneToMany(mappedBy = "cursos", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Topico> topicos;

    private Boolean borrado;

    public Cursos(String nombre, Categoria categoria){
        this.nombre = nombre;
        this.categoria = categoria;
        this.borrado = false;
    }

    public void agregarDatos (DatosActualizarCurso datos){
        if (datos.nombre() != null){
            this.nombre = datos.nombre();
        }
        if (datos.categoria() != null){
            this.categoria = datos.categoria();
        }
    }
    public void cursoBorrado(){
        this.borrado = true;
    }
}

