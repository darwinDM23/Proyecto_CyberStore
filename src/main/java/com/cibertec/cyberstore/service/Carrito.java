package com.cibertec.cyberstore.service;

import com.cibertec.cyberstore.dto.ItemCarrito;
import com.cibertec.cyberstore.model.Producto;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collection;

/**
 * Carrito de compras con alcance de SESION HTTP: cada usuario que navega el
 * sitio tiene su propia instancia, administrada automaticamente por Spring
 * (inyeccion de dependencias / control de inversion, Unidad 2 del silabo).
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Carrito {

    private final Map<Long, ItemCarrito> items = new LinkedHashMap<>();

    public void agregarProducto(Producto producto, int cantidad) {
        items.merge(producto.getId(), new ItemCarrito(producto, cantidad),
                (existente, nuevo) -> {
                    existente.sumarCantidad(cantidad);
                    return existente;
                });
    }

    public void actualizarCantidad(Long productoId, int cantidad) {
        ItemCarrito item = items.get(productoId);
        if (item != null) {
            if (cantidad <= 0) {
                items.remove(productoId);
            } else {
                item.setCantidad(cantidad);
            }
        }
    }

    public void eliminarProducto(Long productoId) {
        items.remove(productoId);
    }

    public void vaciar() {
        items.clear();
    }

    public Collection<ItemCarrito> getItems() {
        return items.values();
    }

    public int getCantidadTotalItems() {
        return items.values().stream().mapToInt(ItemCarrito::getCantidad).sum();
    }

    public BigDecimal getTotal() {
        return items.values().stream()
                .map(ItemCarrito::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isVacio() {
        return items.isEmpty();
    }
}
