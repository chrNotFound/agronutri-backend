# AgroNutri · Backend (microservicio)

API REST en Spring Boot 4 (Java 21) con MySQL para la app móvil AgroNutri. Autentica usuarios con token Bearer, aplica permisos por perfil y guarda predios, análisis, programas y fotos foliares.

**Integrantes:** `[CHRISTIAN ANDRES TRONCOSO NUNEZ]` · `[RAFAEL ELIAS GALLARDO NORIEL]` · `[STEPHAN ENRIQUE VELASQUEZ ARCE]` · `[FABIAN IGNACIO VALENCIA PIZARRO]`&nbsp;

## Cómo ejecutarlo

1. Instala Java 21 y MySQL 8 (en marcha en `localhost:3306`).
2. Por defecto usa usuario `root` sin contraseña y crea la base `agronutri` sola. Para cambiarlo:
   `AGRONUTRI_DB_USER`, `AGRONUTRI_DB_PASSWORD` (variables de entorno).
3. Arranca: `./mvnw spring-boot:run` (Windows: `mvnw.cmd spring-boot:run`).
4. Comprueba: `http://localhost:8080/api/ping` → `{"estado":"ok"}`.

Primer administrador (se crea solo si no existe ninguno): `admin@agronutri.cl` / `Admin12345`.
Cámbialo con `AGRONUTRI_ADMIN_EMAIL` y `AGRONUTRI_ADMIN_PASSWORD` **antes del primer arranque**.

Otras propiedades (`application.properties`): `agronutri.carpeta-fotos` (carpeta `uploads/`), `agronutri.sesion.dias` (30) y `agronutri.recuperacion.mostrar-codigo` (true = modo demo: el código de recuperación vuelve en la respuesta porque no hay servidor de correo).

Para probar desde un teléfono real, el PC y el teléfono deben estar en la misma red y el puerto 8080 abierto en el firewall.

## Perfiles y permisos

| Perfil | Ver predios | Crear predios | Agregar análisis / fotos / programas | Verificar y validar | Borrar | Usuarios |
|---|---|---|---|---|---|---|
| AGRICULTOR | los suyos | sí | en los suyos | no | los suyos | no |
| ASESOR | todos | no | sí | sí | no | no |
| SUPERVISOR | todos | no | no | no | no | no |
| ADMIN | todos | sí | sí | sí | todo | sí |

El servidor fija el dueño del predio y el estado de verificación/validación: lo que un cliente envíe en esos campos sin permiso se ignora.

## Endpoints

Todos devuelven JSON y los errores salen como `{"error":"mensaje"}`. Salvo los marcados, requieren `Authorization: Bearer <token>`.

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/ping` | Estado (sin token) |
| POST | `/api/auth/registro` | Crea cuenta (solo AGRICULTOR o ASESOR) y abre sesión (sin token) |
| POST | `/api/auth/login` | Abre sesión (sin token) |
| POST | `/api/auth/logout` | Cierra la sesión |
| POST | `/api/auth/recuperar` · `/api/auth/restablecer` | Código de 6 dígitos y nueva contraseña (sin token) |
| GET / PUT | `/api/auth/perfil` | Ver / modificar nombre y contraseña |
| GET | `/api/usuarios` | Lista usuarios (ADMIN) |
| PUT | `/api/usuarios/{id}/rol` | Cambia el perfil (ADMIN) |
| DELETE | `/api/usuarios/{id}` | Elimina un usuario (ADMIN) |
| GET / POST | `/api/planes` | Lista / crea predios |
| GET / PUT / DELETE | `/api/planes/{id}` | Ver / modificar / borrar (borra también sus hijos) |
| GET | `/api/planes/{id}/analisis`, `/programas`, `/fotos` | Lo que cuelga de un predio |
| POST | `/api/analisis` · `/api/programas` | Crear |
| GET / PUT / DELETE | `/api/analisis/{id}` · `/api/programas/{id}` | Leer / modificar / borrar |
| POST | `/api/fotos` | Subir JPEG (multipart: `archivo`, `id`, `planId`, `nota`, `creadoEn`); upsert por `id` |
| GET / DELETE | `/api/fotos/{id}/archivo` · `/api/fotos/{id}` | Descargar / borrar |
| POST | `/api/sync` | Recibe `{planes, analisis, programas}` (upsert por id, en una transacción) y responde `{planes, analisis, programas, fotos}` visibles para el usuario |
| GET | `/api/sync` | Solo la descarga anterior |

Las escrituras son *upsert* por `id` (UUID generado en el teléfono) y los borrados son idempotentes, así un envío repetido no duplica ni falla.

## Estructura

```
controller/  Auth, Plan, Analisis, Programa, Foto, Sync, Usuario
service/     AuthService, DatosService (reglas por rol), Validaciones
model/       Usuario, Sesion, Plan, Analisis, Programa, FotoFoliar (JPA)
repository/  Spring Data JPA
security/    Passwords (PBKDF2, tokens), Roles
web/         AuthInterceptor (Bearer), ManejadorErrores
```

Las contraseñas se guardan con PBKDF2-HMAC-SHA256 y salt; de los tokens y códigos de recuperación solo se guarda el hash SHA-256.

## Pruebas

`./mvnw test` (JUnit 5 + Mockito; el contexto de Spring usa H2 en memoria). Cubren permisos por rol, validaciones, autenticación y contraseñas.
