package spring.forohub_challenge.domain.Usuario;


public record DatosListaUsuario(
        Long id,
        String name,
        String email
) {
    public DatosListaUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getNombre(), usuario.getEmail());
    }
}
