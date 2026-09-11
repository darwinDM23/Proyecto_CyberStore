package com.cibertec.cyberstore.repository;

import com.cibertec.cyberstore.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    boolean existsByNombre(String nombre);
}
