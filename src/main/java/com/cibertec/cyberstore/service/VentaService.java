package com.cibertec.cyberstore.service;

import com.cibertec.cyberstore.dto.ItemCarrito;
import com.cibertec.cyberstore.model.*;
import com.cibertec.cyberstore.repository.ClienteRepository;
import com.cibertec.cyberstore.repository.ProductoRepository;
import com.cibertec.cyberstore.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio que confirma una compra: crea el Cliente si no existe, crea la
 * Venta con sus DetalleVenta y descuenta el stock de cada Producto.
 *
 * Todo el metodo esta anotado con @Transactional: si algo falla (por
 * ejemplo, no hay stock suficiente) Hibernate hace rollback de todos los
 * cambios, evitando dejar datos inconsistentes en la base de datos.
 */
@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public VentaService(VentaRepository ventaRepository,
                         ClienteRepository clienteRepository,
                         ProductoRepository productoRepository) {
        this.ventaRepository = ventaRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public Venta confirmarCompra(Carrito carrito, String nombreCliente, String emailCliente,
                                  String telefono, String direccion) {
        if (carrito.isVacio()) {
            throw new IllegalStateException("El carrito esta vacio");
        }

        Cliente cliente = clienteRepository.findByEmail(emailCliente)
                .orElseGet(() -> clienteRepository.save(
                        new Cliente(nombreCliente, emailCliente, telefono, direccion)));

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(LocalDateTime.now());
        venta.setEstado(EstadoVenta.PAGADA);

        for (ItemCarrito item : carrito.getItems()) {
            Producto producto = productoRepository.findById(item.getProducto().getId())
                    .orElseThrow(() -> new IllegalStateException("Producto ya no disponible"));

            if (producto.getStock() < item.getCantidad()) {
                throw new IllegalStateException("Stock insuficiente para " + producto.getNombre());
            }
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            DetalleVenta detalle = new DetalleVenta(producto, item.getCantidad(), producto.getPrecio());
            venta.agregarDetalle(detalle);
        }

        venta.setTotal(carrito.getTotal());
        Venta ventaGuardada = ventaRepository.save(venta);
        carrito.vaciar();
        return ventaGuardada;
    }

    @Transactional(readOnly = true)
    public List<Venta> listarTodas() {
        return ventaRepository.buscarTodasOrdenadasPorFecha();
    }

    @Transactional(readOnly = true)
    public Venta buscarPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada: " + id));
    }
}
