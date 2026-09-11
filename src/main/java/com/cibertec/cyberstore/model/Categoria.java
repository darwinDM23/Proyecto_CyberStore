package com.cibertec.cyberstore.model;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa una categoria de producto
 * (Mouse, Monitores, Impresoras, Tablets), tal como el menu
 * "Productos" del proyecto original.
 */
@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 60)
    private String nombre;

    /** Identificador amigable para URLs, ej: "mouse", "monitores". */
    @Column(nullable = false, unique = true, length = 60)
    private String slug;

    public Categoria() {
    }

    public Categoria(String nombre, String slug) {
        this.nombre = nombre;
        this.slug = slug;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
