  # ⚽ CanchaYa – Sistema de alquiler de canchas de fútbol

**Proyecto Grupo 04 – SC-403 Desarrollo de Aplicaciones Web y Patrones**
Universidad Fidélitas · III Cuatrimestre 2026

## 📌 Descripción
Aplicación web para que complejos deportivos de fútbol 5 y fútbol 7 gestionen sus canchas, horarios y reservas en línea.
Los clientes pueden ver las canchas disponibles, reservar y pagar; el administrador gestiona canchas, tarifas, reservas y reportes.

## 👥 Integrantes
| Nombre | Rol |
|---|---|
| Carlos Adrián Betrano Valverde | Coordinador – repositorio y video |
| Daniel Murillo Zeledon | Analista de requerimientos |
| Juan Diego Murillo Ruiz | QA y documentación |

## 🛠️ Tecnologías
- Java + Spring Boot (patrón MVC)
- Thymeleaf + Bootstrap
- MySQL + Spring Data JPA
- Spring Security (roles ADMIN y CLIENTE)
- Firebase Storage (imágenes de canchas)

## 📁 Estructura del repositorio
| Carpeta | Contenido |
|---|---|
| `/docs` | Documentación: historias de usuario, modelo de datos y mapa de navegación |
| `/prototipo` | Capturas y enlace del prototipo en Figma |

## 🌿 Acuerdo de trabajo por ramas
- **main**: solo versiones estables (entregas de avances). Nadie trabaja directo aquí.
- **develop**: rama de integración, donde se une todo lo terminado.
- **feature/HU-XX-descripcion**: una rama por historia de usuario, creada desde `develop`.
    - Ejemplo: `feature/HU-06-crud-canchas`
- **Commits**: en español e iniciando con el ID de la historia.
   - Ejemplo: `HU-06: agregar formulario de cancha`
- **Pull requests**: al terminar una historia se abre un PR hacia `develop`, y otro integrante lo revisa antes de unirlo.
- ⚠️ Nunca subir la clave de Firebase (`.json`) ni la carpeta `target/`.

## 📅 Entregas
| Entrega | Semana | Estado |
|---|---|---|
| Avance 1 – Historias de usuario y prototipo | 5 | 🟡 En progreso |
| Avance 2 – Implementación del 50% | 9 | ⚪ Pendiente |
| Entrega final y defensa | 15 | ⚪ Pendiente |
