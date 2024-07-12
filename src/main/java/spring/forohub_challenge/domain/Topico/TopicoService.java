package spring.forohub_challenge.domain.Topico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import spring.forohub_challenge.domain.Cursos.CursosRepository;
import spring.forohub_challenge.domain.Usuario.UsuarioRepository;
import spring.forohub_challenge.infra.errors.ValidarIntegridad;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CursosRepository cursosRepository;

    public DatosRespuestaTopico crearTopico(DatosCrearTopico datosCrearTopico){
        if (usuarioRepository.findById(datosCrearTopico.idUsuario()).isPresent()){
            throw new ValidarIntegridad("No se encontro id del usuario");
        }
        if (cursosRepository.findById(datosCrearTopico.idCursos()).isPresent()){
            throw new ValidarIntegridad("No se encontro id del curso");
        }
        if (topicoRepository.findByTitulo(datosCrearTopico.titulo()).isPresent()){
            throw new ValidarIntegridad("Ya existe un topico con ese titulo");
        }
        if(topicoRepository.findByMensaje(datosCrearTopico.mensaje()).isPresent()){
            throw new ValidarIntegridad("Ya existe un topico con ese mensaje");
        }
        var usuario = usuarioRepository.findById(datosCrearTopico.idUsuario()).get();
        var cursos = cursosRepository.findById(datosCrearTopico.idCursos()).get();

        var topico = new Topico(
                datosCrearTopico.titulo(),
                datosCrearTopico.mensaje(),
                usuario,
                cursos);
        topicoRepository.save(topico);
        return new DatosRespuestaTopico(topico);
    }
    public Page<DatosListaTopico> getTopicos(Pageable pageable){
        Page<Topico> topicoPage = topicoRepository.findByBorradoFalse(pageable);
        return topicoPage.map(DatosListaTopico::new);
    }

    public Page<DatosListaTopico> getTopicosByCursosYFechaCreacion(String nombreCurso, String year, Pageable pageable){
        var cursos = cursosRepository.findByNombre(nombreCurso);
        if (cursos == null){
            throw new ValidarIntegridad("El curso no existe");
        }
        LocalDateTime fechaInicio;
        try{
            int numeroAno = Integer.parseInt(year);
            fechaInicio = LocalDateTime.of(numeroAno, 1,
                    1, 0, 0);
        } catch (NumberFormatException e){
            throw new ValidarIntegridad("Formato de fecha invalido");
        }
        var topicos = topicoRepository.findByCursosAndFechaCreacionAfter(cursos, fechaInicio, pageable);
        var datosListaTopicos = topicos.getContent().stream()
                .map(DatosListaTopico::new)
                .collect(Collectors.toList());

        return new PageImpl<>(datosListaTopicos, pageable, topicos.getTotalElements());
    }

    public DatosRespuestaTopico detallesTopico(@PathVariable Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        return new DatosRespuestaTopico(topico);
    }
    public DatosRespuestaTopico actualizarTopico(DatosActualizarTopico datosActualizarTopico){
        if (topicoRepository.findById(datosActualizarTopico.id()).isPresent()){
            throw new ValidarIntegridad("No se encontro el id del topico");
        }

        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id());
        topico.agregarRespuesta(datosActualizarTopico);
        return new DatosRespuestaTopico(topico);
    }
    public void topicoBorrado(Long id){
        Topico topico = topicoRepository.findById(id)
                .orElseThrow(() -> new ValidarIntegridad("No se encontro un topico con esa id"));
        topico.topicoBorrado();
        topicoRepository.save(topico);
    }

}
