package spring.forohub_challenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import spring.forohub_challenge.domain.Usuario.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuario")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {
    @Autowired
    private UsuarioService service;
    @Operation(
            summary = "Crear un nuevo usuario",
            description = "Este endpoint permite al usuario crear una nueva cuenta.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosUsuario.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> crearUsuario(
            @RequestBody @Valid DatosUsuario datosUsuario,
            UriComponentsBuilder uriComponentsBuilder) {
        DatosRespuestaUsuario respuesta = service.crearUsuario(datosUsuario);
        URI url = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(respuesta.id()).toUri();

        return ResponseEntity.created(url).body(respuesta);
    }
    @Operation(
            summary = "Lista usuarios",
            description = "Este endpoint permite al usuario ver una lista de todos los usuarios registrados."
    )    @GetMapping
    public ResponseEntity<List<DatosListaUsuario>> listUsuario() {
        List<DatosListaUsuario> datosListaUsuarios = service.getUsuarios();
        return ResponseEntity.ok(datosListaUsuarios);
    }
    @Operation(
            summary = "Actualizar usuario",
            description = "Este endpoint permite al usuario autorizado actualizar su propia informacion.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosActualizarUsuario.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data")
            }
    )
    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaUsuario> actualizarUsuario(
            @RequestBody @Valid DatosActualizarUsuario datosActualizarUsuario){
        var usuario = service.actualizarUsuario(datosActualizarUsuario);
        return ResponseEntity.ok(usuario);
    }
    @Operation(
            summary = "Borrar usuario",
            description = "Este endpoint permite al usuario autorizado para borrar su cuenta."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity usuarioBorrado(@PathVariable Long id){
        service.usuarioBorrado(id);
        return ResponseEntity.noContent().build();
    }
}
