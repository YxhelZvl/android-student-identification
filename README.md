# UMSNH-ID

Aplicacion movil para Android orientada a la identificacion digital de estudiantes y a la integracion de servicios universitarios.

> Este repositorio contiene codigo fuente sin datos personales del reporte de servicio social. No incluye credenciales, bases de datos reales ni artefactos compilados.

## Funcionalidades

- Inicio de sesion mediante API REST y tokens JWT.
- Registro y consulta de informacion estudiantil.
- Generacion y escaneo de codigos QR para identificacion digital.
- Consulta de inscripciones y ciclo escolar.
- Consulta de biblioteca y solicitud de prestamos.
- Persistencia local de la sesion mediante DataStore.
- Interfaz Android construida con Jetpack Compose y Material 3.

## Arquitectura

El proyecto esta dividido en dos aplicaciones:

- `UMSNHID/`: aplicacion movil Android en Kotlin.
- `umsnh_id/`: backend REST en Java con Spring Boot y acceso a MySQL.

La aplicacion Android consume el backend mediante Ktor. El backend expone, entre otros, los siguientes recursos:

- `POST /api/auth/login`
- `POST /api/usuarios/estudiante`
- `GET /api/usuarios/{id}`
- `GET /api/inscripciones/usuario/{id}`

## Base de datos

El backend utiliza MySQL para almacenar la informacion academica y los recursos universitarios. El modelo contempla las siguientes entidades:

- Usuarios y roles.
- Estudiantes, carreras e inscripciones.
- Ciclos escolares.
- Bibliotecas, laboratorios y catalogo de recursos.
- Ejemplares y prestamos.

El archivo `umsnh.sql` disponible en la carpeta original es un volcado de desarrollo y contiene registros, fechas y posibles datos identificables. Por seguridad, esta excluido mediante `.gitignore` y no debe publicarse tal como esta. Para distribuir la estructura de la base de datos, genera un archivo `database/schema.sql` que contenga solamente `CREATE TABLE`, indices, relaciones y datos genericos sin usuarios reales, correos, matriculas, contrasenas ni fechas personales.

## Tecnologias

### Android

- Kotlin
- Jetpack Compose y Material 3
- Navigation Compose
- Ktor Client
- Koin
- DataStore
- CameraX y ML Kit Barcode Scanning
- ZXing

### Backend

- Java 21
- Spring Boot 3.4
- Spring Security
- JWT
- Spring JDBC
- MySQL
- Gradle

## Requisitos

- JDK 21 para el backend.
- Android Studio reciente y Android SDK 35 para la app.
- MySQL 8 o compatible.
- Un dispositivo Android o emulador con acceso al backend.

## Configuracion del backend

No se deben guardar contrasenas ni secretos en el repositorio. El backend lee la configuracion desde variables de entorno:

```bash
export DB_URL='jdbc:mysql://localhost:3306/umsnh'
export DB_USERNAME='umsnh'
export DB_PASSWORD='cambia-esta-contrasena'
export APP_USER='admin'
export APP_PASSWORD='cambia-esta-contrasena'
export JWT_SECRET='genera-un-secreto-largo-y-unico'
```

Importa el esquema en una base de datos local usando un script SQL propio y previamente sanitizado. No uses un volcado que contenga usuarios reales.

Para ejecutar el backend:

```bash
cd umsnh_id
./gradlew bootRun
```

El servicio queda disponible, por defecto, en `http://localhost:8081`.

## Configuracion de Android

La URL de desarrollo esta definida en `UMSNHID/app/src/main/java/com/yxhelzvl/umsnh_id/core/utils/Constants.kt` como `http://10.0.2.2:8081/api`, que permite al emulador Android acceder al backend local.

Para un dispositivo fisico o un servidor de pruebas, cambia esa URL por la direccion correspondiente. No incluyas en el repositorio direcciones internas, credenciales ni configuraciones de produccion.

Para compilar la app:

```bash
cd UMSNHID
./gradlew assembleDebug
```

El APK generado se crea dentro de `UMSNHID/app/build/outputs/apk/` y esta excluido del repositorio.

## Pruebas

Backend:

```bash
cd umsnh_id
./gradlew test
```

Android:

```bash
cd UMSNHID
./gradlew test
```

## Que se sube y que no

Se recomienda subir:

- `README.md` y `.gitignore`.
- Los directorios `UMSNHID/` y `umsnh_id/` sin sus carpetas generadas.
- Codigo fuente, configuraciones de Gradle, manifiestos y pruebas.

No se debe subir:

- `Reporte_final_servicio.pdf`, porque contiene nombre, matricula, correos y otros datos personales.
- `umsnh.sql` si contiene usuarios, correos, matriculas, contrasenas o datos de prueba identificables. Sustituyelo por un esquema sanitizado si necesitas compartirlo.
- `app-umsnh.apk`, archivos `.jar`, `.zip` y carpetas `build/`, porque son artefactos generados o copias del codigo fuente.
- Contraseñas, secretos JWT, tokens, claves de firma Android, archivos `.env`, `local.properties` y configuraciones de produccion.
- Logs, reportes de pruebas generados, capturas con datos reales y archivos de IDE.

## Publicar en GitHub

Primero crea un repositorio vacio en GitHub, sin agregar README, licencia ni `.gitignore`. Despues ejecuta desde esta carpeta:

```bash
git init
git add README.md .gitignore UMSNHID umsnh_id
git status
```

Revisa que `git status` no muestre el PDF, SQL, APK, JAR, ZIP, claves ni archivos de configuracion local. Si todo es correcto:

```bash
git commit -m "Documenta proyecto UMSNH-ID"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/TU_REPOSITORIO.git
git push -u origin main
```

Si una credencial se subio anteriormente, no basta con borrarla del archivo: revoca o cambia la credencial y limpia el historial antes de hacer publico el repositorio.

## Estado del proyecto

Proyecto academico de servicio social. La configuracion de despliegue, las credenciales y los datos de produccion deben definirse fuera del repositorio antes de utilizarlo en un entorno real.
