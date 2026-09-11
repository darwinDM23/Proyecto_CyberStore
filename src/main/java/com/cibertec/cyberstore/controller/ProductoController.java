package com.cibertec.cyberstore.controller;

import com.cibertec.cyberstore.repository.CategoriaRepository;
import com.cibertec.cyberstore.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaRepository categoriaRepository;

    public ProductoController(ProductoService productoService, CategoriaRepository categoriaRepository) {
        this.productoService = productoService;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/productos/{slug}")
    public String listarPorCategoria(@PathVariable String slug, Model model) {
        model.addAttribute("productos", productoService.listarPorCategoria(slug));
        model.addAttribute("categoriaActual", categoriaRepository.findBySlug(slug).orElse(null));
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "productos/lista";
    }
}
