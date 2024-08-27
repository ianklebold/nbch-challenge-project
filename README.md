# NBCH CHALLENGE PROJECT 
Aqui presento el challenge de NBCH

## Iniciemos :boom:

Las siguientes instrucciones te permitiran tener una copia del proyecto funcionando en tu maquina local. En la seccion de **despliegue** tendrás
las instrucciones para instalar el proyecto. 


### Pre-Requisitos :technologist:

Aqui presentamos que cosas necesitas para correr la api. 
```
- Docker
- Docker-compose
- Docker-desktop
- Maven
```

## Caracteristicas adicionales entregadas :computer:
| Caracteristicas  | Descripcion  | Completado/No Completado  |
|-------------|-------------|-------------|
| Autenticación     | Los mecanismos mas comunes de autenticación en APIs REST son autenticación Basic y autenticación Bearer con JWT(JSON Web Token)   | Completado :white_check_mark:  |
| Autorización     | El mecanismo mas común de autorización en APIs REST es JWT ya que es stateless   | Completado  :white_check_mark: |
| Despliegue     | No se requiere desplegar el servicio en un servidor pero si se desea hacerlo hay algunas plataformas que ofrecen el servicio gratuitamente por tiempo limitado y/o para proyectos personales como AWS, Heroku o GCP   | No Completado  |
| Pruebas unitarias    | La herramienta mas utilizada es JUnit. Además se suele utilizar Mockito para crear mocks   | Completado :white_check_mark:  |
| Base de datos    | Es suficiente con utilizar una base de datos en memoria como H2 o SQLite pero opcionalmente se puede implementar con un motor PostgreSQL, SQL Server o MySQL   | Completado :white_check_mark:  |
| Versionado de Scripts SQL     | Flyway o Liquibase   | Completado :white_check_mark:  |
| Contenerizado     | Hoy en día es bastante común el contenerizado de servicios mediante Docker   | Completado :white_check_mark:  |
| Validación de datos     | JSR 380 - Bean Validation 2.0. Ejemplo https://www.baeldung.com/javax-validation   | Completado :white_check_mark:  |

## Dependencias :warning:

| Dependencia                                               | Versión    |
|----------------------------------------------------------|------------|
| org.springframework.boot:spring-boot-starter-data-jpa     | 3.3.3     |
| org.springframework.boot:spring-boot-starter-validation    | 3.3.3     |
| org.springframework.boot:spring-boot-starter-web          | 3.3.3     |
| com.mysql:mysql-connector-j                               | 8.4.0     |
| org.springframework.boot:spring-boot-devtools             | 3.3.3   |
| org.flywaydb:flyway-mysql                                 | 10.10.0   |
| com.h2database:h2                                        | 2.2.224   |
| com.projectlombok:lombok                                        | 1.18.34   |
| org.springframework.boot:spring-boot-starter-test         | 3.3.3     |
| org.springdoc:springdoc-openapi-starter-webmvc           | 2.3.0     |
| org.mapstruct:mapstruct                                  | 1.5.3.Final |
| org.springframework.boot:spring-boot-starter-security      | 3.3.3     |
| org.springframework.security:spring-security-test           | 6.3.3     |
| io.jsonwebtoken:jjwt-api                                  | 0.11.5    |
| io.jsonwebtoken:jjwt-jackson                              | 0.11.5    |


## Construido con :neckbeard:

- [MYSQL 8.4.0](https://dev.mysql.com/doc/relnotes/mysql/8.4/en/) - Base de datos
- [Dbeaver-ce](https://dbeaver.io/download/) - Herramienta de administracion de datos 
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) - Lenguaje de programacion
- [Maven 3.9.6](https://maven.apache.org/index.html) - Manejador de dependencias
- [Spring-Boot 3.3](https://spring.io/projects/spring-boot) - Framework
- [Spring Initializr](https://start.spring.io/) - Scaffolding
- [Swagger](https://swagger.io/tools/swagger-editor/) - Documentacion de API
- [GIT](https://git-scm.com/) - Versionado

## Despliegue :warning:

Para desplegar la api seguir estas instrucciones : 

1. Clonar repositorio
2. Iniciar docker o docker-desktop
3. Posicionarse con la consola de comandos en la carpeta ian-challenge-project
4. Correr el comando ```mvn clean package -DskipTests```. Esperar a que finalice
5. Correr el comando ```docker-compose up --build```. Esperar a que finalice
![Swagger0](https://github.com/user-attachments/assets/dc6bd91c-19e6-48ff-ab24-179d2f4a42d2)

En docker desktop podemos visualizar algo asi en la seccion de contenedores :
![Swagger-1](https://github.com/user-attachments/assets/4de747f5-147d-42c7-82fb-a26e973897c9)

6. Listo! Acceder a la url http://localhost:8082/swagger-ui/index.html para probar la API.
7. Ir a la seccion **credenciales** para autenticarse y utilizar la API

## Credenciales :warning:
Para probar la API se dispuso de la herramienta Swagger, la cual nos permite documentar la API y probarla.
Las credenciales disponibles que se tienen en la API son dos :

| Username | Password |
|------------|------------|
| ADMIN_NBCH     | NBCH_PASS_TEMPLATE    |
| REGULAR_USER_NBCH     | NBCH_PASS_TEMPLATE    |

1. Ir a la seccion Login e ingresar las credenciales. Luego darle a EXECUTE
![Swagger1](https://github.com/user-attachments/assets/d35038d5-e6e9-402b-8c06-bf325d3b5957)

2. Copiar el TOKEN
![Swagger2](https://github.com/user-attachments/assets/b1f46c62-e926-4897-a4e1-d20effcd378f)


3. Ir al boton Authorize, pegar el token y darle a Authorize
![Swagger3](https://github.com/user-attachments/assets/ffbf8394-bcb4-4687-80a2-51b7bc0ef0a0)

4. Ya podrias consumir las APIS de acuerdo a la autorizacion de cada usuario.

**El usuario admin podra :**
- Crear un producto
- Eliminar un producto por id
- Obtener un producto por id

**El usuario regular user podra :**
- Obtener un producto por id

**Todos los usuarios (sin necesidad de autenticarse) :**
- Obtener todos los productos

## Autores :star_struck:

- Fernández Ian - *Desarrollador Backend, Tester, Documentación* - :alien:[ianklebold](https://www.linkedin.com/in/ian-fern%C3%A1ndez-a72598179/)

## Despedida

Fue un gusto trabajar en este proyecto y compartir mis conocimientos. Con :heart: Por :alien:[ianklebold](https://www.linkedin.com/in/ian-fern%C3%A1ndez-a72598179/) 
