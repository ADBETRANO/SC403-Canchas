# ⚽ Golazo – Sistema de alquiler de canchas de fútbol

**Proyecto Grupo 04 – SC-403 Desarrollo de Aplicaciones Web y Patrones**
Universidad Fidélitas · III Cuatrimestre 2026 · Profesor: Wilberth Molina Pérez

## 📌 Descripción
Aplicación web para que complejos deportivos de fútbol 5, fútbol 7 y fútbol 11 gestionen sus canchas, horarios y reservas en línea.
Los clientes pueden ver las canchas disponibles, reservar y pagar (tarjeta, SINPE Móvil o efectivo); el administrador gestiona canchas, tipos de cancha, horarios, tarifas, reservas, pagos y el resumen del día.

## 👥 Integrantes
| Nombre | Rol |
|---|---|
| Carlos Adrián Betrano Valverde | Coordinador |
| Daniel Antonio Murillo Zeledón | Analista de requerimientos |
| Emmanuel Alfaro Aguilar | QA y documentación |
| Juan Diego Murillo Ruiz | Diseñador UI/UX |

## 🛠️ Tecnologías
- Java + Spring Boot (patrón MVC)
- Thymeleaf + Bootstrap
- MySQL + Spring Data JPA
- Spring Security (roles ADMIN y CLIENTE)
- Firebase Storage (imágenes de canchas)

## 📁 Estructura del repositorio
| Carpeta | Contenido |
|---|---|
| `/docs` | Documento del Avance 1 (Word y PDF), planteamiento, historias de usuario (HU-01 a HU-22), priorización, trazabilidad, modelo de datos y mapa de navegación |
| `/prototipo` | Enlace del prototipo y capturas de las 14 pantallas (P-01 a P-14) |

## 🌿 Acuerdo de trabajo por ramas
- **main**: solo versiones estables (entregas de avances). Nadie trabaja directo aquí.
- **develop**: rama de integración y rama por defecto, donde se une todo lo terminado.
- **feature/HU-XX-descripcion**: una rama por historia de usuario, creada desde `develop`.
  - Ejemplo: `feature/HU-06-registrar-cancha`
- **Commits**: en español e iniciando con el ID de la historia.
  - Ejemplo: `HU-06: agregar formulario de cancha`
- **Pull requests**: al terminar una historia se abre un PR hacia `develop`, y otro integrante lo revisa antes de unirlo.
- ⚠️ Nunca subir la clave de Firebase (`.json`) ni la carpeta `target/`.

## 🎯 Avance 1
- 📄 Documento completo: [`Avance1_Grupo04_Golazo.pdf`](docs/Avance1_Grupo04_Golazo.pdf)
- 📁 Documentación por partes: [`/docs`](docs/)
- 🗺️ Mapa de navegación: [`mapa-navegacion.png`](docs/mapa-navegacion.png)
- 🎨 Prototipo (Google AI Studio): [ver prototipo](https://aistudio.google.com/apps/36699e65-be67-4da2-9940-cc51c2cc64d7?showAssistant=true&showPreview=true)
- 🎥 Video de presentación: [ver video](https://drive.google.com/file/d/1mnFoYZFZl3YMKgqFThyYioPjD9qmOUIK/view?usp=sharing)

## 📅 Entregas
| Entrega | Semana | Estado |
|---|---|---|
| Avance 1 – Historias de usuario y prototipo | 5 | ✅ Entregado |
| Avance 2 – Implementación del 50% | 9 | ⚪ Pendiente |
| Entrega final y defensa | 15 | ⚪ Pendiente |
