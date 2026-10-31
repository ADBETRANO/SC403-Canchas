/*
  Script de creación de base de datos para Golazo
  Sistema de alquiler de canchas de fútbol - Grupo 04 SC-403
  Crea el esquema, las tablas, los usuarios y carga datos de ejemplo.
*/
-- Sección de administración (ejecutar una vez en un entorno de desarrollo)
drop database if exists golazo;
drop user if exists usuario_golazo;

-- Creación del esquema
CREATE database golazo
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

-- Creación del usuario de la aplicación
create user 'usuario_golazo'@'%' identified by 'Golaz0_Clave.';

-- Asignación de permisos
grant select, insert, update, delete on golazo.* to 'usuario_golazo'@'%';
flush privileges;

use golazo;

-- --- Sección de Creación de Tablas ---

-- Tabla de tipos de cancha (Fútbol 5, Fútbol 7, Fútbol 11)
create table tipo_cancha (
  id_tipo_cancha INT NOT NULL AUTO_INCREMENT,
  descripcion VARCHAR(30) NOT NULL,
  activo boolean NOT NULL DEFAULT TRUE,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_tipo_cancha),
  unique (descripcion))
  ENGINE = InnoDB;

-- Tabla de canchas
create table cancha (
  id_cancha INT NOT NULL AUTO_INCREMENT,
  id_tipo_cancha INT NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  descripcion text,
  superficie VARCHAR(30),
  capacidad INT NOT NULL CHECK (capacidad > 0),
  ruta_imagen varchar(1024),
  activo boolean NOT NULL DEFAULT TRUE,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_cancha),
  unique (nombre),
  foreign key fk_cancha_tipo (id_tipo_cancha) references tipo_cancha(id_tipo_cancha))
  ENGINE = InnoDB;

-- Tabla de imágenes adicionales de cada cancha (galería en Firebase)
create table imagen_cancha (
  id_imagen INT NOT NULL AUTO_INCREMENT,
  id_cancha INT NOT NULL,
  ruta_imagen varchar(1024) NOT NULL,
  fecha_subida TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_imagen),
  foreign key fk_imagen_cancha (id_cancha) references cancha(id_cancha))
  ENGINE = InnoDB;

-- Tabla de horarios de atención ("Lunes a viernes" o "Fin de semana")
create table horario (
  id_horario INT NOT NULL AUTO_INCREMENT,
  id_cancha INT NOT NULL,
  dia VARCHAR(20) NOT NULL,
  hora_apertura TIME NOT NULL,
  hora_cierre TIME NOT NULL,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CHECK (hora_cierre > hora_apertura),
  PRIMARY KEY (id_horario),
  unique (id_cancha, dia),
  foreign key fk_horario_cancha (id_cancha) references cancha(id_cancha))
  ENGINE = InnoDB;

-- Tabla de tarifas ("Diurna" antes de las 18:00, "Nocturna" desde las 18:00)
create table tarifa (
  id_tarifa INT NOT NULL AUTO_INCREMENT,
  id_cancha INT NOT NULL,
  tipo_tarifa VARCHAR(10) NOT NULL,
  precio_hora DECIMAL(12,2) NOT NULL CHECK (precio_hora >= 0),
  vigente boolean NOT NULL DEFAULT TRUE,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_tarifa),
  foreign key fk_tarifa_cancha (id_cancha) references cancha(id_cancha))
  ENGINE = InnoDB;

-- Tabla de usuarios
CREATE TABLE usuario (
  id_usuario INT NOT NULL AUTO_INCREMENT,
  username varchar(30) NOT NULL UNIQUE,
  password varchar(512) NOT NULL,
  nombre VARCHAR(20) NOT NULL,
  apellidos VARCHAR(30) NOT NULL,
  correo VARCHAR(75) NOT NULL UNIQUE,
  telefono VARCHAR(25) NULL,
  activo boolean NOT NULL DEFAULT TRUE,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_usuario),
  index ndx_username (username))
  ENGINE = InnoDB;

-- Tabla de roles
create table rol (
  id_rol INT NOT NULL AUTO_INCREMENT,
  rol varchar(25) NOT NULL unique,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  primary key (id_rol))
  ENGINE = InnoDB;

-- Tabla de relación entre usuarios y roles
create table usuario_rol (
  id_usuario int not null,
  id_rol INT NOT NULL,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_usuario,id_rol),
  foreign key fk_usuarioRol_usuario (id_usuario) references usuario(id_usuario),
  foreign key fk_usuarioRol_rol (id_rol) references rol(id_rol))
  ENGINE = InnoDB;

-- Tabla de reservas ("Pendiente", "Confirmada" o "Cancelada")
create table reserva (
  id_reserva INT NOT NULL AUTO_INCREMENT,
  id_usuario INT NOT NULL,
  id_cancha INT NOT NULL,
  fecha DATE NOT NULL,
  hora_inicio TIME NOT NULL,
  hora_fin TIME NOT NULL,
  monto_total DECIMAL(12,2) NOT NULL CHECK (monto_total >= 0),
  estado VARCHAR(15) NOT NULL DEFAULT 'Pendiente',
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_reserva),
  index ndx_reserva_cancha_fecha (id_cancha, fecha),
  foreign key fk_reserva_usuario (id_usuario) references usuario(id_usuario),
  foreign key fk_reserva_cancha (id_cancha) references cancha(id_cancha))
  ENGINE = InnoDB;

-- Tabla de pagos ("Tarjeta", "SINPE Móvil" o "Efectivo")
-- estado: "Pendiente", "Pagado", "Rechazado" o "Reembolsado"
create table pago (
  id_pago INT NOT NULL AUTO_INCREMENT,
  id_reserva INT NOT NULL,
  monto DECIMAL(12,2) NOT NULL CHECK (monto >= 0),
  metodo VARCHAR(20) NOT NULL,
  estado VARCHAR(15) NOT NULL,
  fecha_pago TIMESTAMP NULL,
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id_pago),
  unique (id_reserva),
  foreign key fk_pago_reserva (id_reserva) references reserva(id_reserva))
  ENGINE = InnoDB;

-- --- Sección de Inserción de Datos ---

-- Tipos de cancha
INSERT INTO tipo_cancha (descripcion, activo) VALUES
('Fútbol 5', true),
('Fútbol 7', true),
('Fútbol 11', true);

-- Canchas (las imágenes se suben desde la aplicación a Firebase)
INSERT INTO cancha (id_tipo_cancha, nombre, descripcion, superficie, capacidad, ruta_imagen, activo) VALUES
(1, 'Cancha Bombonera', 'Cancha techada de fútbol 5 con iluminación LED.', 'Sintética', 10, NULL, true),
(1, 'Cancha Monumental', 'Cancha al aire libre de fútbol 5 con graderías.', 'Sintética', 10, NULL, true),
(2, 'Cancha Saprissa', 'Cancha de fútbol 7 con camerinos y parqueo.', 'Sintética', 14, NULL, true),
(3, 'Cancha Nacional', 'Cancha de fútbol 11 de césped natural.', 'Natural', 22, NULL, true);

-- Horarios de atención
INSERT INTO horario (id_cancha, dia, hora_apertura, hora_cierre) VALUES
(1, 'Lunes a viernes', '08:00', '22:00'),
(1, 'Fin de semana',   '07:00', '23:00'),
(2, 'Lunes a viernes', '08:00', '22:00'),
(2, 'Fin de semana',   '07:00', '23:00'),
(3, 'Lunes a viernes', '09:00', '22:00'),
(3, 'Fin de semana',   '08:00', '22:00'),
(4, 'Fin de semana',   '08:00', '18:00');

-- Tarifas por hora (colones)
INSERT INTO tarifa (id_cancha, tipo_tarifa, precio_hora, vigente) VALUES
(1, 'Diurna', 15000, true), (1, 'Nocturna', 20000, true),
(2, 'Diurna', 14000, true), (2, 'Nocturna', 18000, true),
(3, 'Diurna', 25000, true), (3, 'Nocturna', 32000, true),
(4, 'Diurna', 60000, true), (4, 'Nocturna', 75000, true);

-- Roles del sistema
insert into rol (rol) values ('ADMIN'), ('CLIENTE');

-- Usuarios de prueba (la contraseña se encriptará con BCrypt cuando se vea Spring Security)
INSERT INTO usuario (username, password, nombre, apellidos, correo, telefono, activo) VALUES
('admin',  '123', 'Admin',  'Golazo',        'admin@golazo.com',  '2222-0000', true),
('carlos', '123', 'Carlos', 'Rojas Mora',    'carlos@correo.com', '8888-1111', true),
('maria',  '123', 'María',  'Solano Vargas', 'maria@correo.com',  '8888-2222', true);

-- Asignación de roles a usuarios
insert into usuario_rol (id_usuario, id_rol) values
(1,1), (1,2), (2,2), (3,2);

-- Reservas de ejemplo
INSERT INTO reserva (id_usuario, id_cancha, fecha, hora_inicio, hora_fin, monto_total, estado) VALUES
(2, 1, CURDATE() + INTERVAL 2 DAY, '19:00', '20:00', 20000, 'Confirmada'),
(3, 3, CURDATE() + INTERVAL 3 DAY, '10:00', '11:00', 25000, 'Pendiente');

-- Pagos de ejemplo
INSERT INTO pago (id_reserva, monto, metodo, estado, fecha_pago) VALUES
(1, 20000, 'SINPE Móvil', 'Pagado', NOW()),
(2, 25000, 'Efectivo', 'Pendiente', NULL);
