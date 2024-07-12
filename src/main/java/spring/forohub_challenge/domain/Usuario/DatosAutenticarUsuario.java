package spring.forohub_challenge.domain.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosAutenticarUsuario(
        @Email
        String email,
        @NotBlank
        String password
) {
}
