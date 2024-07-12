package spring.forohub_challenge.domain.Cursos;

public record DatosListaCurso(
        Long id,
        String nombre,
        Categoria categoria
) {
    public DatosListaCurso(Cursos cursos){
        this(
                cursos.getId(),
                cursos.getNombre(),
                cursos.getCategoria()
        );
    }
}
