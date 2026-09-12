package com.cibertec.cyberstore.controller.admin;

import com.cibertec.cyberstore.model.Proveedor;
import com.cibertec.cyberstore.repository.ProveedorRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/proveedores")
public class ProveedorAdminController {

    private final ProveedorRepository proveedorRepository;

    public ProveedorAdminController(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("proveedores", proveedorRepository.findAll());
        return "admin/proveedores/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "admin/proveedores/formulario";
    }

    @GetMapping("/{id}/editar")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado"));

        model.addAttribute("proveedor", proveedor);
        return "admin/proveedores/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("proveedor") Proveedor proveedor,
                          BindingResult resultado) {

        if (resultado.hasErrors()) {
            return "admin/proveedores/formulario";
        }

        proveedorRepository.save(proveedor);
        return "redirect:/admin/proveedores";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        proveedorRepository.deleteById(id);
        return "redirect:/admin/proveedores";
    }
}