package com.cibertec.cyberstore.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Manejo centralizado de errores de negocio (por ejemplo, buscar un
 * producto o venta que no existe), para mostrar una pagina amigable en
 * lugar de una traza de error generica de Spring.
 */
@ControllerAdvice
public class ManejadorErroresGlobal {

    @ExceptionHandler(IllegalArgumentException.class)
    public String manejarNoEncontrado(IllegalArgumentException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error-negocio";
    }
}
