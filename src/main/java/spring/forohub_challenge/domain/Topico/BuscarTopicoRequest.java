package spring.forohub_challenge.domain.Topico;

public record BuscarTopicoRequest(
        String nombreCurso,
        String fechaCreacion
) {
}
