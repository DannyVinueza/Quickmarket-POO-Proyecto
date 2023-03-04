CREATE DATABASE quickmarket;
USE quickmarket;

  CREATE TABLE `quickmarket`.`roles` (
  `idroles` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombres` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`idroles`));

  
INSERT INTO `quickmarket`.`roles` (`nombres`) VALUES ('Administrador');
INSERT INTO `quickmarket`.`roles` (`nombres`) VALUES ('Cajero');

CREATE UNIQUE INDEX roles_pk ON `roles` (`idroles`);

CREATE TABLE `quickmarket`.`usuarios`(
  `idusuario` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `idroles` INT UNSIGNED NOT NULL,
  `usuario` VARCHAR(25) NOT NULL,
  `contrasenia` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`idusuario`),
  CONSTRAINT FK_USUARIOS_TIENE_ROLES FOREIGN KEY (`idroles`) REFERENCES `roles` (`idroles`)
);

CREATE UNIQUE INDEX USUARIOS_PK ON USUARIOS (`idusuario`);

CREATE INDEX TIENE_FK ON USUARIOS (`idroles`);

INSERT INTO `quickmarket`.`usuarios` (`idroles`, `usuario`, `contrasenia`) VALUES ('1', 'dannyV', '1234');
INSERT INTO `quickmarket`.`usuarios` (`idroles`, `usuario`, `contrasenia`) VALUES ('2', 'danielQ', '1111');
INSERT INTO `quickmarket`.`usuarios` (`idroles`, `usuario`, `contrasenia`) VALUES ('1', 'nestorC', '1211');
INSERT INTO `quickmarket`.`usuarios` (`idroles`, `usuario`, `contrasenia`) VALUES ('2', 'joseP', '1122');

-- Crear tabla de clientes
CREATE TABLE Clientes (
    id_cliente INT(11) NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    correo_elec VARCHAR(100),
    PRIMARY KEY (id_cliente)
);

-- Crear tabla de productos
CREATE TABLE Productos (
    id_producto INT(11) NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT,
    precio_unit DECIMAL(10,2) NOT NULL,
    cantidad_stock INT(11) NOT NULL,
    PRIMARY KEY (id_producto)
);

-- Crear tabla de facturas
CREATE TABLE Facturas (
    id_factura INT(11) NOT NULL AUTO_INCREMENT,
    id_cliente INT(11) NOT NULL,
    fecha_emision DATE NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id_factura),
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);

-- Crear tabla de detalle de facturas
CREATE TABLE Detalle_Factura (
    id_detalle INT(11) NOT NULL AUTO_INCREMENT,
    id_factura INT(11) NOT NULL,
    id_producto INT(11) NOT NULL,
    cantidad INT(11) NOT NULL,
    precio_unit DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id_detalle),
    FOREIGN KEY (id_factura) REFERENCES Facturas(id_factura),
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);

-- Insertar datos en tabla Clientes
INSERT INTO Clientes (nombre, direccion, telefono, correo_elec)
VALUES
    ('Juan Perez', 'Calle Falsa 123', '555-1234', 'juanperez@email.com'),
    ('María García', 'Avenida Real 456', '555-5678', 'mariagarcia@email.com'),
    ('Pedro Martínez', 'Calle del Sol 789', '555-9012', 'pedromartinez@email.com'),
    ('Ana López', 'Calle del Río 246', '555-3456', 'anlopez@email.com'),
    ('José Hernández', 'Avenida del Bosque 135', '555-7890', 'josehernandez@email.com');

-- Insertar datos en tabla Productos
INSERT INTO Productos (nombre, descripcion, precio_unit, cantidad_stock)
VALUES
    ('Televisor', 'Pantalla plana de 55 pulgadas', 1500.00, 10),
    ('Reproductor de Blu-ray', 'Reproduce discos de alta definición', 250.00, 20),
    ('Equipo de sonido', 'Incluye dos altavoces y un amplificador', 800.00, 5),
    ('Laptop', 'Procesador Intel Core i7, 16 GB de RAM, disco SSD de 512 GB', 2000.00, 7),
    ('Tablet', 'Pantalla táctil de 10 pulgadas, 64 GB de almacenamiento', 500.00, 15);

-- Insertar datos en tabla Facturas
INSERT INTO Facturas (id_cliente, fecha_emision, total)
VALUES
    (1, '2022-02-28', 3500.00),
    (2, '2022-03-01', 1200.00),
    (3, '2022-03-02', 600.00),
    (4, '2022-03-03', 1500.00),
    (5, '2022-03-04', 900.00);

-- Insertar datos en tabla Detalle_Factura
INSERT INTO Detalle_Factura (id_factura, id_producto, cantidad, precio_unit)
VALUES
    (1, 1, 2, 1500.00),
    (1, 2, 1, 250.00),
    (2, 4, 1, 2000.00),
    (2, 5, 2, 500.00),
    (3, 2, 3, 250.00),
    (4, 1, 1, 1500.00),
    (4, 3, 1, 800.00),
    (5, 5, 1, 500.00),
    (5, 2, 2, 250.00);