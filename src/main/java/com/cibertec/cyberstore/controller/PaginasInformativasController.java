package com.cibertec.cyberstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador simple para las paginas "Nosotros" y "Promociones" del sitio
 * original, que no requieren datos dinamicos de la base de datos.
 */
@Controller
public class PaginasInformativasController {

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

    @GetMapping("/promociones")
    public String promociones() {
        return "promociones";
    }
}
