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
import spring.forohub_challenge.domain.Cursos.*;


import java.net.URI;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursosController {
    @Autowired
    private CursosService service;
    @Operation(
            summary = "Crear un nuevo curso",
            description = "Este endpoint permite a usuarios autorizados crear nuevos cursos",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosCrearCurso.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Curso creado correctamente"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaCurso> crearCurso(
            @RequestBody @Valid DatosCrearCurso datosCrearCurso,
            UriComponentsBuilder uriComponentsBuilder){
        DatosRespuestaCurso respuesta = service.crearCurso(datosCrearCurso);
        URI url = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(respuesta.id()).toUri();

    return ResponseEntity.created(url).body(respuesta);
    }
    @Operation(
            summary = "Lista de Cursos",
            description = "Este endpoint permite al usuario ver todos los cursos disponibles."
    )
    @GetMapping
    public ResponseEntity<Page<DatosListaCurso>> listaCurso(
            @Parameter(description = "El numero de paginas a retornar")
            @PageableDefault(size = 10, sort = "nombre")
            Pageable pageable
    ){
        Page<DatosListaCurso> datosListaCursos = service.getCurso(pageable);
        return ResponseEntity.ok(datosListaCursos);
    }
    @Operation(
            summary = "Buscar por un curso",
            description = "Este endpoint permite al usuario buscar un curso por el nombre.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BusquedaCursoRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Curso encontrado"),
                    @ApiResponse(responseCode = "404", description = "Curso no encontrado")
            }
    )
    @GetMapping("/busqueda")
    public ResponseEntity<DatosListaCurso> buscarCurso(@RequestBody @Valid BusquedaCursoRequest busquedaCursoRequest){
        DatosListaCurso datosListaCurso = service.getCursoByNombre(busquedaCursoRequest.nombre());
    return ResponseEntity.ok(datosListaCurso);
    }
    @Operation(
            summary = "Actualizar un curso",
            description = "Este endpoint permite a usuarios autorizados actualizar un curso existente.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosActualizarCurso.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Curso actualizado correctamente"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaCurso> actualizarCurso(@RequestBody @Valid DatosActualizarCurso datosActualizarCurso){
        var curso = service.actualizarCurso(datosActualizarCurso);
        return ResponseEntity.ok(curso);
    }
    @Operation(
            summary = "Borrar un curso",
            description = "Este endpoint permite a un usuario autorizado para borrar un curso existente."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity cursoBorrado(@PathVariable Long id){
        service.cursoBorrado(id);
        return ResponseEntity.noContent().build();
    }
}
