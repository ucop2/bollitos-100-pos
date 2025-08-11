-- SQL script to create database and sample data for Bollitos POS
CREATE DATABASE IF NOT EXISTS bollitos_pos;
USE bollitos_pos;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    rol ENUM('ADMIN','VENDEDOR') NOT NULL
);

CREATE TABLE IF NOT EXISTS productos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE IF NOT EXISTS ventas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS detalle_ventas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_venta INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_venta) REFERENCES ventas(id),
    FOREIGN KEY (id_producto) REFERENCES productos(id)
);

-- sample admin user (password: admin)
INSERT IGNORE INTO usuarios (nombre, usuario, contrasena, rol)
VALUES ('Administrador','admin','admin','ADMIN');

-- sample products
INSERT IGNORE INTO productos (nombre, precio, stock) VALUES
('Bollito de mantequilla', 10.00, 100),
('Concha', 12.00, 80),
('Baguette', 20.00, 50);
