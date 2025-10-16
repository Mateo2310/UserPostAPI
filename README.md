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
 * app: la API Spring Boot en el puerto 8081.
 * db: PostgreSQL en el puerto 5432.

3. Variables de entorno:
- DB_HOST=db
- DB_PORT=5432
- DB_NAME=userpostapidb
- DB_USER=userpostapi
- DB_PASSWORD=wP9dX2rL7kF3bQ8hN4mA6cE1jY

Allí podrás probar todos los endpoints: usuarios, roles y posts.

