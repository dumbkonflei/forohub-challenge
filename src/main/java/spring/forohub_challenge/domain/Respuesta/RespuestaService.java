package spring.forohub_challenge.domain.Respuesta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import spring.forohub_challenge.domain.Topico.EstadoTopico;
import spring.forohub_challenge.domain.Topico.TopicoRepository;
import spring.forohub_challenge.domain.Usuario.UsuarioRepository;
import spring.forohub_challenge.infra.errors.ValidarIntegridad;

@Service
public class RespuestaService {
@Autowired
private TopicoRepository topicoRepository;
@Autowired
private UsuarioRepository usuarioRepository;
@Autowired
    private RespuestaRepository respuestaRepository;

public DatosRespuesta crearRespuesta(DatosCrearRespuesta datosCrearRespuesta){
    if (usuarioRepository.findById(datosCrearRespuesta.idUsuario()).isPresent()) {
        throw new ValidarIntegridad("No se encontro id del usuario");
    }
    if (topicoRepository.findById(datosCrearRespuesta.idTopico()).isPresent()){
        throw new ValidarIntegridad("No se encontro id del topico");
    }
    var usuario = usuarioRepository.findById(datosCrearRespuesta.idUsuario()).get();
    var topico = topicoRepository.findById(datosCrearRespuesta.idTopico()).get();
    var respuesta = new Respuesta(
      datosCrearRespuesta.mensaje(),
      datosCrearRespuesta.cerrado(),
      topico,
      usuario
    );
    respuestaRepository.save(respuesta);

    topico.setEstado(EstadoTopico.RESPONDIDO);
    topicoRepository.save(topico);
    return new DatosRespuesta(respuesta);
}
public Page<DatosListaRespuesta> getRespuesta(Pageable pageable){
    Page<Respuesta> respuestaPage = respuestaRepository.findByBorradoIsFalse(pageable);
return respuestaPage.map(DatosListaRespuesta::new);
}

public DatosRespuesta detallesRespuesta(@PathVariable Long id){
    Respuesta respuesta = respuestaRepository.getReferenceById(id);
    return new DatosRespuesta(respuesta);
}

public DatosRespuesta actualizarRespuesta(DatosActualizarRespuesta datosActualizarRespuesta){
    if (respuestaRepository.findById(datosActualizarRespuesta.id()).isPresent()){
        throw new ValidarIntegridad("No se encontró el id de la respuesta");
    }
    Respuesta respuesta = respuestaRepository.getReferenceById(datosActualizarRespuesta.id());
    respuesta.agregarDatos(datosActualizarRespuesta);
    return new DatosRespuesta(respuesta);
}
public void respuestaBorrada(Long id){
    Respuesta respuesta = respuestaRepository.findById(id)
            .orElseThrow(()-> new ValidarIntegridad("No se encontró un topico con esa id"));
    respuesta.respuestaBorrada();
    respuestaRepository.save(respuesta);
}
}
