package spring.forohub_challenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import spring.forohub_challenge.domain.Respuesta.*;
import java.net.URI;

@RestController
@RequestMapping("/respuesta")
public class RespuestaController {
    @Autowired
    private RespuestaService service;
    @Operation(
            summary = "Crear una nueva respuesta",
            description = "Este endpoint permite al usuario crear una nueva respuesta en el foro",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosCrearRespuesta.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Respuesta creada correctamente"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuesta> crearRespuesta(
            @RequestBody @Valid DatosCrearRespuesta datosCrearRespuesta,
            UriComponentsBuilder uriComponentsBuilder){
        DatosRespuesta respuesta = service.crearRespuesta(datosCrearRespuesta);
        URI url = uriComponentsBuilder.path("/respuesta/{id}").buildAndExpand(respuesta.id()).toUri();
   return ResponseEntity.created(url).body(respuesta);
    }
    @Operation(
            summary = "Lista respuestas",
            description = "Este endpoint permite al usuario listar todos los cursos en el foro."
    )
    @GetMapping
    public ResponseEntity<Page<DatosListaRespuesta>> listaRespuesta(
            @Parameter(description = "El numero de paginas a retornar")
            @PageableDefault(size =10, sort = "fechaCreacion")
            Pageable pageable
    ){
        Page<DatosListaRespuesta> datosListaRespuesta = service.getRespuesta(pageable);
        return ResponseEntity.ok(datosListaRespuesta);
    }

    @Operation(
            summary = "Actualizar Respuesta",
            description = "Este endpoint permite al usuario actualizar una respuesta existente en el foro",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosActualizarRespuesta.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Respuesta actualizada correctamente"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuesta> actualizarRespuesta(@RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta){
        var respuesta = service.actualizarRespuesta(datosActualizarRespuesta);
        return ResponseEntity.ok(respuesta);
    }

    @Operation(
            summary = "Borrar una respuesta",
            description = "Este endpoint permite al usuario borrar una respuesta existente."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity borrarRespuesta(@PathVariable Long id){
        service.respuestaBorrada(id);
        return ResponseEntity.noContent().build();
    }
}
