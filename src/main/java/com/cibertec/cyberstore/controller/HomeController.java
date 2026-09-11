package com.cibertec.cyberstore.controller;

import com.cibertec.cyberstore.repository.CategoriaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CategoriaRepository categoriaRepository;

    public HomeController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "index";
    }
}
