package com.cibertec.cyberstore.controller;

import com.cibertec.cyberstore.service.MarcaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MarcaController {

    private final MarcaService marcaService;

    public MarcaController(MarcaService marcaService) {
        this.marcaService = marcaService;
    }

    @GetMapping("/marcas")
    public String listarMarcas(Model model) {
        model.addAttribute("marcas", marcaService.listarTodas());
        return "marcas/lista";
    }
}
