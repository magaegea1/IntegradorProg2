/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.main;

import Prog2.service.*;
import java.util.Scanner;

/**
 *
 * @author magae
 */

public class MenuPrincipal {

    private Scanner scanner = new Scanner(System.in);

    private CategoriaService categoriaService = new CategoriaService();
    private ProductoService productoService = new ProductoService();
    private UsuarioService usuarioService = new UsuarioService();
    private PedidoService pedidoService = new PedidoService(usuarioService, productoService);

    private MenuCategorias menuCategorias = new MenuCategorias(scanner, categoriaService, productoService);
    private MenuProductos menuProductos = new MenuProductos(scanner, productoService, categoriaService);
    private MenuUsuarios menuUsuarios = new MenuUsuarios(scanner, usuarioService);
    private MenuPedidos menuPedidos = new MenuPedidos(scanner, pedidoService, usuarioService, productoService);

    // Getters
    public CategoriaService getCategoriaService() {
        return categoriaService;
    }

    public ProductoService getProductoService() {
        return productoService;
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public PedidoService getPedidoService() {
        return pedidoService;
    }



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
                case 1 -> menuCategorias.iniciar();
                case 2 -> menuProductos.iniciar();
                case 3 -> menuUsuarios.iniciar();
                case 4 -> menuPedidos.iniciar();
                case 0 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
}


