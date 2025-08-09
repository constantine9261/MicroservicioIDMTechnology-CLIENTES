
# Proyecto Microservicios: Clientes y Cuentas

## Descripción

Este proyecto implementa dos microservicios independientes para la gestión de clientes y cuentas bancarias. Cada microservicio maneja su propia base de datos y lógica de negocio para asegurar escalabilidad, mantenimiento y separación clara de responsabilidades.

---

## Arquitectura

- **Microservicio Clientes**: Gestiona la información de los clientes, incluyendo creación, consulta y validación de datos personales.
- **Microservicio Cuentas**: Gestiona las cuentas bancarias asociadas a los clientes, permitiendo crear cuentas, consultar y actualizar saldos.

---

## Estructura de la base de datos

### Tabla `clientes`

```sql
CREATE TABLE clientes (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    dni VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL
);
```

### Tabla `cuentas`

```sql
CREATE TABLE cuentas (
    id SERIAL PRIMARY KEY,
    numero_cuenta VARCHAR(50) NOT NULL UNIQUE,
    tipo_cuenta VARCHAR(20) NOT NULL,
    saldo NUMERIC(15, 2) NOT NULL DEFAULT 0,
    cliente_id BIGINT NOT NULL,
    fecha_creacion TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);
```

---

## Tecnologías utilizadas

- **Java 17+**: Lenguaje principal para el desarrollo.
- **Spring Boot 3.x**: Framework para crear aplicaciones y microservicios RESTful.
- **Spring WebFlux**: Para programación reactiva y manejo de APIs no bloqueantes.
- **Spring Data R2DBC**: Integración reactiva con bases de datos relacionales.
- **PostgreSQL**: Base de datos relacional utilizada.
- **Lombok**: Para reducir código repetitivo con anotaciones.
- **JUnit 5 y Mockito**: Para pruebas unitarias y mocks.
- **OpenAPI (Swagger)**: Para documentación y pruebas de las APIs REST.
- **Docker (opcional)**: Para contenerización de microservicios.
- **Maven**: Gestión de dependencias y compilación.

---

## Endpoints principales

### Microservicio Clientes

- `POST /clientes` — Crear nuevo cliente.
- `GET /clientes/{id}` — Obtener cliente por ID.

### Microservicio Cuentas

- `POST /cuentas` — Crear nueva cuenta asociada a un cliente.
- `GET /cuentas/cliente/{clienteId}` — Obtener cuentas de un cliente.

---

## Documentación API

La documentación Swagger/OpenAPI de ambas APIs se encuentra en la carpeta `src/main/resources/openapi` de cada microservicio.

Para visualizar la documentación, ejecutar la aplicación y acceder a la URL Swagger UI, generalmente:

```
http://localhost:{puerto}/swagger-ui/index.html
```

---

## Cómo ejecutar

1. Clonar repositorio.
2. Configurar la conexión a PostgreSQL en `application.yml`.
3. Ejecutar los microservicios Clientes y Cuentas por separado.
4. Probar los endpoints usando Swagger UI o Postman.

---

## Contacto

Desarrollado por Jean Franco Constantino Chihuan Omonte.
