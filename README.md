# Proyecto: Gestor de Libros y Autores

## Descripción

Este proyecto es un sistema para buscar, gestionar y almacenar información de libros y sus autores. Utiliza una API externa para buscar libros basados en un título ingresado por el usuario, y permite guardar los resultados en una base de datos. Además, incluye funcionalidades para consultar autores vivos en un intervalo de tiempo y realizar operaciones CRUD sobre los libros y autores almacenados.

---

## Tecnologías Utilizadas

### Backend

- **Java 17**
- **Spring Boot** (JPA, REST)

### Base de Datos

- **PostgreSQL**

### Otros

- **Maven** como gestor de dependencias.
- **IntelliJ IDEA** como entorno de desarrollo.

---

## Funcionalidades

### 1. Búsqueda de Libros

- Busca libros en una API externa ingresando el título del libro.
- Muestra una lista de resultados con un contador.

### 2. Selección y Almacenamiento

- Permite al usuario seleccionar uno o más libros de los resultados obtenidos.
- Guarda los libros seleccionados en la base de datos.

### 3. Consulta de Autores Vivos

- Realiza una búsqueda en la base de datos para obtener los autores vivos durante un intervalo de tiempo determinado (basado en el año de nacimiento y muerte).

### 4. CRUD de Libros

- Crear, leer, actualizar y eliminar registros de libros.

### 5. Integración con la Base de Datos

- Relación entre libros y autores en PostgreSQL.

---

## Estructura del Proyecto

### Directorio principal

```
/src
  /main
    /java
      /com.alura.literalura
        /modelo
        /repository
        /service
    /resources
  /test
```

### Paquetes principales

- **modelo**: Clases representativas de la base de datos (Entidades).
- **repository**: Interfaces que extienden JpaRepository para la comunicación con la base de datos.
- **service**: Lógica de negocio para la gestión de libros y autores.

---

## Requisitos de Instalación

### Prerrequisitos

1. Tener instalado **Java 17**.
2. Tener **PostgreSQL** configurado.
3. Instalar **Maven**.

### Configuración de la Base de Datos

1. Crear una base de datos en PostgreSQL.
2. Configurar el archivo `application.properties` en `/resources`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nombre_base_datos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```

### Compilación y Ejecución

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/gestor-libros.git
   ```
2. Navegar al directorio del proyecto:
   ```bash
   cd gestor-libros
   ```
3. Compilar y ejecutar:
   ```bash
   mvn spring-boot:run
   ```

---

## Endpoints Principales

### Libros

- **GET /libros**: Obtener todos los libros.
- **POST /libros**: Crear un nuevo libro.
- **DELETE /libros/{id}**: Eliminar un libro por ID.

### Autores

- **GET /autores/vivos**: Obtener autores vivos en un intervalo de tiempo.
- **POST /autores**: Crear un nuevo autor.

---

## Ejemplo de Uso

### Búsqueda y Selección de Libros

1. Ejecutar el programa y buscar un libro por título.
2. Seleccionar los libros deseados ingresando los números correspondientes separados por comas (por ejemplo, `1,3,5`).
3. Confirmar la selección para guardar en la base de datos.

### Consulta de Autores Vivos

1. Ingresar un intervalo de tiempo (años de nacimiento y muerte).
2. Ver los resultados de autores vivos en ese periodo.

---

## Contribución

Si deseas contribuir:

1. Haz un fork del repositorio.
2. Crea una rama nueva para tu funcionalidad o corrección de errores.
   ```bash
   git checkout -b nueva-funcionalidad
   ```
3. Realiza tus cambios y haz un commit.
   ```bash
   git commit -m "Agrega nueva funcionalidad"
   ```
4. Envía un Pull Request.

---

## Autor

Martin Alcaraz\
[LinkedIn] [https://www.linkedin.com/in/martin-alcaraz-desbackendjunior/](https://www.linkedin.com/in/martin-alcaraz-desbackendjunior/)







