package com.cibertec.cyberstore.controller.admin;

import com.cibertec.cyberstore.model.Cliente;
import com.cibertec.cyberstore.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/clientes")
public class ClienteAdminController {

    private final ClienteRepository clienteRepository;

    public ClienteAdminController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteRepository.findAll());
        return "admin/clientes/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "admin/clientes/formulario";
    }

    @GetMapping("/{id}/editar")
    public String formularioEditar(@PathVariable Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        model.addAttribute("cliente", cliente);
        return "admin/clientes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente,
                          BindingResult resultado) {

        if (resultado.hasErrors()) {
            return "admin/clientes/formulario";
        }

        clienteRepository.save(cliente);
        return "redirect:/admin/clientes";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        clienteRepository.deleteById(id);
        return "redirect:/admin/clientes";
    }
}