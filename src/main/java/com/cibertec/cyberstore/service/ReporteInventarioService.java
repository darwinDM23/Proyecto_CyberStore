package com.cibertec.cyberstore.service;

import com.cibertec.cyberstore.model.Producto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReporteInventarioService {

    public byte[] generarReporteInventarioPdf(List<Producto> productos) throws Exception {

        InputStream inputStream = new ClassPathResource(
                "reports/reporte_inventario.jrxml"
        ).getInputStream();

        JasperReport jasperReport =
                JasperCompileManager.compileReport(inputStream);

        JRBeanCollectionDataSource dataSource =
                new JRBeanCollectionDataSource(productos);

        Map<String, Object> parametros = new HashMap<>();

        parametros.put(
                "titulo",
                "Reporte de Inventario - CiberStore"
        );

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(
                        jasperReport,
                        parametros,
                        dataSource
                );

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}