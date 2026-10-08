# Trazabilidad de historias de usuario y pantallas

**Proyecto:** Golazo – Sistema de alquiler de canchas de fútbol
**Responsable:** Emmanuel Alfaro Aguilar

## Objetivo

La trazabilidad permite relacionar cada historia de usuario con la pantalla del sistema donde se implementará su funcionalidad. Esta relación facilita verificar que las funcionalidades definidas en el backlog estén representadas dentro del prototipo y del mapa de navegación del sistema.

## Matriz de trazabilidad por historia

| Historia | Funcionalidad | Pantalla asociada |
|---|---|---|
| HU-01 | Registrarse en el sistema | P-02 Registro |
| HU-02 | Iniciar sesión | P-01 Login |
| HU-03 | Cerrar sesión | P-01 Login (menú) |
| HU-04 | Consultar y modificar perfil | P-13 Mi perfil |
| HU-05 | Gestionar usuarios | P-12 Usuarios |
| HU-06 | Registrar cancha | P-08 Gestión de canchas |
| HU-07 | Editar cancha | P-08 Gestión de canchas |
| HU-08 | Desactivar cancha | P-08 Gestión de canchas |
| HU-09 | Ver y filtrar catálogo de canchas | P-03 Catálogo de canchas |
| HU-10 | Subir imágenes de una cancha | P-08 Gestión de canchas |
| HU-11 | Ver disponibilidad de una cancha | P-04 Detalle de cancha y disponibilidad |
| HU-12 | Reservar una cancha | P-05 Confirmar reserva |
| HU-13 | Pagar la reserva | P-06 Pago y comprobante |
| HU-14 | Ver historial de reservas | P-07 Mis reservas |
| HU-15 | Cancelar una reserva | P-07 Mis reservas |
| HU-16 | Gestionar tipos de cancha | P-14 Tipos de cancha |
| HU-17 | Definir horario de apertura y cierre | P-09 Tarifas y horarios |
| HU-18 | Ver resumen de reservas del día | P-10 Reservas del día |
| HU-19 | Registrar tarifa de cancha | P-09 Tarifas y horarios |
| HU-20 | Modificar tarifa de cancha | P-09 Tarifas y horarios |
| HU-21 | Consultar reservas | P-10 Reservas del día |
| HU-22 | Consultar reportes | P-11 Reportes |

## Matriz de trazabilidad por pantalla

| Pantalla | Nombre | Historias que cubre | Responsable |
|---|---|---|---|
| P-01 | Login | HU-02, HU-03 | Juan Diego Murillo Ruiz |
| P-02 | Registro | HU-01 | Juan Diego Murillo Ruiz |
| P-03 | Catálogo de canchas | HU-09 | Daniel Murillo Zeledón |
| P-04 | Detalle de cancha y disponibilidad | HU-11 | Carlos Adrián Betrano Valverde |
| P-05 | Confirmar reserva | HU-12 | Carlos Adrián Betrano Valverde |
| P-06 | Pago y comprobante | HU-13 | Carlos Adrián Betrano Valverde |
| P-07 | Mis reservas | HU-14, HU-15 | Carlos Adrián Betrano Valverde |
| P-08 | Gestión de canchas (admin) | HU-06, HU-07, HU-08, HU-10 | Daniel Murillo Zeledón |
| P-09 | Tarifas y horarios (admin) | HU-17, HU-19, HU-20 | Emmanuel Alfaro Aguilar |
| P-10 | Reservas del día (admin) | HU-18, HU-21 | Emmanuel Alfaro Aguilar |
| P-11 | Reportes (admin) | HU-22 | Emmanuel Alfaro Aguilar |
| P-12 | Usuarios (admin) | HU-05 | Juan Diego Murillo Ruiz |
| P-13 | Mi perfil | HU-04 | Juan Diego Murillo Ruiz |
| P-14 | Tipos de cancha (admin) | HU-16 | Emmanuel Alfaro Aguilar |

## Consideraciones

- Las 22 historias tienen al menos una pantalla asociada y las 14 pantallas del prototipo tienen al menos una historia.
- Los códigos y nombres de pantalla coinciden con el mapa de navegación (`mapa-navegacion.png`) y con el prototipo en Google AI Studio.
- Las historias HU-16, HU-17 y HU-18 se redefinieron para no duplicar las historias de gestión de canchas (HU-06 a HU-08) y para cubrir las pantallas P-14, P-09 y P-10.
