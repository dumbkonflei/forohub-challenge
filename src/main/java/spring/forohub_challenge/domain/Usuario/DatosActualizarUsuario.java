package spring.forohub_challenge.domain.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarUsuario(
        @NotNull
        Long id,
        @NotBlank
        String nombre,
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}
