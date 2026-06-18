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

/**
 * Menú para gestionar pedidos.
 * Permite listar, crear, actualizar estado/forma de pago y eliminar pedidos.
 * Interactúa con PedidoService, UsuarioService y ProductoService.
 */
public class MenuPedidos {

    private Scanner scanner;
    private PedidoService pedidoService;
    private UsuarioService usuarioService;
    private ProductoService productoService;

    // Constructor
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
    // CREAR PEDIDO
    // ============================
    private void crear() {

        // 1. Seleccionar usuario
        System.out.println("\nUsuarios disponibles:");
        usuarioService.listar().forEach(System.out::println);

        System.out.print("ID del usuario: ");
        Long idUsuario = leerLong();

        // 2. Cargar detalles del pedido
        List<DetallePedido> detalles = new ArrayList<>();

        String continuar;
        do {
            System.out.println("\nProductos disponibles:");
            productoService.listar().forEach(System.out::println);

            System.out.print("ID del producto: ");
            Long idProd = leerLong();

            System.out.print("Cantidad: ");
            int cantidad = leerEntero();

            // Creamos un DetallePedido temporal solo para pasar datos al service
            Producto prod = productoService.buscarPorId(idProd);
            if (prod == null || prod.isEliminado()) {
                System.out.println("Producto inválido.");
            } else {
                double subtotal = prod.getPrecio() * cantidad;
                detalles.add(new DetallePedido(cantidad, subtotal, prod));
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
        FormaPago formaPago = FormaPago.valueOf(fpStr.toUpperCase());

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
        if (p == null || p.isEliminado()) {
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
        Estado nuevoEstado = estadoStr.isEmpty() ? null : Estado.valueOf(estadoStr.toUpperCase());

        // Nueva forma de pago
        System.out.println("\nFormas de pago:");
        for (FormaPago fp : FormaPago.values()) {
            System.out.println("- " + fp);
        }

        System.out.print("Nueva forma de pago (enter para mantener): ");
        String fpStr = scanner.nextLine();
        FormaPago nuevaFormaPago = fpStr.isEmpty() ? null : FormaPago.valueOf(fpStr.toUpperCase());

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
