package com.cibertec.cyberstore.controller.admin;

import com.cibertec.cyberstore.service.ReporteVentasService;
import com.cibertec.cyberstore.service.VentaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/ventas")
public class VentaAdminController {

    private final VentaService ventaService;
    private final ReporteVentasService reporteVentasService;

    public VentaAdminController(VentaService ventaService, ReporteVentasService reporteVentasService) {
        this.ventaService = ventaService;
        this.reporteVentasService = reporteVentasService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false, defaultValue = "") String cliente,
                     Model model) {

    if (cliente.isBlank()) {
        model.addAttribute("ventas", ventaService.listarTodas());
    } else {
        model.addAttribute("ventas", ventaService.buscarPorCliente(cliente.trim()));
    }

    model.addAttribute("cliente", cliente);

    return "admin/ventas/lista";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("venta", ventaService.buscarPorId(id));
        return "admin/ventas/detalle";
    }

    /**
     * Unidad 3 del silabo: genera y descarga el reporte de ventas en PDF
     * usando JasperReports, a partir de la informacion real almacenada
     * (via JPA/Hibernate) en la base de datos.
     */
    @GetMapping("/reporte")
    public ResponseEntity<byte[]> descargarReportePdf() throws Exception {
        byte[] pdf = reporteVentasService.generarReporteVentasPdf(ventaService.listarTodas());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "reporte_ventas.pdf");

        return ResponseEntity.ok().headers(headers).body(pdf);
    }
}
