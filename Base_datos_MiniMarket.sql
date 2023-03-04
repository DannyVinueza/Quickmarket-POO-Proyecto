CREATE DATABASE quickmarket;
USE quickmarket;

CREATE TABLE quickmarket (
  id_tienda INT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  direccion VARCHAR(100) NOT NULL,
  telefono VARCHAR(20) NOT NULL
);

INSERT INTO quickmarket (id_tienda, nombre, direccion, telefono)
VALUES
(123, 'Quick Market', 'quick.market@example.com', '0995511259');

CREATE TABLE `quickmarket`.`roles` (
  `idroles` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nombres` VARCHAR(30) NOT NULL,
PRIMARY KEY (`idroles`));
CREATE UNIQUE INDEX roles_pk ON roles (idroles);

INSERT INTO `quickmarket`.`roles` (`nombres`) VALUES ('Administrador');
INSERT INTO `quickmarket`.`roles` (`nombres`) VALUES ('Cajero');


CREATE TABLE usuarios (
  idusuario INT AUTO_INCREMENT PRIMARY KEY,
  idroles INT UNSIGNED NOT NULL,
  nombre_completo VARCHAR(50) NOT NULL,
  `usuario` VARCHAR(25) NOT NULL,
  `contrasenia` VARCHAR(25) NOT NULL,
  CONSTRAINT FK_USUARIOS_TIENE_ROLES FOREIGN KEY (idroles) REFERENCES roles (idroles)
);

CREATE UNIQUE INDEX USUARIOS_PK ON USUARIOS (`idusuario`);
CREATE INDEX TIENE_FK ON USUARIOS (`idroles`);

CREATE TABLE Clientes (
  id_cliente INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  direccion VARCHAR(100) NOT NULL,
  correo VARCHAR(100) NOT NULL,
  telefono VARCHAR(20) NOT NULL,
  cedula VARCHAR(20) NOT NULL
);

CREATE TABLE Productos (
  id_producto INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(50) NOT NULL,
  descripcion VARCHAR(100),
  precio DECIMAL(10,2) NOT NULL,
  stock INT NOT NULL
);

CREATE TABLE FACTURAS (
  id_venta INT AUTO_INCREMENT PRIMARY KEY,
  idusuario INT NOT NULL,
  id_tienda INT NOT NULL,
  id_cliente INT NOT NULL,
  id_producto INT NOT NULL,
  fecha_venta DATE NOT NULL,
  forma_pago VARCHAR(50) NOT NULL,
  importe_total DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (id_tienda) REFERENCES quickmarket(id_tienda),
  FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente),
  FOREIGN KEY (id_producto) REFERENCES Productos(id_producto),
  FOREIGN KEY (idusuario) REFERENCES usuarios(idusuario)
);

CREATE TABLE DetalleFacturas (
  id_detalle INT PRIMARY KEY AUTO_INCREMENT,
  id_venta INT NOT NULL,
  id_producto INT NOT NULL,
  cantidad INT NOT NULL,
  precio DECIMAL(10,2) NOT NULL,
  subtotal DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (id_venta) REFERENCES FACTURAS(id_venta),
  FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);

-- Insertar datos en la tabla Usuarios
INSERT INTO usuarios (idroles, nombre_completo, usuario, contrasenia)
VALUES
('1', 'Danny Vinueza', 'dannyV', '1234'),
('2', 'Daniel Quishpe', 'danielQ', '1111'),
('1', 'Néstor Chumania', 'nestorC', '1211'),
('2', 'José Panchi', 'joseP', '1122');

-- Insertar datos en la tabla Clientes
INSERT INTO Clientes (nombre, direccion, correo, telefono, cedula)
VALUES
('María López', 'Calle Falsa 123', 'maria.lopez@example.com', '0995511259', '1751358422'),
('Luis González', 'Avenida Real 456', 'luis.gonzalez@example.com','0998511559', '1752354522'),
('Ana Sánchez', 'Calle del Sol 789', 'ana.sanchez@example.com', '0995211459','1751454622'),
('Juan Martínez', 'Calle del Río 246', 'juan.martinez@example.com', '0995113259','1755355422'),
('Miguel Pérez', 'Avenida del Bosque 135', 'miguel.perez@example.com', '0945153659','1755354622');

-- Insertar datos en la tabla Productos
INSERT INTO Productos (nombre, descripcion, precio, stock)
VALUES
('Leche', 'Leche descremada de 1 litro', 1.99, 100),
('Queso', 'Queso fresco de vaca', 2.99, 50),
('Pan', 'Pan de molde integral', 0.99, 200),
('Café', 'Café molido de tueste medio', 3.49, 75),
('Galletas', 'Galletas de avena con pasas', 1.49, 150);

-- Insertar datos en la tabla Facturas
INSERT INTO Facturas( idusuario, id_tienda, id_cliente, id_producto, fecha_venta, forma_pago, importe_total)
VALUES
(1, '123', 2,'1', '2023-03-02 10:30:00','Efectivo', 52.50),
(2,'123', 4,'2', '2023-03-03 11:15:00','Cheque', 51.97),
(1,'123', 2, '2','2023-03-03 12:30:00','Transferencia', 21.97),
(2,'123', 3,'4', '2023-03-03 14:45:00','Tarjeta de Crédito', 35.48),
(1,'123', 1,'5', '2023-03-04 09:00:00','Tarjeta de Débito', 12.47);

-- Insertar datos en la tabla DetalleFacturas
INSERT INTO DetalleFacturas (id_venta, id_producto, cantidad, precio, subtotal)
VALUES
(1, 1, 2, 1.99, 3.98),
(1, 2, 1, 2.99, 2.99),
(2, 3, 3, 0.99, 2.97),
(2, 4, 2, 3.49, 6.98),
(3, 5, 1, 1.49, 1.49);