# Parte técnica del planteamiento (Sección 1)

**Proyecto:** Golazo – Sistema de alquiler de canchas de fútbol
**Responsable:** Carlos Adrián Betrano Valverde

## Usuarios del sistema (roles)

| Rol | Qué puede hacer |
|---|---|
| Administrador | Gestionar las canchas (con imágenes), los tipos de cancha, las tarifas, los horarios, los usuarios y las reservas, y consultar los reportes. |
| Cliente | Registrarse, ver el catálogo de canchas, consultar disponibilidad, reservar, pagar, cancelar y ver su historial. |

## Módulos

| Módulo | Descripción |
|---|---|
| Usuarios y acceso (HU-01 a HU-05) | Registro, inicio y cierre de sesión, perfil y gestión de usuarios. |
| Canchas (HU-06 a HU-10) | CRUD de canchas, desactivación, catálogo con filtro por tipo e imágenes en Firebase. |
| Reservas del cliente (HU-11 a HU-15) | Disponibilidad, reserva, pago transaccional, historial y cancelación. |
| Administración y tarifas (HU-16 a HU-22) | Tipos de cancha, horarios, resumen del día, tarifas, consulta de reservas y reportes. |

## Tecnologías

Java + Spring Boot (patrón MVC), Thymeleaf, Bootstrap, MySQL con Spring Data JPA, Spring Security para roles, Firebase Storage para imágenes y GitHub para el control de versiones.

## Cumplimiento de las condiciones técnicas obligatorias

| Condición | Cómo se cumple |
|---|---|
| Aplicación web con arquitectura MVC | Spring Boot con capas domain, repository, service y controller, y vistas Thymeleaf. |
| Base de datos con datos dinámicos y transaccionales | Entidades Usuario, TipoCancha, Cancha, ImagenCancha, Horario, Tarifa, Reserva y Pago (ver `modelo-datos.md`). Transacciones en HU-13 (pago + confirmación de la reserva) y HU-15 (cancelación + reembolso). |
| Uso de imágenes, archivos y recursos | Imágenes de las canchas en Firebase Storage (HU-09 y HU-10). |
| Repositorio en GitHub con evidencia de colaboración | Repositorio SC403-Canchas con los integrantes como colaboradores, README y trabajo por ramas (sección 8). |
