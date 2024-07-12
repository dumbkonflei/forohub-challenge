package spring.forohub_challenge.domain.Cursos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarCurso(
        @NotNull
        Long id,
        @NotBlank
        String nombre,
        @NotNull
        Categoria categoria
) {
}
