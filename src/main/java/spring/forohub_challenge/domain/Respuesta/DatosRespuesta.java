package spring.forohub_challenge.domain.Respuesta;

public record DatosRespuesta(
        Long id,
        String mensaje,
        Boolean cerrado,
        Long idTopico,
        Long idUsusario
) {
    public DatosRespuesta(Respuesta respuesta){
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getSolved(),
                respuesta.getUsuario().getId(),
                respuesta.getTopico().getId()
        );
    }
}
