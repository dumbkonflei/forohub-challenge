package spring.forohub_challenge.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public OpenAPI customAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ForumHub API")
                        .version("1.0")
                        .description("**API ForumHub** Es una plataforma que permite a los usuarios crear y participar en debates sobre diversos temas. Los usuarios pueden crear nuevos temas, publicar respuestas a temas existentes y marcar las respuestas como resueltas si brindan una solución satisfactoria.\n\n" +
                                "para acceder a la API, los usuarios primero deben crear una cuenta y autenticarse obteniendo un token JWT válido. Este token debe incluirse en el encabezado \"Autorización\" de todas las solicitudes posteriores a la API.\n\n" +
                                "La API proporciona endpoints para las siguientes funcionalidades:\n\n" +
                                "- **Gestión de usuarios**: los usuarios pueden registrarse, iniciar sesión y actualizar la información de su perfil.\n" +
                                "- **Gestión de temas**: los usuarios pueden crear nuevos temas, ver temas existentes y publicar respuestas a temas.\n" +
                                "- **Gestión de respuestas**: los usuarios pueden publicar respuestas a temas, marcar las respuestas como resueltas y actualizar o eliminar sus propias respuestas..\n" +
                                "La API se creó utilizando *SpringBoot 3* y utiliza varios componentes Spring, como *Spring Security* para autenticación y autorización, *Spring Data* para interacciones de bases de datos y *SpringDoc* para generar documentación API interactiva."))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
    @Bean
    public String message(){
        return "bearer worked";
    }
}
