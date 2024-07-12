package spring.forohub_challenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.forohub_challenge.domain.Usuario.DatosAutenticarUsuario;
import spring.forohub_challenge.domain.Usuario.Usuario;
import spring.forohub_challenge.infra.security.DatosJWTtoken;
import spring.forohub_challenge.infra.security.TokenService;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Operation(
            summary = "Autenticar un Usuario",
            description = "Este endpoint permite al usuario autenticarse y obtener una JWT token.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DatosAutenticarUsuario.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Autenticacion exitosa, devuelve un JWT token"),
                    @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
            }
    )
    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticarUsuario datosAutenticarUsuario){
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                datosAutenticarUsuario.email(),
                datosAutenticarUsuario.password()
        );
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
    return ResponseEntity.ok(new DatosJWTtoken(JWTtoken));
    }
}
