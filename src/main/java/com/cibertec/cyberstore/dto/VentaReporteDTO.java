package com.cibertec.cyberstore.dto;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import com.cibertec.cyberstore.model.Venta;

/**
 * DTO "plano" (sin relaciones JPA) que alimenta el reporte JasperReports.
 * Se usa un DTO en lugar de la entidad Venta directamente para que
 * JasperReports (via reflexion / JavaBeans) no dependa de proxies de
 * Hibernate ni de colecciones lazy.
 */
public class VentaReporteDTO {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final Long id;
    private final String fecha;
    private final String cliente;
    private final String email;
    private final Integer cantidadItems;
    private final BigDecimal total;
    private final String estado;

    public VentaReporteDTO(Venta venta) {
        this.id = venta.getId();
        this.fecha = venta.getFecha().format(FORMATO_FECHA);
        this.cliente = venta.getCliente().getNombre();
        this.email = venta.getCliente().getEmail();
        this.cantidadItems = venta.getDetalles().size();
        this.total = venta.getTotal();
        this.estado = venta.getEstado().name();
    }

    public Long getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public String getEmail() {
        return email;
    }

    public Integer getCantidadItems() {
        return cantidadItems;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getEstado() {
        return estado;
    }
}
