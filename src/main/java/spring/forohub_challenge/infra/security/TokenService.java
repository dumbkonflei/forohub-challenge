package spring.forohub_challenge.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.forohub_challenge.domain.Usuario.Usuario;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {
@Value("{api.security.secret}")
    private String apiSecret;



public String generarToken(Usuario usuario){
    try{
        Algorithm algorithm = Algorithm.HMAC256(apiSecret);
        return JWT.create()
                .withIssuer("foro hub")
                .withSubject(usuario.getEmail())
                .withClaim("id", usuario.getId())
                .withExpiresAt(generarFechaDeExpiracion())
                .sign(algorithm);
    } catch (JWTCreationException excepction){
    throw new RuntimeException();
    }
}
public String getSubject(String token){
    if (token == null){
        throw new RuntimeException();
    }
    DecodedJWT verifier = null;
    try {
        Algorithm algorithm = Algorithm.HMAC256(apiSecret);
        verifier = JWT.require(algorithm)
                .withIssuer("foro hub")
                .build()
                .verify(token);
        verifier.getSubject();
    } catch (JWTVerificationException exception){
        System.out.println(exception);
    }
    if (verifier.getSubject() == null){
        throw new RuntimeException("Verifier invalido");
    }
    return verifier.getSubject();

}
    private Instant generarFechaDeExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}

