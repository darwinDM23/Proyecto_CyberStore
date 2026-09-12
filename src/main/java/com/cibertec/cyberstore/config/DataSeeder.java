package com.cibertec.cyberstore.config;

import com.cibertec.cyberstore.model.Categoria;
import com.cibertec.cyberstore.model.Marca;
import com.cibertec.cyberstore.model.Producto;
import com.cibertec.cyberstore.model.Usuario;

import com.cibertec.cyberstore.repository.CategoriaRepository;
import com.cibertec.cyberstore.repository.MarcaRepository;
import com.cibertec.cyberstore.repository.ProductoRepository;
import com.cibertec.cyberstore.repository.UsuarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Carga datos iniciales de CiberStore.
 *
 * Crea usuarios para demostrar sesiones y roles.
 * Los productos se cargan solamente si la base de datos
 * aun no contiene marcas.
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final MarcaRepository marcaRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(MarcaRepository marcaRepository,
                      CategoriaRepository categoriaRepository,
                      ProductoRepository productoRepository,
                      UsuarioRepository usuarioRepository,
                      PasswordEncoder passwordEncoder) {

        this.marcaRepository = marcaRepository;
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // =========================================================
        // USUARIOS DEL SISTEMA
        // =========================================================

        if (usuarioRepository.findByCorreo("admin@ciberstore.com").isEmpty()) {

            Usuario admin = new Usuario();

            admin.setNombre("Administrador CiberStore");
            admin.setCorreo("admin@ciberstore.com");
            admin.setPassword(passwordEncoder.encode("Admin123*"));
            admin.setRol("ADMIN");
            admin.setActivo(true);

            usuarioRepository.save(admin);
        }

        if (usuarioRepository.findByCorreo("vendedor@ciberstore.com").isEmpty()) {

            Usuario vendedor = new Usuario();

            vendedor.setNombre("Vendedor CiberStore");
            vendedor.setCorreo("vendedor@ciberstore.com");
            vendedor.setPassword(passwordEncoder.encode("Vendedor123*"));
            vendedor.setRol("VENDEDOR");
            vendedor.setActivo(true);

            usuarioRepository.save(vendedor);
        }

        if (usuarioRepository.findByCorreo("almacenero@ciberstore.com").isEmpty()) {

            Usuario almacenero = new Usuario();

            almacenero.setNombre("Almacenero CiberStore");
            almacenero.setCorreo("almacenero@ciberstore.com");
            almacenero.setPassword(passwordEncoder.encode("Almacen123*"));
            almacenero.setRol("ALMACENERO");
            almacenero.setActivo(true);

            usuarioRepository.save(almacenero);
        }

        // =========================================================
        // PRODUCTOS, MARCAS Y CATEGORIAS
        // =========================================================

        // Si ya existen marcas, no volvemos a cargar productos.
        if (marcaRepository.count() > 0) {
            return;
        }

        Map<String, Marca> marcas = new LinkedHashMap<>();

        marcas.put("Acer", marcaRepository.save(
                new Marca(
                        "Acer",
                        "Marca taiwanesa reconocida por sus laptops, PCs y monitores gaming.",
                        "/img/marcas/acer.jpg"
                )
        ));

        marcas.put("HP", marcaRepository.save(
                new Marca(
                        "HP",
                        "Hewlett-Packard, lider mundial en computadoras e impresoras.",
                        "/img/marcas/hp.png"
                )
        ));

        marcas.put("Lenovo", marcaRepository.save(
                new Marca(
                        "Lenovo",
                        "Fabricante lider de laptops, tablets y equipos empresariales.",
                        "/img/marcas/lenovo.png"
                )
        ));

        marcas.put("Asus", marcaRepository.save(
                new Marca(
                        "Asus",
                        "Reconocida por sus componentes y equipos orientados a gaming.",
                        "/img/marcas/asus.png"
                )
        ));

        marcas.put("Apple", marcaRepository.save(
                new Marca(
                        "Apple",
                        "Ecosistema premium de dispositivos: iPad, Mac y accesorios.",
                        "/img/marcas/apple.png"
                )
        ));

        marcas.put("Logitech", marcaRepository.save(
                new Marca(
                        "Logitech",
                        "Especialista en perifericos: mouse, teclados y accesorios gamer.",
                        null
                )
        ));

        marcas.put("Samsung", marcaRepository.save(
                new Marca(
                        "Samsung",
                        "Electronica de consumo: monitores, tablets y mas.",
                        null
                )
        ));

        marcas.put("Epson", marcaRepository.save(
                new Marca(
                        "Epson",
                        "Especialista en impresoras de tinta continua EcoTank.",
                        null
                )
        ));

        marcas.put("Xiaomi", marcaRepository.save(
                new Marca(
                        "Xiaomi",
                        "Tecnologia accesible: tablets, accesorios y mas.",
                        null
                )
        ));

        marcas.put("Brother", marcaRepository.save(
                new Marca(
                        "Brother",
                        "Impresoras laser multifuncion para hogar y oficina.",
                        null
                )
        ));

        Categoria mouse =
                categoriaRepository.save(
                        new Categoria("Mouse", "mouse")
                );

        Categoria monitores =
                categoriaRepository.save(
                        new Categoria("Monitores", "monitores")
                );

        Categoria impresoras =
                categoriaRepository.save(
                        new Categoria("Impresoras", "impresoras")
                );

        Categoria tablets =
                categoriaRepository.save(
                        new Categoria("Tablets", "tablets")
                );

        // =========================================================
        // MOUSE
        // =========================================================

        productoRepository.save(
                new Producto(
                        "Mouse Gamer Logitech G203 Lightsync",
                        "Mouse gamer con iluminacion RGB Lightsync, sensor optico de alta precision.",
                        new BigDecimal("185.00"),
                        "/img/productos/mouse-logitech-g203.avif",
                        25,
                        marcas.get("Logitech"),
                        mouse
                )
        );

        productoRepository.save(
                new Producto(
                        "Mouse Gamer Logitech G502 X Plus RGB",
                        "Mouse gamer inalambrico de alta gama, switches opticos LIGHTFORCE.",
                        new BigDecimal("600.00"),
                        "/img/productos/mouse-logitech-g502.avif",
                        12,
                        marcas.get("Logitech"),
                        mouse
                )
        );

        productoRepository.save(
                new Producto(
                        "Mouse Inalambrico INPHIC A9 Bee",
                        "Mouse inalambrico recargable de tres modos de conexion, interfaz USB.",
                        new BigDecimal("250.00"),
                        "/img/productos/mouse-inphic-a9.avif",
                        30,
                        null,
                        mouse
                )
        );

        // =========================================================
        // MONITORES
        // =========================================================

        productoRepository.save(
                new Producto(
                        "Monitor Curvo Teros TE-2731S 27\"",
                        "Monitor curvo de 27 pulgadas, ideal para productividad y entretenimiento.",
                        new BigDecimal("530.00"),
                        "/img/productos/monitor-teros.avif",
                        15,
                        null,
                        monitores
                )
        );

        productoRepository.save(
                new Producto(
                        "Monitor Gamer LG 27GS65F",
                        "Monitor gamer de alta tasa de refresco para juegos competitivos.",
                        new BigDecimal("1800.00"),
                        "/img/productos/monitor-lg-gamer.jpg",
                        8,
                        null,
                        monitores
                )
        );

        productoRepository.save(
                new Producto(
                        "Monitor Samsung A22i",
                        "Monitor Samsung con panel de alta calidad de imagen y colores vivos.",
                        new BigDecimal("1000.00"),
                        "/img/productos/monitor-samsung-a22i.jpg",
                        10,
                        marcas.get("Samsung"),
                        monitores
                )
        );

        // =========================================================
        // IMPRESORAS
        // =========================================================

        productoRepository.save(
                new Producto(
                        "Impresora Multifuncional Epson EcoTank L3250",
                        "Sistema de tinta continua, impresion, copia y escaneo con Wi-Fi.",
                        new BigDecimal("700.00"),
                        "/img/productos/impresora-epson-l3250.webp",
                        18,
                        marcas.get("Epson"),
                        impresoras
                )
        );

        productoRepository.save(
                new Producto(
                        "Impresora HP Smart Tank 580",
                        "Impresora multifuncional con tinta continua, Wi-Fi y app HP Smart.",
                        new BigDecimal("580.00"),
                        "/img/productos/impresora-hp-smart-tank.webp",
                        14,
                        marcas.get("HP"),
                        impresoras
                )
        );

        productoRepository.save(
                new Producto(
                        "Impresora Brother DCP-L2640DW",
                        "Impresora laser multifuncion, ideal para oficinas con alto volumen.",
                        new BigDecimal("950.00"),
                        "/img/productos/impresora-brother.jpg",
                        9,
                        marcas.get("Brother"),
                        impresoras
                )
        );

        // =========================================================
        // TABLETS
        // =========================================================

        productoRepository.save(
                new Producto(
                        "Tablet Samsung Galaxy Tab S6 Lite (2024)",
                        "Tablet Samsung con S Pen incluido, pantalla de 10.4 pulgadas.",
                        new BigDecimal("739.00"),
                        "/img/productos/tablet-samsung-s6-lite.jpg",
                        20,
                        marcas.get("Samsung"),
                        tablets
                )
        );

        productoRepository.save(
                new Producto(
                        "Tablet Lenovo 11\" 128GB con Folio Case y Lapiz",
                        "Tablet Lenovo con accesorios incluidos, ideal para estudio y trabajo.",
                        new BigDecimal("699.00"),
                        "/img/productos/tablet-lenovo-11.avif",
                        16,
                        marcas.get("Lenovo"),
                        tablets
                )
        );

        productoRepository.save(
                new Producto(
                        "Apple iPad Air 11\" Chip M2 128GB Wifi",
                        "iPad Air con chip M2, pantalla Liquid Retina de 11 pulgadas.",
                        new BigDecimal("3299.00"),
                        "/img/productos/tablet-ipad-air.webp",
                        6,
                        marcas.get("Apple"),
                        tablets
                )
        );

        productoRepository.save(
                new Producto(
                        "Tablet Xiaomi Redmi Pad Pro",
                        "Tablet Xiaomi con gran autonomia de bateria y pantalla de alta resolucion.",
                        new BigDecimal("1299.00"),
                        "/img/productos/tablet-xiaomi-redmi-pad.webp",
                        11,
                        marcas.get("Xiaomi"),
                        tablets
                )
        );
    }
}