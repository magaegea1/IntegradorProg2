/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.main;

import Prog2.entities.Categoria;
import Prog2.entities.Producto;
import Prog2.service.CategoriaService;
import Prog2.service.ProductoService;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author magae
 */


public class MenuProductos {

    private Scanner scanner;
    private ProductoService productoService;
    private CategoriaService categoriaService;

    public MenuProductos(Scanner scanner, ProductoService productoService, CategoriaService categoriaService) {
        this.scanner = scanner;
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    public void iniciar() {
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
                case 1 -> listar();
                case 2 -> crear();
                case 3 -> editar();
                case 4 -> eliminar();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    private void listar() {
        List<Producto> lista = productoService.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }
        lista.forEach(System.out::println);
    }

    private void crear() {
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

        categoriaService.listar().forEach(System.out::println);
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

    private void editar() {
        listar();
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

    private void eliminar() {
        listar();
        System.out.print("ID a eliminar: ");
        Long id = leerLong();

        System.out.print("Confirmar (S/N): ");
        String conf = scanner.nextLine();

        if (conf.equalsIgnoreCase("S")) {
            boolean ok = productoService.eliminar(id);
            System.out.println(ok ? "Producto eliminado." : "No se pudo eliminar.");
        }
    }

    private int leerEntero() {
        try { return Integer.parseInt(scanner.nextLine()); }
        catch (Exception e) { return -1; }
    }

    private Long leerLong() {
        try { return Long.parseLong(scanner.nextLine()); }
        catch (Exception e) { return -1L; }
    }

    private Double leerDouble() {
        try { return Double.parseDouble(scanner.nextLine()); }
        catch (Exception e) { return -1.0; }
    }
}

