package com.cibertec.cyberstore.service;

import com.cibertec.cyberstore.dto.VentaReporteDTO;
import com.cibertec.cyberstore.model.Venta;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Unidad 3 del silabo: generacion de reportes dinamicos con JasperReports.
 *
 * El .jrxml (diseñado, en un caso real, con Jasper Studio) se compila en
 * memoria con JasperCompileManager y se llena (fill) con los datos reales
 * de las ventas registradas en la base de datos, exportando el resultado
 * como un PDF descargable.
 */
@Service
public class ReporteVentasService {

    private static final String RUTA_JRXML = "reports/reporte_ventas.jrxml";

    public byte[] generarReporteVentasPdf(List<Venta> ventas) throws JRException {
        List<VentaReporteDTO> datosReporte = ventas.stream()
                .map(VentaReporteDTO::new)
                .collect(Collectors.toList());

        try (InputStream jrxmlStream = new ClassPathResource(RUTA_JRXML).getInputStream()) {
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("titulo", "Reporte de Ventas - CiberStore");

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datosReporte);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new JRException("No se pudo generar el reporte de ventas", e);
        }
    }
}
