# Parte técnica del planteamiento (Sección 1)

**Responsable:** Adrián Betrano

## Usuarios del sistema (roles)

| Rol | Qué puede hacer |
|---|---|
| Administrador | Gestionar las canchas (con imágenes), tarifas, los horarios, las reservas y consultar los reportes. |
| Cliente | Registrarse, ver el catálogo de canchas, consultar disponibilidad, reservar, pagar, cancelar y ver historial. |

## Módulos

| Módulo | Descripción |
|---|---|
| Usuarios y acceso (HU-01 a HU-05) | Registro, login, visitante, perfil y gestión de usuarios. |
| Canchas (HU-06 a HU-10) | CRUD de canchas con imagen en Firebase, catálogo y tipos de cancha. |
| Reservas del cliente (HU-11 a HU-15) | Disponibilidad, reserva y pago transaccional, historial y cancelación. |
| Administración y tarifas (HU-16 a HU-22) | Tarifas, horarios, bloqueos, reservas del día, reserva manual, reportes y pagos. |

## Tecnologías

Java + Spring Boot (patrón MVC), Thymeleaf, Bootstrap, MySQL con Spring Data JPA, Spring Security para roles, Firebase Storage para imágenes y GitHub para el control de versiones.

## Cumplimiento de las condiciones técnicas obligatorias

| Condición | Cómo se cumple |
|---|---|
| Aplicación web con arquitectura MVC | Spring Boot con capas domain, repository, service y controller, y vistas Thymeleaf. |
| Base de datos con datos dinámicos y transaccionales | Entidades usuario, rol, tipo_cancha, cancha, tarifa, horario, bloqueo, reserva y pago. Transacciones en HU-13 (pago + confirmación), HU-15 (cancelación + reembolso) y HU-20 (reserva manual). |
| Uso de imágenes, archivos y recursos | Fotos de las canchas en Firebase Storage (HU-06, HU-07, HU-09). |
| Repositorio en GitHub con evidencia de colaboración | Repositorio SC403-Canchas con los integrantes como colaboradores, README y trabajo por ramas (sección 8). |
