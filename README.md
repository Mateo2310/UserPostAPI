# UserPostAPI

API REST en Spring Boot para manejar usuarios, roles y posts.

## Descripción

Esta aplicación permite:
- Crear, actualizar, eliminar y consultar usuarios.
- Gestionar roles y permisos.
- Crear y administrar posts asociados a usuarios. El usuario con rol admin puede ver todos.
- Autenticación y autorización con roles.

## Tecnologías

- Java 21
- Spring Boot 3
- PostgreSQL 15
- Docker + Docker Compose
- Swagger (OpenAPI) para documentación de la API

## Levantar la aplicación con Docker

1. Clonar el proyecto:
   ```bash
   git clone <repo_url>
   cd <repo>
   ```
2. Construir y levantar los contenedores:
    ```bash
   docker-compose up --build
   ```
   Esto levantará dos servicios:
 * **app**: la API Spring Boot en el puerto 8081.
 * **db**: PostgreSQL en el puerto 5432.
 * **swagger**: http://localhost:8081/swagger-ui/index.html

3. Variables de entorno:
- **DB_HOST**: db
- **DB_PORT**: 5432
- **DB_NAME**: userpostapidb
- **DB_USER**: userpostapi
- **DB_PASSWORD**: wP9dX2rL7kF3bQ8hN4mA6cE1jY
- **APP_ADMIN_USERNAME**: admin
- **APP_ADMIN_PASSWORD**: x9!Vb7#qR4z@F2mLp8Ys

Allí podrás probar todos los endpoints: usuarios, roles y posts.

## Aclaraciones

- El proyecto carga el rol **ADMIN** automaticamente cuando se ejecuta, si existe no lo carga. Como asi tambien un usuario con rol ADMIN, username y contraseña, la cual se encuentran en las variables de entorno **APP_ADMIN_USERNAME** y **APP_ADMIN_PASSWORD** mencionadas en el punto tres.
- La arquitectura seleccionada es la onion, con sus respectivas capas, **domain**, **application**, **infrastructure**.
- La autenticacion esta realizada con Spring Security y JWT.
- Para mas informacion de los endpoints y registro en la app dirigirse al link del Swagger.

