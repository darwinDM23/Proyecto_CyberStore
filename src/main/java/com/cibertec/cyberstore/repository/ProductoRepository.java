package com.cibertec.cyberstore.repository;

import com.cibertec.cyberstore.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoria_Slug(String slug);

    List<Producto> findByMarca_Id(Long marcaId);

    // Ejemplo de JPQL (JPA Query Language) visto en la Unidad 1 del silabo
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
