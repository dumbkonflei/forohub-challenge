package spring.forohub_challenge.domain.Cursos;

public record BusquedaCursoRequest(
        String nombre,
        Categoria categoria
) {
}
