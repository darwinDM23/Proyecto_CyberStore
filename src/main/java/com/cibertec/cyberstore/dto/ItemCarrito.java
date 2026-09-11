package com.cibertec.cyberstore.dto;

import com.cibertec.cyberstore.model.Producto;

import java.math.BigDecimal;

/**
 * Representa una linea del carrito de compras (no se persiste hasta el
 * checkout; en ese momento se convierte en un DetalleVenta real).
 */
public class ItemCarrito {

    private final Producto producto;
    private int cantidad;

    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void sumarCantidad(int cantidad) {
        this.cantidad += cantidad;
    }

    public BigDecimal getSubtotal() {
        return producto.getPrecio().multiply(BigDecimal.valueOf(cantidad));
    }
}
