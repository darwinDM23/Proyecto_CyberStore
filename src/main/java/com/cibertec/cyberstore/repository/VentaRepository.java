package com.cibertec.cyberstore.repository;

import com.cibertec.cyberstore.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    // JPQL: trae las ventas ordenadas de la mas reciente a la mas antigua,
    // usado tanto por el panel de administracion como por el reporte JasperReports.
    @Query("SELECT v FROM Venta v ORDER BY v.fecha DESC")
    List<Venta> buscarTodasOrdenadasPorFecha();
    @Query("""
       SELECT v FROM Venta v
       WHERE LOWER(v.cliente.nombre) LIKE LOWER(CONCAT('%', :cliente, '%'))
       ORDER BY v.fecha DESC
       """)
List<Venta> buscarPorCliente(@Param("cliente") String cliente);
}
