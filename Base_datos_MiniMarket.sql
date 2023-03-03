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

CREATE TABLE PROVEEDORES (
   ID_PROV INT NOT NULL AUTO_INCREMENT,
   RUCPROV NUMERIC(13, 0) NOT NULL,
   NOM_PROV VARCHAR(25) NOT NULL,
   DIR_PROV VARCHAR(50) NOT NULL,
   TELF_PROV NUMERIC(10) NOT NULL,
   PRIMARY KEY (ID_PROV)
);

CREATE TABLE STOCK (
   ID_STOCK INT NOT NULL AUTO_INCREMENT,
   CANT_PROD INT NOT NULL,
   FEcha_Ing datetime NOT NULL,
   Fech_Sal datetime NOT NULL,
   PRIMARY KEY (ID_STOCK)
);


CREATE TABLE CLIENTES (
   ID_CLI INT NOT NULL AUTO_INCREMENT,
   CED_CLI NUMERIC(10, 0) NOT NULL,
   NOM_CLI VARCHAR(25) NOT NULL,
   APELL_CLI VARCHAR(25) NOT NULL,
   TELF_CLI NUMERIC(10) NOT NULL,
   DIRCLI TEXT NOT NULL,
   MAILCLI VARCHAR(30) NOT NULL,
   PRIMARY KEY (ID_CLI)
);

CREATE TABLE CAB_FACTURA (
   ID_FACT INT NOT NULL AUTO_INCREMENT,
   ID_CLI INT NOT NULL,
   FECHAEMI datetime NOT NULL,	
   TotalFac DECIMAL(6, 2) NULL,
   Descuentos DECIMAL(6, 2) NULL,
   IvaFac DECIMAL(6, 2) NULL,
   TotalPagar DECIMAL(6, 2) NULL,
   PRIMARY KEY (ID_FACT),
   FOREIGN KEY (ID_CLI) REFERENCES CLIENTES(ID_CLI)
);



CREATE TABLE PRODUCTOS (
   ID_PROD INT NOT NULL AUTO_INCREMENT,
   NOM_PROD VARCHAR(25) NOT NULL,
   ID_STOCK INT NOT NULL,
   VALCOMPRA DECIMAL(7, 2) NOT NULL,
   VALVENTA DECIMAL(7, 2) NOT NULL,
   ID_PROV INT NOT NULL,
   PRIMARY KEY (ID_PROD),
   FOREIGN KEY (ID_STOCK) REFERENCES STOCK(ID_STOCK),
   FOREIGN KEY (ID_PROV) REFERENCES PROVEEDORES(ID_PROV)
);

CREATE TABLE DETFACTURA (
   ID_DETALLE INT NOT NULL AUTO_INCREMENT,
   ID_FACT INT NOT NULL,
   ID_PROD INT NOT NULL,
   CANTIDAD INT NOT NULL,
   VALOR_UNIT DECIMAL(10, 2) NOT NULL,
   TotalDet DECIMAL(6, 2) NULL,
   PRIMARY KEY (ID_DETALLE),
   FOREIGN KEY (ID_FACT) REFERENCES CAB_FACTURA(ID_FACT),
   FOREIGN KEY (ID_PROD) REFERENCES PRODUCTOS(ID_PROD)
);

ALTER TABLE CAB_FACTURA
ADD FOREIGN KEY (ID_CLI) REFERENCES CLIENTES(ID_CLI);

ALTER TABLE DETFACTURA
ADD FOREIGN KEY (ID_FACT) REFERENCES CAB_FACTURA(ID_FACT),
ADD FOREIGN KEY (ID_PROD) REFERENCES PRODUCTOS(ID_PROD);

ALTER TABLE PRODUCTOS
ADD FOREIGN KEY (ID_STOCK) REFERENCES STOCK(ID_STOCK),
ADD FOREIGN KEY (ID_PROV) REFERENCES PROVEEDORES(ID_PROV);



INSERT INTO PROVEEDORES (RUCPROV, NOM_PROV, DIR_PROV, TELF_PROV) VALUES
(1234567890001, 'Proveedor 1', 'Dirección 1', 9876543210),
(2234567890002, 'Proveedor 2', 'Dirección 2', 9876543211),
(3234567890003, 'Proveedor 3', 'Dirección 3', 9876543212);



INSERT INTO STOCK (CANT_PROD, FEcha_Ing, Fech_Sal) VALUES
(50, '2023-02-28 14:30:00', '2023-03-01 09:15:00'),
(100, '2023-03-01 15:45:00', '2023-03-02 12:30:00'),
(75, '2023-03-02 14:30:00', '2023-03-03 10:15:00');



INSERT INTO CLIENTES (CED_CLI, NOM_CLI, APELL_CLI, TELF_CLI, DIRCLI, MAILCLI) VALUES
(1234567890, 'Cliente 1', 'Apellido 1', 9876543210, 'Dirección 1', 'cliente1@mail.com'),
(2234567891, 'Cliente 2', 'Apellido 2', 9876543211, 'Dirección 2', 'cliente2@mail.com'),
(3234567892, 'Cliente 3', 'Apellido 3', 9876543212, 'Dirección 3', 'cliente3@mail.com');



INSERT INTO CAB_FACTURA (ID_CLI, FECHAEMI, TotalFac, Descuentos, IvaFac, TotalPagar) VALUES
(1, '2023-03-01 10:30:00', 500.00, 50.00, 80.00, 530.00),
(2, '2023-03-02 11:45:00', 750.00, 0.00, 120.00, 870.00),
(3, '2023-03-03 09:30:00', 250.00, 25.00, 40.00, 265.00);



INSERT INTO PRODUCTOS (NOM_PROD, ID_STOCK, VALCOMPRA, VALVENTA, ID_PROV) VALUES
('Producto 1', 1, 10.00, 15.00, 1),
('Producto 2', 2, 20.00, 30.00, 2),
('Producto 3', 3, 5.00, 8.00, 3);



INSERT INTO DETFACTURA (ID_FACT, ID_PROD, CANTIDAD, VALOR_UNIT, TotalDet) VALUES
(1, 1, 10, 15.00, 150.00),
(1, 2, 5, 30.00, 150.00),
(2, 2, 10, 30.00, 300.00);