package com.cibertec.cyberstore.repository;

import com.cibertec.cyberstore.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findBySlug(String slug);
    boolean existsByNombre(String nombre);
}
