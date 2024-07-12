package spring.forohub_challenge.domain.Cursos;

public record DatosRespuestaCurso(
        Long id,
        String nombre,
        Categoria categoria,
        Boolean borrado
) {
    public DatosRespuestaCurso(Cursos cursos){
        this(
                cursos.getId(),
                cursos.getNombre(),
                cursos.getCategoria(),
                cursos.getBorrado()
        );
    }
}
