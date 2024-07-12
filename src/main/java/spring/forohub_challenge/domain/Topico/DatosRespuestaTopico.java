package spring.forohub_challenge.domain.Topico;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        EstadoTopico status,
        Long idUsuario,
        Long idCurso,
        Boolean cerrado
) {
    public DatosRespuestaTopico(Topico topico){
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getEstado(),
                topico.getUsuario().getId(),
                topico.getCursos().getId(),
                topico.getBorrado()
        );
    }
}
