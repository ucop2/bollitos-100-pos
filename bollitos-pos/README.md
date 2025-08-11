# Bollitos POS - Versión básica

Proyecto Java (Maven) mínimo para gestionar productos y ventas con interfaz Swing.

## Requisitos
- Java 11+
- Maven
- MySQL (instancia local)
- NetBeans (puede abrir proyectos Maven directamente)

## Configuración rápida
1. Crear la base de datos:
   - Ejecuta `sql/create_db.sql` en MySQL Workbench para crear la base de datos y datos de ejemplo.
2. Edita la conexión (si es necesario):
   - Archivo: `src/main/java/com/bollitos/modelo/Conexion.java`
   - Ajusta `USER` y `PASSWORD` según tu MySQL.
3. Abrir proyecto:
   - Abre NetBeans -> `File > Open Project` -> selecciona la carpeta del proyecto.
4. Ejecutar:
   - Ejecuta el proyecto (Run). Usuario de prueba: `admin` / `admin`.

## Estructura
- `src/main/java/com/bollitos` - código fuente
- `sql/create_db.sql` - script SQL para crear tablas y datos de ejemplo
- `pom.xml` - configuración Maven (incluye conector MySQL)

## Notas
- Las contraseñas se almacenan en texto plano en esta versión simple (solo para aprendizaje). En producción cifra las contraseñas.
- Para simplificar se usa Swing y JDBC directo (sin frameworks).
