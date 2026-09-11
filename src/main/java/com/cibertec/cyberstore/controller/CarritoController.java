package com.cibertec.cyberstore.controller;

import com.cibertec.cyberstore.model.Venta;
import com.cibertec.cyberstore.service.Carrito;
import com.cibertec.cyberstore.service.ProductoService;
import com.cibertec.cyberstore.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final Carrito carrito;
    private final ProductoService productoService;
    private final VentaService ventaService;

    public CarritoController(Carrito carrito, ProductoService productoService, VentaService ventaService) {
        this.carrito = carrito;
        this.productoService = productoService;
        this.ventaService = ventaService;
    }

    @GetMapping
    public String verCarrito(Model model) {
        model.addAttribute("carrito", carrito);
        return "carrito/ver";
    }

    @PostMapping("/agregar/{productoId}")
    public String agregarProducto(@PathVariable Long productoId,
                                   @RequestParam(defaultValue = "1") int cantidad,
                                   @RequestHeader(value = "Referer", required = false) String referer) {
        carrito.agregarProducto(productoService.buscarPorId(productoId), cantidad);
        return "redirect:" + (referer != null ? referer : "/carrito");
    }

    @PostMapping("/actualizar/{productoId}")
    public String actualizarCantidad(@PathVariable Long productoId, @RequestParam int cantidad) {
        carrito.actualizarCantidad(productoId, cantidad);
        return "redirect:/carrito";
    }

    @PostMapping("/eliminar/{productoId}")
    public String eliminarProducto(@PathVariable Long productoId) {
        carrito.eliminarProducto(productoId);
        return "redirect:/carrito";
    }

    @PostMapping("/confirmar")
    public String confirmarCompra(@RequestParam String nombre,
                                   @RequestParam String email,
                                   @RequestParam(required = false) String telefono,
                                   @RequestParam(required = false) String direccion,
                                   Model model) {
        try {
            Venta venta = ventaService.confirmarCompra(carrito, nombre, email, telefono, direccion);
            model.addAttribute("venta", venta);
            return "carrito/confirmacion";
        } catch (RuntimeException ex) {
            model.addAttribute("carrito", carrito);
            model.addAttribute("error", ex.getMessage());
            return "carrito/ver";
        }
    }
}
