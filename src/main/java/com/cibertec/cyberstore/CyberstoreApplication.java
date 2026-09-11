package com.cibertec.cyberstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicacion CiberStore.
 *
 * Este proyecto retoma el "Proyecto Final" del ciclo anterior (una tienda de
 * tecnologia hecha solo con HTML/CSS, sin backend ni base de datos) y lo
 * transforma en una aplicacion Java Web completa, aplicando todo lo visto en
 * el silabo de Lenguaje de Programacion II:
 *
 *   Unidad 1: Maven (este mismo proyecto) + JPA para persistencia de datos.
 *   Unidad 2: Git para control de versiones + Spring Boot / Spring MVC / Spring Data JPA.
 *   Unidad 3: JasperReports para reportes + despliegue en la nube (Azure).
 */
@SpringBootApplication
public class CyberstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CyberstoreApplication.class, args);
    }
}
