# Sistema de Gestión de Productos - Backend API
### Java Spring Boot PostgreSQL JWT

Este repositorio contiene la API REST para el Sistema de Gestión de Productos, permitiendo a los usuarios registrarse, iniciar sesión y administrar su propio catálogo de productos de manera segura.

## Demo en vivo
- **API URL:** [https://gestionproductos.onrender.com](https://gestionproductos.onrender.com)

## Características principales
- **Autenticación y autorización:** Sistema completo con JWT.
- **Gestión de productos:** CRUD completo con acceso restringido por usuario.
- **API RESTful:** Endpoints protegidos con Spring Security.
- **Seguridad robusta:** Implementación de JWT y encriptación de contraseñas.
- **Base de datos en la nube:** PostgreSQL alojado en Neon.

## Estructura del proyecto
```
src/
├── main/
│   ├── java/
│   │   └── com.jerorodriguez.springbootjwt/
│   │       ├── auth/           # Lógica de autenticación y autorización
│   │       ├── config/         # Configuraciones de Spring
│   │       ├── controllers/    # Controladores REST
│   │       ├── demo/           # Demostraciones de funcionalidad
│   │       ├── entities/       # Entidades JPA
│   │       ├── jwt/            # Configuración y utilidades JWT
│   │       ├── repository/     # Repositorios JPA
│   │       ├── services/       # Servicios de negocio
│   │       ├── KeyGenerator    # Generador de claves para JWT
│   │       └── SpringBootJwtApplication # Clase principal
```

## Tecnologías utilizadas
- **Spring Boot:** Framework para el desarrollo de aplicaciones Java.
- **Spring Security:** Para gestión de autenticación y autorización.
- **Spring Data JPA:** Para el acceso y manipulación de datos.
- **PostgreSQL:** Base de datos relacional en la nube (Neon).
- **JSON Web Token (JWT):** Para la gestión de tokens de autenticación.
- **Maven:** Gestión de dependencias y construcción.
- **Java 17:** Versión del lenguaje utilizada.

## Requisitos previos (desarrollo local)
- Java 17 o superior
- Maven 3.6 o superior
- PostgreSQL 12 o superior

## Configuración del entorno local
1. Clona el repositorio:
```bash
git clone https://github.com/tu-usuario/gestion-productos-backend.git
cd gestion-productos-backend
```

2. Configura la base de datos PostgreSQL en application.properties:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/securitydb
spring.datasource.username=postgres
spring.datasource.password=tu_contraseña
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3. Ejecuta la aplicación Spring Boot:
```bash
./mvnw spring-boot:run
```

La API estará disponible en http://localhost:8080

## Documentación de la API

### Autenticación

#### Registro de usuario
```
POST /auth/register
Content-Type: application/json

{
  "username": "username",
  "password": "password",
  "firstname": "firstName",
  "lastname": "lastName",
  "country": "country"
}
```
Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Inicio de sesión
```
POST /auth/login
Content-Type: application/json

{
  "username": "username",
  "password": "password"
}
```
Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Información del usuario
```
GET /auth/user-info
Headers:
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```
Response:
```json
{
  "username": "username",
  "firstname": "firstname",
  "lastname": "lastname",
  "country": "country",
  "role": "role"
}
```

### Gestión de Productos

#### Obtener todos los productos del usuario actual
```
GET /api/products/
Headers:
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### Obtener un producto específico
```
GET /api/products/{id}
Headers:
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### Crear un nuevo producto
```
POST /api/products/
Headers:
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
  "name": "Nombre del producto",
  "description": "Descripción del producto",
  "price": 99.99
}
```

#### Actualizar un producto existente
```
PUT /api/products/{id}
Headers:
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
  "name": "Nuevo nombre",
  "description": "Nueva descripción",
  "price": 129.99
}
```

#### Eliminar un producto
```
DELETE /api/products/{id}
Headers:
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## Seguridad y JWT

El sistema utiliza JSON Web Tokens (JWT) para gestionar la autenticación y autorización. Cada vez que un usuario se registra o inicia sesión, el servidor genera un token que debe incluirse en todas las solicitudes posteriores.

### Flujo de autenticación
1. El usuario se registra o inicia sesión.
2. El servidor valida las credenciales y genera un token JWT.
3. El cliente almacena este token.
4. Para cada solicitud a un endpoint protegido, el cliente incluye el token en el encabezado Authorization.
5. El servidor verifica la validez del token antes de procesar la solicitud.

La seguridad está implementada utilizando Spring Security junto con filtros personalizados para JWT. La configuración principal se encuentra en los paquetes `config` y `jwt`.

## Despliegue

La API está desplegada en [Render](https://render.com) y utiliza una base de datos PostgreSQL alojada en [Neon](https://neon.tech).

## Contacto

- Desarrollador: Jeronimo Rodriguez Sepulveda
- Email: jeronimoroseag@gmail.com
