/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.main;

import Prog2.entities.Categoria;
import Prog2.entities.Producto;
import Prog2.exception.DatoInvalidoException;
import Prog2.exception.EntidadNoEncontradaException;
import Prog2.service.CategoriaService;
import Prog2.service.ProductoService;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author magae
 */

/**
 * Menú para gestionar productos.
 * Permite listar, crear, editar y eliminar productos.
 * Interactúa con ProductoService y CategoriaService.
 */
public class MenuProductos {

    private Scanner scanner;
    private ProductoService productoService;
    private CategoriaService categoriaService;

    // Constructor
    public MenuProductos(Scanner scanner, ProductoService productoService, CategoriaService categoriaService) {
        this.scanner = scanner;
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    // ============================
    // MENÚ PRINCIPAL
    // ============================
    public void iniciar() {
        int opcion;

        do {
            System.out.println("\n=== PRODUCTOS ===");
            System.out.println("1. Listar");
            System.out.println("2. Listar por categoría");
            System.out.println("3. Crear");
            System.out.println("4. Editar");
            System.out.println("5. Eliminar");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1 -> listar();
                case 2 -> listarPorCategoria();
                case 3 -> crear();
                case 4 -> editar();
                case 5 -> eliminar();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    // ============================
    // LISTAR TODOS LOS PRODUCTOS
    // ============================
    private void listar() {
        List<Producto> lista = productoService.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }
        lista.forEach(System.out::println);
    }

    // ============================
    // LISTAR PRODUCTOS POR CATEGORÍA
    // ============================
    private void listarPorCategoria() {
        System.out.println("\nCategorías disponibles:");
        List<Categoria> categorias = categoriaService.listar();
        categorias.forEach(System.out::println);

        System.out.print("ID de categoría: ");
        Long idCat = leerLong();

        Categoria categoria = categoriaService.buscarPorId(idCat);

        if (categoria == null || categoria.isEliminado()) {
            System.out.println("Categoría no encontrada o eliminada.");
            return;
        }

        List<Producto> lista = productoService.listarPorCategoria(categoria);

        if (lista.isEmpty()) {
            System.out.println("No hay productos en esta categoría.");
            return;
        }

        lista.forEach(System.out::println);
    }

    // ============================
    // CREAR PRODUCTO
    // ============================
    private void crear() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Precio: ");
        Double precio = leerDouble();

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        System.out.print("Stock: ");
        Integer stock = leerEntero();

        System.out.print("Imagen (URL o ruta): ");
        String imagen = scanner.nextLine();

        System.out.print("Disponible (true/false): ");
        boolean disponible = Boolean.parseBoolean(scanner.nextLine());

        System.out.println("\nCategorías disponibles:");
        categoriaService.listar().forEach(System.out::println);

        System.out.print("ID de categoría: ");
        Long idCat = leerLong();

        try {
            Categoria categoria = categoriaService.buscarPorId(idCat);
            if (categoria == null || categoria.isEliminado()) {
                throw new EntidadNoEncontradaException("La categoría no existe o está eliminada.");
            }

            Producto nuevo = productoService.crear(
                nombre, precio, descripcion, stock, imagen, disponible, categoria
            );

            System.out.println("Producto creado con ID: " + nuevo.getId());

        } catch (DatoInvalidoException | EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ============================
    // EDITAR PRODUCTO
    // ============================
    private void editar() {
        listar();
        System.out.print("ID a editar: ");
        Long id = leerLong();

        Producto p = productoService.buscarPorId(id);
        if (p == null || p.isEliminado()) {
            System.out.println("Producto no encontrado o eliminado.");
            return;
        }

        System.out.print("Nuevo precio (enter para mantener): ");
        String precioStr = scanner.nextLine();
        Double precio = precioStr.isEmpty() ? null : Double.parseDouble(precioStr);

        System.out.print("Nuevo stock (enter para mantener): ");
        String stockStr = scanner.nextLine();
        Integer stock = stockStr.isEmpty() ? null : Integer.parseInt(stockStr);

        System.out.println("\nCategorías disponibles:");
        categoriaService.listar().forEach(System.out::println);

        System.out.print("Nueva categoría (enter para mantener): ");
        String catStr = scanner.nextLine();
        Categoria categoria = null;

        if (!catStr.isEmpty()) {
            Long idCat = Long.parseLong(catStr);
            categoria = categoriaService.buscarPorId(idCat);

            if (categoria == null || categoria.isEliminado()) {
                System.out.println("Categoría inválida.");
                return;
            }
        }

        try {
            productoService.editar(id, precio, stock, categoria);
            System.out.println("Producto actualizado.");
        } catch (DatoInvalidoException | EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ============================
    // ELIMINAR PRODUCTO
    // ============================
    private void eliminar() {
        listar();
        System.out.print("ID a eliminar: ");
        Long id = leerLong();

        System.out.print("Confirmar (S/N): ");
        String conf = scanner.nextLine();

        if (conf.equalsIgnoreCase("S")) {
            try {
                productoService.eliminar(id);
                System.out.println("Producto eliminado.");
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

    private Double leerDouble() {
        try { return Double.parseDouble(scanner.nextLine()); }
        catch (Exception e) { return -1.0; }
    }

    private Long leerLong() {
        try { return Long.parseLong(scanner.nextLine()); }
        catch (Exception e) { return -1L; }
    }
}
