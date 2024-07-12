package spring.forohub_challenge.domain.Respuesta;

import java.time.LocalDateTime;

public record DatosListaRespuesta(
        Long id,
        String mensaje,
        Boolean cerrado,
        Long idTopico,
        Long idUsuario,
        LocalDateTime fechaCreacion
) { public DatosListaRespuesta(Respuesta respuesta){
    this(respuesta.getId(),
            respuesta.getMensaje(),
            respuesta.getSolved(),
            respuesta.getUsuario().getId(),
            respuesta.getTopico().getId(),
            respuesta.getFechaCreacion());
}

}
