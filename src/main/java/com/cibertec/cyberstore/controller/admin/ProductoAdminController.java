package com.cibertec.cyberstore.controller.admin;

import com.cibertec.cyberstore.model.Categoria;
import com.cibertec.cyberstore.model.Marca;
import com.cibertec.cyberstore.model.Producto;
import com.cibertec.cyberstore.repository.CategoriaRepository;
import com.cibertec.cyberstore.repository.MarcaRepository;
import com.cibertec.cyberstore.service.ProductoService;
import com.cibertec.cyberstore.service.ReporteInventarioService;

import jakarta.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Panel de administracion para demostrar el ciclo CRUD completo
 * (crear, leer, actualizar, eliminar) sobre la entidad Producto
 * usando JPA/Hibernate a traves de ProductoService.
 *
 * Tambien permite generar el reporte de inventario en PDF
 * utilizando JasperReports.
 */
@Controller
@RequestMapping("/admin/productos")
public class ProductoAdminController {

    private final ProductoService productoService;
    private final MarcaRepository marcaRepository;
    private final CategoriaRepository categoriaRepository;
    private final ReporteInventarioService reporteInventarioService;

    public ProductoAdminController(ProductoService productoService,
                                    MarcaRepository marcaRepository,
                                    CategoriaRepository categoriaRepository,
                                    ReporteInventarioService reporteInventarioService) {

        this.productoService = productoService;
        this.marcaRepository = marcaRepository;
        this.categoriaRepository = categoriaRepository;
        this.reporteInventarioService = reporteInventarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        return "admin/productos/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        cargarListas(model);
        return "admin/productos/formulario";
    }

    @GetMapping("/{id}/editar")
    public String formularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoService.buscarPorId(id));
        cargarListas(model);
        return "admin/productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("producto") Producto producto,
                           BindingResult resultado,
                           @RequestParam(required = false) Long marcaId,
                           @RequestParam Long categoriaId,
                           Model model) {

        if (resultado.hasErrors()) {
            cargarListas(model);
            return "admin/productos/formulario";
        }

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Categoria no encontrada"));

        producto.setCategoria(categoria);

        if (marcaId != null) {

            Marca marca = marcaRepository.findById(marcaId)
                    .orElseThrow(() ->
                            new IllegalArgumentException("Marca no encontrada"));

            producto.setMarca(marca);

        } else {
            producto.setMarca(null);
        }

        productoService.guardar(producto);

        return "redirect:/admin/productos";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {

        productoService.eliminar(id);

        return "redirect:/admin/productos";
    }

    /**
     * Genera el reporte de inventario en formato PDF
     * utilizando JasperReports.
     */
    @GetMapping("/reporte")
    public ResponseEntity<byte[]> descargarReporteInventarioPdf() throws Exception {

        byte[] pdf = reporteInventarioService.generarReporteInventarioPdf(
                productoService.listarTodos()
        );

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_PDF);

        headers.setContentDispositionFormData(
                "inline",
                "reporte_inventario.pdf"
        );

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }

    private void cargarListas(Model model) {

        model.addAttribute(
                "marcas",
                marcaRepository.findAll()
        );

        model.addAttribute(
                "categorias",
                categoriaRepository.findAll()
        );
    }
}