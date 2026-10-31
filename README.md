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
| `/docs` | Documento del Avance 1 (Word y PDF), historias de usuario, priorización, trazabilidad, modelo de datos y mapa de navegación |
| `/prototipo` | Enlace del prototipo navegable |
| `/src` | Código fuente de la aplicación Spring Boot |

## 🌿 Acuerdo de trabajo por ramas
- **main**: solo versiones estables (entregas de avances). Nadie trabaja directo aquí.
- **develop**: rama de integración y rama por defecto, donde se une todo lo terminado.
- **feature/HU-XX-descripcion**: una rama por historia de usuario, creada desde `develop`.
  - Ejemplo: `feature/HU-06-registrar-cancha`
- **Commits**: en español e iniciando con el ID de la historia.
  - Ejemplo: `HU-06: agregar formulario de cancha`
- **Pull requests**: al terminar una historia se abre un PR hacia `develop`, y otro integrante lo revisa antes de unirlo.
- ⚠️ Nunca subir la clave de Firebase (`.json`) ni la carpeta `target/`.

## ▶️ Requisitos y ejecución
**Requisitos:** JDK 21, Apache NetBeans (o cualquier IDE con Maven), MySQL 8 (local o Aiven) y MySQL Workbench.

1. Clonar el repositorio y abrir la carpeta raíz como proyecto Maven en NetBeans.
2. Ejecutar `src/main/resources/creaTablas.sql` en MySQL Workbench (con el usuario administrador). Crea la base `golazo`, el usuario `usuario_golazo` y datos de ejemplo.
3. Revisar en `application.properties` la conexión: Aiven (activa) o `localhost` (comentada).
4. Copiar la llave de Firebase (`.json`, la entrega el coordinador por privado) en `src/main/resources/firebase/`. **Nunca se sube al repositorio.**
5. *Clean and Build* y *Run*. Abrir http://localhost

**Estructura del código (igual a la vista en clase):** `domain` (entidades JPA), `repository` (JpaRepository), `service` (lógica y transacciones), `controller` (rutas MVC) y vistas Thymeleaf + Bootstrap en `templates/<módulo>/listado.html` y `fragmentos.html`.

## 🎯 Avance 1
- 📄 Documento completo: [`Avance1_Grupo04_Golazo.pdf`](docs/Avance1_Grupo04_Golazo.pdf)
- 🗺️ Mapa de navegación: [`mapa-navegacion.png`](docs/mapa-navegacion.png)
- 🎨 Prototipo (Google AI Studio): [ver prototipo](https://aistudio.google.com/apps/36699e65-be67-4da2-9940-cc51c2cc64d7?showAssistant=true&showPreview=true)
- 🎥 Video: [ver video](https://drive.google.com/file/d/1mnFoYZFZl3YMKgqFThyYioPjD9qmOUIK/view?usp=sharing)

## 🚧 Avance 2 – Estado de las historias
| Turno | Módulo | Responsable | Historias | Estado |
|---|---|---|---|---|
| 1 | Proyecto base y reservas y pagos | Integrante 1 (Carlos Adrián) | HU-11, HU-12, HU-13, HU-14, HU-15 | ✅ |
| 2 | Módulo A: canchas, tipos de cancha, catálogo e imágenes (Firebase) | Integrante 2 | HU-06, HU-07, HU-08, HU-09, HU-10, HU-16 | ⬜ Pendiente |
| 3 | Módulo B: horarios y tarifas | Integrante 3 | HU-17, HU-19, HU-20 | ⬜ Pendiente |
| 4 | Módulo C: usuarios, registro y reservas del día | Integrante 4 | HU-01, HU-05, HU-18, HU-21 | ⬜ Pendiente |
| Después | Inicio y cierre de sesión | Integrante 4 | HU-02, HU-03 | ⏳ cuando se vea Spring Security |

**Meta:** 18 de 22 historias (82 %).

> Mientras no se implemente el inicio de sesión (Spring Security), en *Reservar* y *Mis reservas* el cliente se elige de una lista.

## 📅 Entregas
| Entrega | Semana | Estado |
|---|---|---|
| Avance 1 – Historias de usuario y prototipo | 5 | ✅ Entregado |
| Avance 2 – Implementación del 50% | 9 | 🟡 En progreso |
| Entrega final y defensa | 15 | ⚪ Pendiente |
