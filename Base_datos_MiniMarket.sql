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