   <p align="left">
   <img src="https://img.shields.io/badge/STATUS-FINALIZADO-blue">
   </p>
   
   # Forohub-Challenge
   Bienvenidos sean todos a este nuevo desafío de programación, Forohub. En donde aprendi persistencia de datos, seguridad, autenticar y autorizar request, crear tablas en una base de datos, metodos POST, GET, DELETE, PUT y crear mi propia API.

# Descripción

Foro hub es una plataforma de foros diseñada para fomentar la interacción y el intercambio de conocimientos entre los usuarios. Este proyecto se centra exclusivamente en el desarrollo de la API y el backend, proporcionando una infraestructura para la gestión de usuarios y la creación y manejo de temas.

# Caracteristicas
- Registro y Gestion de Usuario
- Autenticación
- Gestión de temas (Crear, Responder, Borrar)

# Acesso
1. Clonar el Repositorio
```
git clone https://github.com/dumbkonflei/forohub-challenge.git
```   
2. Ejecutar la Aplicación:
* Abra el proyecto en IntelliJ IDEA.
* Configure la conexión de la base de datos PostgreSQL en application.properties mediante el uso de variables de entorno:
 ```
spring.datasource.url=jdbc:postgresql://${DB_HOST}/${DB_NAME}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASS}
spring.datasource.driver-class-name=org.postgresql.Driver
   ```
* Compila y ejecuta la aplicación.

3. Explora la API
* Acceda a Swagger UI en http://localhost:8080/swagger-ui/index.html#/ para la documentación de la API.
* Utilice herramientas como Insomnia para probar los endpoints e interactuar con la API.

# Licensia
 [MIT](https://choosealicense.com/licenses/mit/#)


# Tecnologias Usadas
- Java 17
- Spring Boot
- Maven
- Spring Security
- JWT (JSON Web Tokens)
- Spring Data JPA
- H2 Database
- BD Postgres
- Springdoc
- Swagger
