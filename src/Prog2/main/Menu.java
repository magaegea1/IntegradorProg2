/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.main;

import Prog2.service.CategoriaService;
import Prog2.service.PedidoService;
import Prog2.service.ProductoService;
import Prog2.service.UsuarioService;
import java.util.Scanner;

/**
 *
 * @author magae
 */

public class Menu {

    private static final Scanner sc = new Scanner(System.in);

    private static final UsuarioService usuarioService = new UsuarioService();
    private static final CategoriaService categoriaService = new CategoriaService();
    private static final ProductoService productoService = new ProductoService();
    private static final PedidoService pedidoService = new PedidoService();

    public static void mostrarMenuPrincipal() {
        int opcion;

        do {
            System.out.println("=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> menuCategorias();
                case 2 -> menuProductos();
                case 3 -> menuUsuarios();
                case 4 -> menuPedidos();
            }

        } while (opcion != 0);
    }

    private static void menuCategorias() {}
    private static void menuProductos() {}
    private static void menuUsuarios() {}
    private static void menuPedidos() {}

}

