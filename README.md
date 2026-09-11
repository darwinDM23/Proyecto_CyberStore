# CiberStore — Proyecto de Lenguaje de Programación II

Este proyecto retoma el **Proyecto Final del ciclo anterior** (una tienda de
tecnología llamada *CiberStore*, hecha solo con HTML y CSS, sin backend ni
base de datos) y lo convierte en una **aplicación Java Web completa**,
aplicando todo lo que pide el sílabo del curso.

> Este repositorio es una **referencia / punto de partida**, generada para
> mostrar cómo podría quedar el proyecto aplicando el sílabo completo. Antes
> de presentarlo, revísalo con tu equipo, ajusta nombres, agrega tu propio
> sello (colores, textos, lógica adicional) y verifica que todo compile en
> tu máquina, ya que aquí no fue posible ejecutar `mvn` por no tener acceso
> a internet en este entorno.

## 1. Cómo se relaciona con el sílabo

| Unidad del sílabo | Dónde está en el proyecto |
|---|---|
| **U1 – Maven** | Todo el proyecto está estructurado como proyecto Maven (`pom.xml`, ciclo de vida `mvn clean install`, `mvn spring-boot:run`). |
| **U1 – JPA** | Paquete `model` (`Producto`, `Marca`, `Categoria`, `Cliente`, `Venta`, `DetalleVenta`) con anotaciones `@Entity`, `@ManyToOne`, `@OneToMany`, y paquete `repository` con interfaces `JpaRepository`. |
| **U2 – Git** | Ver sección "Flujo de trabajo con Git" más abajo. Se incluye `.gitignore` listo para Java/Maven. |
| **U2 – Spring / Spring Boot / Spring Data JPA / Hibernate** | Toda la app corre sobre Spring Boot; Spring Data JPA + Hibernate son el motor de persistencia (`spring.jpa.*` en `application.properties`); Spring MVC + Thymeleaf arman las vistas; `VentaService` usa `@Transactional` para demostrar manejo de transacciones. |
| **U3 – JasperReports** | `ReporteVentasService` + `reports/reporte_ventas.jrxml` generan un PDF de ventas descargable desde `/admin/ventas/reporte`. |
| **U3 – Despliegue en la nube (Azure)** | Ver sección "Despliegue en Azure" más abajo. |

## 2. Qué se conservó del proyecto original

- El nombre de la tienda: **CiberStore**.
- La estructura de navegación: Productos (Mouse, Monitores, Impresoras,
  Tablets), Marcas (Acer, HP, Lenovo, Asus, Apple...), Nosotros, Promociones
  y Ventas (ahora convertido en un carrito de compras real).
- La paleta de colores y el estilo visual (fondo oscuro, menú azul marino),
  reescritos en un único archivo `static/css/style.css`.
- Los productos, precios e imágenes de ejemplo, cargados automáticamente al
  iniciar la aplicación (`config/DataSeeder.java`) para que el sitio no se
  vea vacío la primera vez que lo levantes.

Lo que **antes no existía y ahora sí**: base de datos real, carrito de
compras funcional que descuenta stock, panel de administración con CRUD de
productos, historial de ventas y reporte en PDF.

## 3. Cómo ejecutarlo en tu máquina

Requisitos: **JDK 17** y **Maven 3.9+** (o usa el `mvnw` si lo agregas con
`mvn -N io.takari:maven:wrapper`).

```bash
cd cyberstore
mvn spring-boot:run
```

Al iniciar, Spring Boot crea automáticamente las tablas (Hibernate,
`ddl-auto=update`) en una base de datos **H2** guardada en `./data/`, y
`DataSeeder` inserta marcas, categorías y productos de ejemplo.

Abre `http://localhost:8080` para ver la tienda, y
`http://localhost:8080/admin/productos` para el panel de administración.
La consola de H2 (para ver las tablas directamente) está en
`http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:file:./data/cyberstore`).

Para generar el `.jar` ejecutable:

```bash
mvn clean package
java -jar target/cyberstore.jar
```

## 4. Flujo de trabajo con Git (Unidad 2)

Este proyecto ya incluye un `.gitignore` para Maven/Java. Un flujo sugerido
para el equipo, acorde a lo visto en la Unidad 2 (Git y GitHub básico):

```bash
git init
git add .
git commit -m "Estructura inicial del proyecto Spring Boot"

# Un repositorio remoto en GitHub por equipo:
git remote add origin https://github.com/tu-usuario/cyberstore.git
git branch -M main
git push -u origin main

# Trabajo por ramas (una por integrante o por funcionalidad):
git checkout -b feature/carrito-compras
# ... cambios ...
git add .
git commit -m "Implementa carrito de compras con sesion"
git push -u origin feature/carrito-compras
# luego, Pull Request hacia main en GitHub
```

Buenas prácticas a mencionar en la sustentación: ramas por funcionalidad,
mensajes de commit descriptivos, y uso de Pull Requests para revisar código
antes de fusionar a `main`.

## 5. Reporte con JasperReports (Unidad 3)

El archivo `src/main/resources/reports/reporte_ventas.jrxml` fue escrito a
mano siguiendo la estructura que genera Jasper Studio (bandas `title`,
`columnHeader`, `detail`, `summary`). En un entorno real, este mismo archivo
se puede abrir y editar visualmente con **Jasper Studio** (plugin de
Eclipse) para agregar logotipos, gráficos o agrupaciones.

`ReporteVentasService` compila el `.jrxml` en memoria con
`JasperCompileManager`, lo llena con los datos reales de la tabla `venta`
(a través de JPA/Hibernate) y lo exporta a PDF. Se descarga desde:

```
GET /admin/ventas/reporte
```

## 6. Despliegue en la nube — Azure (Unidad 3)

Pasos generales para desplegar en **Azure App Service** con
**Azure Database for MySQL**, como pide la Unidad 3:

1. Crear una cuenta educativa de Azure (Azure for Students).
2. Crear un recurso **Azure Database for MySQL Flexible Server** y crear la
   base de datos `cyberstore` (usa reglas de firewall para permitir el
   acceso desde App Service).
3. Crear un **Azure App Service** (plan Linux, Java 17).
4. En **Configuration > Application settings** del App Service, definir las
   variables de entorno que usa el perfil `mysql`:
   - `SPRING_PROFILES_ACTIVE=mysql`
   - `DB_URL=jdbc:mysql://<servidor>.mysql.database.azure.com:3306/cyberstore?useSSL=true&serverTimezone=UTC`
   - `DB_USERNAME=<usuario>`
   - `DB_PASSWORD=<contraseña>`
5. Generar el `.jar` (`mvn clean package`) y desplegarlo con el plugin de
   Maven para Azure, con la extensión de Azure para VS Code, o subiendo el
   `.jar` directamente desde el portal (Deployment Center).
6. Verificar en la URL pública del App Service (`https://<tu-app>.azurewebsites.net`)
   que el sitio cargue y que las ventas se guarden correctamente en MySQL.

El perfil `application-mysql.properties` ya está preparado para leer estas
variables de entorno en lugar de tener credenciales escritas en el código.

## 7. Estructura del proyecto

```
cyberstore/
├── pom.xml
├── src/main/java/com/cibertec/cyberstore/
│   ├── CyberstoreApplication.java
│   ├── model/          (entidades JPA)
│   ├── repository/     (interfaces Spring Data JPA)
│   ├── service/        (logica de negocio, transacciones, carrito)
│   ├── controller/      (paginas publicas y carrito)
│   ├── controller/admin/ (CRUD de productos, ventas y reporte)
│   ├── dto/             (objetos de transferencia: carrito y reporte)
│   └── config/          (datos de ejemplo, manejo de errores)
└── src/main/resources/
    ├── application.properties        (perfil H2 - desarrollo)
    ├── application-mysql.properties  (perfil MySQL/Azure - produccion)
    ├── reports/reporte_ventas.jrxml
    ├── templates/       (vistas Thymeleaf)
    └── static/          (css e imagenes, tomadas del proyecto original)
```

## 8. Pendientes sugeridos para el equipo

- Agregar autenticación (Spring Security) si el docente lo pide para el
  panel `/admin`.
- Agregar más categorías/productos reales de la empresa que elijan.
- Mejorar el `.jrxml` en Jasper Studio agregando el logo de la tienda.
- Escribir pruebas unitarias sobre `VentaService` (ya viene con
  `spring-boot-starter-test` en el `pom.xml`).
