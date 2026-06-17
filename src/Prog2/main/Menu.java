/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.main;

import Prog2.entities.*;
import Prog2.enums.*;
import Prog2.service.*;
import Prog2.utils.Validaciones;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author magae
 */

public class Menu {

    private Scanner scanner;
    private CategoriaService categoriaService;
    private ProductoService productoService;
    private UsuarioService usuarioService;
    private PedidoService pedidoService;

    public Menu() {
        scanner = new Scanner(System.in);
        categoriaService = new CategoriaService();
        productoService = new ProductoService();
        usuarioService = new UsuarioService();
        pedidoService = new PedidoService(usuarioService, productoService);
    }

    // Getters para pruebas en el Main
    public CategoriaService getCategoriaService() { return categoriaService; }
    public ProductoService getProductoService() { return productoService; }
    public UsuarioService getUsuarioService() { return usuarioService; }
    public PedidoService getPedidoService() { return pedidoService; }

    // ============================
    // MÉTODO PRINCIPAL
    // ============================
    public void iniciar() {
        int opcion;

        do {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> menuCategorias();
                case 2 -> menuProductos();
                case 3 -> menuUsuarios();
                case 4 -> menuPedidos();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    // ============================
    // CATEGORÍAS
    // ============================
    private void menuCategorias() {
        int opcion;

        do {
            System.out.println("\n=== CATEGORÍAS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> listarCategorias();
                case 2 -> crearCategoria();
                case 3 -> editarCategoria();
                case 4 -> eliminarCategoria();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private void listarCategorias() {
        List<Categoria> lista = categoriaService.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }
        lista.forEach(System.out::println);
    }

    private void crearCategoria() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        try {
            Categoria nueva = categoriaService.crear(nombre, descripcion);
            System.out.println("Categoría creada con ID: " + nueva.getId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editarCategoria() {
        listarCategorias();
        System.out.print("ID a editar: ");
        Long id = leerLong();

        Categoria categoria = categoriaService.buscarPorId(id);
        if (categoria == null || categoria.isEliminado()) {
            System.out.println("Categoría no encontrada o eliminada.");
            return;
        }

        System.out.print("Nuevo nombre (enter para mantener): ");
        String nombre = scanner.nextLine();
        if (nombre.isEmpty()) nombre = categoria.getNombre();

        System.out.print("Nueva descripción (enter para mantener): ");
        String descripcion = scanner.nextLine();
        if (descripcion.isEmpty()) descripcion = categoria.getDescripcion();

        try {
            boolean ok = categoriaService.editar(id, nombre, descripcion);
            System.out.println(ok ? "Categoría actualizada." : "No se pudo actualizar.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarCategoria() {
        listarCategorias();
        System.out.print("ID a eliminar: ");
        Long id = leerLong();

        System.out.print("Confirmar (S/N): ");
        String conf = scanner.nextLine();

        if (conf.equalsIgnoreCase("S")) {
            try {
                boolean ok = categoriaService.eliminar(id, productoService.listar());
                System.out.println(ok ? "Categoría eliminada." : "No se pudo eliminar.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ============================
    // PRODUCTOS
    // ============================
    private void menuProductos() {
        int opcion;

        do {
            System.out.println("\n=== PRODUCTOS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> listarProductos();
                case 2 -> crearProducto();
                case 3 -> editarProducto();
                case 4 -> eliminarProducto();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private void listarProductos() {
        List<Producto> lista = productoService.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }
        lista.forEach(System.out::println);
    }

    private void crearProducto() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        System.out.print("Precio: ");
        Double precio = leerDouble();

        System.out.print("Stock: ");
        int stock = leerEntero();

        System.out.print("Imagen: ");
        String imagen = scanner.nextLine();

        System.out.print("Disponible (true/false): ");
        boolean disponible = Boolean.parseBoolean(scanner.nextLine());

        listarCategorias();
        System.out.print("ID categoría: ");
        Long idCat = leerLong();

        Categoria cat = categoriaService.buscarPorId(idCat);
        if (cat == null || cat.isEliminado()) {
            System.out.println("Categoría inválida.");
            return;
        }

        try {
            Producto nuevo = productoService.crear(nombre, precio, descripcion, stock, imagen, disponible, cat);
            System.out.println("Producto creado con ID: " + nuevo.getId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editarProducto() {
        listarProductos();
        System.out.print("ID a editar: ");
        Long id = leerLong();

        System.out.print("Nuevo precio (enter para mantener): ");
        String precioStr = scanner.nextLine();

        System.out.print("Nuevo stock (enter para mantener): ");
        String stockStr = scanner.nextLine();

        System.out.print("Nueva categoría (enter para mantener): ");
        String catStr = scanner.nextLine();

        Double precio = null;
        Integer stock = null;
        Categoria cat = null;

        try {
            if (!precioStr.isEmpty()) precio = Double.parseDouble(precioStr);
            if (!stockStr.isEmpty()) stock = Integer.parseInt(stockStr);
            if (!catStr.isEmpty()) cat = categoriaService.buscarPorId(Long.parseLong(catStr));
        } catch (Exception e) {
            System.out.println("Error: valores inválidos.");
            return;
        }

        boolean ok = productoService.editar(id, precio, stock, cat);
        System.out.println(ok ? "Producto actualizado." : "No se pudo actualizar.");
    }

    private void eliminarProducto() {
        listarProductos();
        System.out.print("ID a eliminar: ");
        Long id = leerLong();

        System.out.print("Confirmar (S/N): ");
        String conf = scanner.nextLine();

        if (conf.equalsIgnoreCase("S")) {
            boolean ok = productoService.eliminar(id);
            System.out.println(ok ? "Producto eliminado." : "No se pudo eliminar.");
        }
    }

    // ============================
    // USUARIOS
    // ============================
    private void menuUsuarios() {
        int opcion;

        do {
            System.out.println("\n=== USUARIOS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> listarUsuarios();
                case 2 -> crearUsuario();
                case 3 -> editarUsuario();
                case 4 -> eliminarUsuario();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private void listarUsuarios() {
        List<Usuario> lista = usuarioService.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
            return;
        }
        lista.forEach(u -> System.out.printf(
            "ID: %d - %s %s - %s\n",
            u.getId(), u.getNombre(), u.getApellido(), u.getMail()
        ));
    }

    private void crearUsuario() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();

        System.out.print("Mail: ");
        String mail = scanner.nextLine();

        System.out.print("Celular: ");
        String celular = scanner.nextLine();

        try {
            Usuario nuevo = usuarioService.crear(nombre, apellido, mail, celular);
            System.out.println("Usuario creado con ID: " + nuevo.getId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editarUsuario() {
        listarUsuarios();
        System.out.print("ID a editar: ");
        Long id = leerLong();

        System.out.print("Nuevo nombre (enter para mantener): ");
        String nombre = scanner.nextLine();

        System.out.print("Nuevo apellido (enter para mantener): ");
        String apellido = scanner.nextLine();

        System.out.print("Nuevo mail (enter para mantener): ");
        String mail = scanner.nextLine();

        System.out.print("Nuevo celular (enter para mantener): ");
        String celular = scanner.nextLine();

        boolean ok = usuarioService.editar(id, nombre, apellido, mail, celular);
        System.out.println(ok ? "Usuario actualizado." : "No se pudo actualizar.");
    }

    private void eliminarUsuario() {
        listarUsuarios();
        System.out.print("ID a eliminar: ");
        Long id = leerLong();

        System.out.print("Confirmar (S/N): ");
        String conf = scanner.nextLine();

        if (conf.equalsIgnoreCase("S")) {
            boolean ok = usuarioService.eliminar(id);
            System.out.println(ok ? "Usuario eliminado." : "No se pudo eliminar.");
        }
    }

    // ============================
    // PEDIDOS
    // ============================
    private void menuPedidos() {
        int opcion;

        do {
            System.out.println("\n=== PEDIDOS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Actualizar estado / forma de pago");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> listarPedidos();
                case 2 -> crearPedido();
                case 3 -> actualizarPedido();
                case 4 -> eliminarPedido();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private void listarPedidos() {
        List<Pedido> lista = pedidoService.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay pedidos cargados.");
            return;
        }
        lista.forEach(System.out::println);
    }

    private void crearPedido() {
        listarUsuarios();
        System.out.print("ID usuario: ");
        Long idUsuario = leerLong();

        List<DetallePedido> detalles = new ArrayList<>();

        String continuar = "S";   // inicializada
        do {
            listarProductosConId();

            System.out.print("ID producto: ");
            Long idProd = leerLong();

            Producto prod = productoService.buscarPorId(idProd);
            if (prod == null || prod.isEliminado()) {
                System.out.println("Producto inválido.");
                continue;
            }

            System.out.print("Cantidad: ");
            int cantidad = leerEntero();

            if (cantidad <= 0) {
                System.out.println("Cantidad inválida.");
                continue;
            }

            if (cantidad > prod.getStock()) {
                System.out.println("Stock insuficiente.");
                continue;
            }

            double subtotal = prod.getPrecio() * cantidad;
            detalles.add(new DetallePedido(cantidad, subtotal, prod));

            System.out.print("Agregar otro detalle? (S/N): ");
            continuar = scanner.nextLine();

        } while (continuar.equalsIgnoreCase("S"));

        System.out.println("Formas de pago:");
        for (FormaPago fp : FormaPago.values()) {
            System.out.println(fp.ordinal() + " - " + fp);
        }

        System.out.print("Seleccione forma de pago: ");
        int fpIndex = leerEntero();
        FormaPago formaPago = FormaPago.values()[fpIndex];

        Pedido nuevo = pedidoService.crear(idUsuario, detalles, formaPago);

        if (nuevo != null) {
            System.out.println("Pedido creado con ID: " + nuevo.getId());
            System.out.println("Total: $" + nuevo.getTotal());
        } else {
            System.out.println("Error al crear el pedido.");
        }
    }

    private void listarProductosConId() {
        System.out.println("=== Productos disponibles ===");
        for (Producto p : productoService.listar()) {
            System.out.printf(
                "ID: %d - %s - $%.2f - Stock: %d - Categoría: %s\n",
                p.getId(),
                p.getNombre(),
                p.getPrecio(),
                p.getStock(),
                p.getCategoria().getNombre()
            );
        }
    }

    private void actualizarPedido() {
        listarPedidos();
        System.out.print("ID pedido: ");
        Long id = leerLong();

        System.out.println("Estados:");
        for (Estado e : Estado.values()) {
            System.out.println(e.ordinal() + " - " + e);
        }
        System.out.print("Nuevo estado: ");
        int estIndex = leerEntero();
        Estado estado = Estado.values()[estIndex];

        System.out.println("Formas de pago:");
        for (FormaPago fp : FormaPago.values()) {
            System.out.println(fp.ordinal() + " - " + fp);
        }
        System.out.print("Nueva forma de pago: ");
        int fpIndex = leerEntero();
        FormaPago formaPago = FormaPago.values()[fpIndex];

        boolean ok = pedidoService.actualizar(id, estado, formaPago);
        System.out.println(ok ? "Pedido actualizado." : "No se pudo actualizar.");
    }

    private void eliminarPedido() {
        listarPedidos();
        System.out.print("ID a eliminar: ");
        Long id = leerLong();

        System.out.print("Confirmar (S/N): ");
        String conf = scanner.nextLine();

        if (conf.equalsIgnoreCase("S")) {
            boolean ok = pedidoService.eliminar(id);
            System.out.println(ok ? "Pedido eliminado." : "No se pudo eliminar.");
        }
    }

    // ============================
    // MÉTODOS AUXILIARES
    // ============================
    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    private Long leerLong() {
        try {
            return Long.parseLong(scanner.nextLine());
        } catch (Exception e) {
            return -1L;
        }
    }

    private Double leerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (Exception e) {
            return -1.0;
        }
    }
}
