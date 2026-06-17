/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.main;

import Prog2.entities.DetallePedido;
import Prog2.entities.Pedido;
import Prog2.entities.Producto;
import Prog2.entities.Usuario;
import Prog2.enums.Estado;
import Prog2.enums.FormaPago;
import Prog2.service.PedidoService;
import Prog2.service.ProductoService;
import Prog2.service.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author magae
 */

public class MenuPedidos {

    private Scanner scanner;
    private PedidoService pedidoService;
    private UsuarioService usuarioService;
    private ProductoService productoService;

    public MenuPedidos(Scanner scanner, PedidoService pedidoService, UsuarioService usuarioService, ProductoService productoService) {
        this.scanner = scanner;
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
        this.productoService = productoService;
    }

    public void iniciar() {
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
                case 1 -> listar();
                case 2 -> crear();
                case 3 -> actualizar();
                case 4 -> eliminar();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    // ============================
    // LISTAR
    // ============================
    private void listar() {
        List<Pedido> lista = pedidoService.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay pedidos cargados.");
            return;
        }
        lista.forEach(System.out::println);
    }

    // ============================
    // CREAR PEDIDO
    // ============================
    private void crear() {

        // Mostrar usuarios
        usuarioService.listar().forEach(u -> System.out.printf(
            "ID: %d - %s %s\n", u.getId(), u.getNombre(), u.getApellido()
        ));

        System.out.print("ID usuario: ");
        Long idUsuario = leerLong();

        List<DetallePedido> detalles = new ArrayList<>();
        String continuar = "S";

        do {
            listarProductos();

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

        // Formas de pago
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

    // ============================
    // LISTAR PRODUCTOS
    // ============================
    private void listarProductos() {
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

    // ============================
    // ACTUALIZAR PEDIDO
    // ============================
    private void actualizar() {
        listar();
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

    // ============================
    // ELIMINAR PEDIDO
    // ============================
    private void eliminar() {
        listar();
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
        try { return Integer.parseInt(scanner.nextLine()); }
        catch (Exception e) { return -1; }
    }

    private Long leerLong() {
        try { return Long.parseLong(scanner.nextLine()); }
        catch (Exception e) { return -1L; }
    }
}

