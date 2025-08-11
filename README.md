# BollitosPOS

## Descripción
**BollitosPOS** es un sistema de punto de venta diseñado para panaderías, que permite gestionar ventas, inventarios, clientes y reportes de forma sencilla y rápida. El sistema está desarrollado en **Java EE** usando **Spring MVC**, con persistencia en **MySQL** y despliegue en **GlassFish**.

---

## Problema Identificado
En la mayoría de panaderías pequeñas y medianas, el control de inventarios y ventas se hace de forma manual, lo que provoca:
- Errores en el conteo de productos.
- Pérdida de información histórica.
- Tiempos de atención más largos al cliente.
- Falta de reportes confiables para toma de decisiones.

---

## Solución
Implementar un sistema centralizado que:
- Automatice ventas y control de inventarios.
- Genere reportes diarios y mensuales.
- Gestione usuarios y permisos según rol.
- Mantenga la información en una base de datos segura.

---

## Arquitectura
- **Cliente**: Navegador Web (HTML, CSS, JS)
- **Servidor de Aplicaciones**: GlassFish
- **Servidor Web**: Incluido en GlassFish
- **Base de Datos**: MySQL
- **Framework**: Spring MVC + JPA/Hibernate
- **Servicios**: RESTful API

mermaid
graph TD
A[Usuario Final] -->|HTTP/HTTPS| B[GlassFish Server]
B -->|JPA/Hibernate| C[Base de Datos MySQL]
B -->|REST API| D[Clientes externos]
Tabla de Contenidos
Descripción

Problema Identificado

Solución

Arquitectura

Requerimientos

Instalación

Configuración

Uso

Contribución

Roadmap

🛠 Requerimientos
Servidores
GlassFish 6.x

MySQL 8.x

Servidor web (opcional si se usa GlassFish)

Paquetes Adicionales
Spring MVC

Hibernate/JPA

MySQL Connector/J

Lombok (opcional para reducir código boilerplate)

Versión de Java
Java 17 (LTS)

⚙ Instalación
1️Instalar Ambiente de Desarrollo
bash
Copiar
Editar
# Clonar el repositorio
git clone https://github.com/usuario/BollitosPOS.git
cd BollitosPOS

# Instalar dependencias (si se usa Maven)
mvn clean install
2️Configurar Base de Datos
sql
Copiar
Editar
CREATE DATABASE bollitospos;
CREATE USER 'bollitosuser'@'localhost' IDENTIFIED BY 'contraseña';
GRANT ALL PRIVILEGES ON bollitospos.* TO 'bollitosuser'@'localhost';
Actualizar application.properties con credenciales de la BD.

3️Ejecutar Pruebas Manualmente
bash
Copiar
Editar
mvn test
4Implementar en Producción
Local: Desplegar WAR en GlassFish.

Heroku: Empaquetar como .jar y desplegar usando heroku deploy:jar.

Docker (opcional): Crear imagen con GlassFish y MySQL integrados.

Configuración
Archivos de Configuración
src/main/resources/application.properties

persistence.xml

Configuración de GlassFish para data source.

Configuración de Requerimientos
Credenciales de BD.

Puerto del servidor (default 8080).

Variables de entorno para despliegue en la nube.

Uso
Para Usuario Final
Manual en PDF: Manual Usuario Final

Para Administrador
Manual en PDF: Manual Administrador

Contribución
Clonar repositorio:

bash
Copiar
Editar
git clone https://github.com/usuario/BollitosPOS.git
Crear nueva rama:

bash
Copiar
Editar
git checkout -b feature/nueva-funcionalidad
Subir cambios y hacer Pull Request:

bash
Copiar
Editar
git push origin feature/nueva-funcionalidad
Esperar revisión y merge.

Roadmap
 Integrar pagos en línea.

 Reportes gráficos con estadísticas.

 Módulo de pedidos en línea.

 Soporte multi-sucursal.

yaml
Copiar
Editar
