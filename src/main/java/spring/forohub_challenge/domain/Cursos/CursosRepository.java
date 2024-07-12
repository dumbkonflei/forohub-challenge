package spring.forohub_challenge.domain.Cursos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CursosRepository extends JpaRepository<Cursos, Long> {
    Cursos findByNombre(String nombre);
    Page<Cursos> findByBorradoIsFalse(Pageable pageable);
    Optional<Cursos> findByNombreIgnoreCase(String nombre);
}
