package spring.forohub_challenge.domain.Usuario;

public record DatosRespuestaUsuario(
        Long id,
        String nombre,
        String email,
        String password
) {
    public DatosRespuestaUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getPassword());
    }
}
