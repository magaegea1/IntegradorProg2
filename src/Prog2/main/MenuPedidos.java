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
import Prog2.exception.DatoInvalidoException;
import Prog2.exception.EntidadNoEncontradaException;
import Prog2.exception.StockInsuficienteException;
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

    public MenuPedidos(Scanner scanner, PedidoService pedidoService,
                       UsuarioService usuarioService, ProductoService productoService) {
        this.scanner = scanner;
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
        this.productoService = productoService;
    }

    // ============================
    // MENÚ PRINCIPAL
    // ============================
    public void iniciar() {
        int opcion;

        do {
            System.out.println("\n=== PEDIDOS ===");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Actualizar estado / forma de pago");
            System.out.println("4. Eliminar");
            System.out.println("5. Ver pedido completo");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> listar();
                case 2 -> crear();
                case 3 -> actualizar();
                case 4 -> eliminar();
                case 5 -> verPedidoCompleto();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    // ============================
    // LISTAR PEDIDOS
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
    // VER PEDIDO COMPLETO
    // ============================
    private void verPedidoCompleto() {
        System.out.print("Ingrese el ID del pedido: ");
        Long id = leerLong();

        Pedido p = pedidoService.buscarPorId(id);

        if (p == null) {
            System.out.println("El pedido no existe o está eliminado.");
            return;
        }

        System.out.println("\n=== DETALLE COMPLETO DEL PEDIDO ===");
        System.out.println("ID: " + p.getId());
        System.out.println("Usuario: " + p.getUsuario().getNombre() + " " + p.getUsuario().getApellido());
        System.out.println("Estado: " + p.getEstado());
        System.out.println("Forma de pago: " + p.getFormaPago());
        System.out.println("Fecha: " + p.getFecha());
        System.out.println("Total: $" + p.getTotal());

        System.out.println("\n--- DETALLES ---");
        if (p.getDetalles().isEmpty()) {
            System.out.println("Este pedido no tiene detalles.");
        } else {
            for (DetallePedido d : p.getDetalles()) {
                System.out.println("Producto: " + d.getProducto().getNombre());
                System.out.println("Cantidad: " + d.getCantidad());
                System.out.println("Precio unitario: $" + d.getProducto().getPrecio());
                System.out.println("Subtotal: $" + d.getSubtotal());
                System.out.println("-------------------------");
            }
        }
    }

    // ============================
    // CREAR PEDIDO
    // ============================
    private void crear() {

        // 1. Seleccionar usuario
        System.out.println("\nUsuarios disponibles:");
        List<Usuario> usuarios = usuarioService.listar();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios cargados.");
            return;
        }
        usuarios.forEach(System.out::println);

        System.out.print("ID del usuario: ");
        Long idUsuario = leerLong();

        // 2. Cargar detalles del pedido
        List<DetallePedido> detalles = new ArrayList<>();

        String continuar;
        do {
            System.out.println("\nProductos disponibles:");
            List<Producto> productos = productoService.listar();
            if (productos.isEmpty()) {
                System.out.println("No hay productos cargados.");
                break;
            }
            productos.forEach(System.out::println);

            System.out.print("ID del producto: ");
            Long idProd = leerLong();

            System.out.print("Cantidad: ");
            int cantidad = leerEntero();

            if (cantidad <= 0) {
                System.out.println("Cantidad inválida.");
            } else {
                Producto prod = productoService.buscarPorId(idProd);
                if (prod == null) {
                    System.out.println("Producto inválido.");
                } else {
                    double subtotal = prod.getPrecio() * cantidad;
                    detalles.add(new DetallePedido(cantidad, subtotal, prod));
                }
            }

            System.out.print("¿Agregar otro producto? (S/N): ");
            continuar = scanner.nextLine();

        } while (continuar.equalsIgnoreCase("S"));

        // 3. Seleccionar forma de pago
        System.out.println("\nFormas de pago:");
        for (FormaPago fp : FormaPago.values()) {
            System.out.println("- " + fp);
        }

        System.out.print("Forma de pago: ");
        String fpStr = scanner.nextLine();
        FormaPago formaPago;

        try {
            formaPago = FormaPago.valueOf(fpStr.toUpperCase());
        } catch (Exception e) {
            System.out.println("Forma de pago inválida.");
            return;
        }

        // 4. Crear pedido
        try {
            Pedido nuevo = pedidoService.crear(idUsuario, detalles, formaPago);
            System.out.println("Pedido creado con ID: " + nuevo.getId());
        } catch (EntidadNoEncontradaException | DatoInvalidoException | StockInsuficienteException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ============================
    // ACTUALIZAR PEDIDO
    // ============================
    private void actualizar() {
        listar();
        System.out.print("ID del pedido a actualizar: ");
        Long id = leerLong();

        Pedido p = pedidoService.buscarPorId(id);
        if (p == null) {
            System.out.println("Pedido no encontrado o eliminado.");
            return;
        }

        // Nuevo estado
        System.out.println("\nEstados disponibles:");
        for (Estado e : Estado.values()) {
            System.out.println("- " + e);
        }

        System.out.print("Nuevo estado (enter para mantener): ");
        String estadoStr = scanner.nextLine();
        Estado nuevoEstado = null;

        if (!estadoStr.isEmpty()) {
            try {
                nuevoEstado = Estado.valueOf(estadoStr.toUpperCase());
            } catch (Exception e) {
                System.out.println("Estado inválido.");
                return;
            }
        }

        // Nueva forma de pago
        System.out.println("\nFormas de pago:");
        for (FormaPago fp : FormaPago.values()) {
            System.out.println("- " + fp);
        }

        System.out.print("Nueva forma de pago (enter para mantener): ");
        String fpStr = scanner.nextLine();
        FormaPago nuevaFormaPago = null;

        if (!fpStr.isEmpty()) {
            try {
                nuevaFormaPago = FormaPago.valueOf(fpStr.toUpperCase());
            } catch (Exception e) {
                System.out.println("Forma de pago inválida.");
                return;
            }
        }

        try {
            pedidoService.actualizar(id, nuevoEstado, nuevaFormaPago);
            System.out.println("Pedido actualizado.");
        } catch (EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
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
            try {
                pedidoService.eliminar(id);
                System.out.println("Pedido eliminado.");
            } catch (EntidadNoEncontradaException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ============================
    // MÉTODOS AUXILIARES DE LECTURA
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