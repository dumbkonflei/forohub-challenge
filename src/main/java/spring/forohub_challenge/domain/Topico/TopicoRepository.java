package spring.forohub_challenge.domain.Topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import spring.forohub_challenge.domain.Cursos.Cursos;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Optional<Topico> findByTitulo(String titulo);
    Optional<Topico> findByMensaje(String mensaje);
    Page<Topico> findByBorradoFalse(Pageable pageable);
    Page<Topico> findByCursosAndFechaCreacionAfter(Cursos cursos, LocalDateTime fechaCreacon, Pageable pageable);
}
