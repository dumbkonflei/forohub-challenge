package spring.forohub_challenge.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import spring.forohub_challenge.domain.Topico.*;


import java.net.URI;

@RestController
@RequestMapping("/topico")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {
    @Autowired
    private TopicoService service;

    @Operation(
            summary = "Crear un nuevo topico",
            description = "Este endpoint permite al usuario autorizado crear un nuevo topico.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosCrearTopico.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Topico creado correctamente"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> crearTopico(
            @RequestBody @Valid DatosCrearTopico datosCrearTopico,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        DatosRespuestaTopico respuesta = service.crearTopico(datosCrearTopico);
        URI url = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(respuesta.id()).toUri();
        return ResponseEntity.created(url).body(respuesta);
    }

    @Operation(
            summary = "Listar topicos",
            description = "Este endpoint permite al usuario listar todos los topcios disponibles."
    )
    @GetMapping
    public ResponseEntity<Page<DatosListaTopico>> listaTopico(
            @Parameter(description = "El numero de paginas a retornar")
            @PageableDefault(size = 10, sort = "fechaCreacion")
            Pageable pageable
    ) {
        Page<DatosListaTopico> datosListaTopicosPage = service.getTopicos(pageable);
        return ResponseEntity.ok(datosListaTopicosPage);
    }

    @Operation(
            summary = "Buscar un topico",
            description = "Este endpoint permite al usuario buscar un topico por el nombre del curso y la fecha de creacion.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BuscarTopicoRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Topico encontrado"),
                    @ApiResponse(responseCode = "404", description = "Topico no encontrado")
            }
    )
    @GetMapping("/busqueda")
    public ResponseEntity<Page<DatosListaTopico>> buscarTopico(
            @RequestBody BuscarTopicoRequest buscarTopico,
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable
    ) {
        Page<DatosListaTopico> datosListaTopicosPage = service.getTopicosByCursosYFechaCreacion(
                buscarTopico.nombreCurso(),
                buscarTopico.fechaCreacion(),
                pageable
        );
        return ResponseEntity.ok(datosListaTopicosPage);
    }
    @Operation(
            summary = "Actualizar un topico",
            description = "Este endpoint permite a usuarios autorizados actualizar un topico existente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosActualizarTopico.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Topico actualizado correctamente"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
@PutMapping
@Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        var topico = service.actualizarTopico(datosActualizarTopico);
        return ResponseEntity.ok(topico);
    }
    @Operation(
            summary = "Borrar un topico",
            description = "Este endpoint permite al usuario autorizado borrar un topico existente."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity borrarTopico(@PathVariable Long id){
        service.topicoBorrado(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(
            summary = "Detalles del topico",
            description = "Este endpoint permite al usuario obtener los detalles de un topico especifico."
    )
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> detallesTopico(@PathVariable Long id){
        DatosRespuestaTopico datosRespuestaTopico = service.detallesTopico(id);
        return ResponseEntity.ok(datosRespuestaTopico);
    }
}
