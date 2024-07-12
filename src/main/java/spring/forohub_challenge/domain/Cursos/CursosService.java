package spring.forohub_challenge.domain.Cursos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.forohub_challenge.infra.errors.ValidarIntegridad;

import java.util.Optional;

@Service
public class CursosService {
    @Autowired
    private CursosRepository cursosRepository;

    public DatosRespuestaCurso crearCurso(DatosCrearCurso datosCrearCurso){
        Optional<Cursos> cursoExistente = Optional.ofNullable(cursosRepository.findByNombre(datosCrearCurso.nombre()));

    if (cursoExistente.isPresent()){
        throw new ValidarIntegridad("Ya existe ese curso");
    }
    Cursos nuevoCurso = new Cursos(datosCrearCurso.nombre(), datosCrearCurso.categoria());
    cursosRepository.save(nuevoCurso);
    return new DatosRespuestaCurso(nuevoCurso);
    }
    public Page<DatosListaCurso> getCurso(Pageable pageable){
        Page<Cursos> cursoPage = cursosRepository.findByBorradoIsFalse(pageable);
        return cursoPage.map(DatosListaCurso::new);
    }
    public DatosListaCurso getCursoByNombre(String cursoNombre){
        Optional<Cursos> cursoExistente = cursosRepository.findByNombreIgnoreCase(cursoNombre);

    if (cursoExistente.isEmpty()){
        throw new ValidarIntegridad("El curso no existe");
    }
    return new DatosListaCurso(cursoExistente.get());
    }
    public DatosRespuestaCurso actualizarCurso(DatosActualizarCurso datosActualizarCurso){
        if (cursosRepository.findById(datosActualizarCurso.id()).isPresent()){
            throw new ValidarIntegridad("No se encontro id del curso");
        }
        Cursos cursos = cursosRepository.getReferenceById(datosActualizarCurso.id());
        cursos.agregarDatos(datosActualizarCurso);
        return new DatosRespuestaCurso(cursos);
}
public void cursoBorrado(Long id){
    Cursos cursos = cursosRepository.findById(id)
            .orElseThrow(()-> new ValidarIntegridad("No se encontro un curso con esa id"));

    cursos.cursoBorrado();
    cursosRepository.save(cursos);
    }
}

