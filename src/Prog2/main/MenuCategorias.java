/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.main;

import Prog2.entities.Categoria;
import Prog2.exception.DatoInvalidoException;
import Prog2.exception.EntidadNoEncontradaException;
import Prog2.exception.OperacionNoPermitidaException;
import Prog2.service.CategoriaService;
import Prog2.service.ProductoService;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author magae
 */

/**
 * Menú de gestión de Categorías.
 * Implementa las opciones de listar, crear, editar y eliminar categorías.
 */
public class MenuCategorias {

    // Scanner compartido desde el menú principal
    private Scanner scanner;

    // Servicios necesarios
    private CategoriaService categoriaService;
    private ProductoService productoService;

    // Constructor
    public MenuCategorias(Scanner scanner, CategoriaService categoriaService, ProductoService productoService) {
        this.scanner = scanner;
        this.categoriaService = categoriaService;
        this.productoService = productoService;
    }

    // ============================
    // MENÚ PRINCIPAL DE CATEGORÍAS
    // ============================
    public void iniciar() {
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
                case 1 -> listar();
                case 2 -> crear();
                case 3 -> editar();
                case 4 -> eliminar();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    // ============================
    // LISTAR CATEGORÍAS
    // ============================
    private void listar() {
        List<Categoria> lista = categoriaService.listar();
        if (lista.isEmpty()) {
            System.out.println("No hay categorías cargadas.");
            return;
        }
        lista.forEach(System.out::println);
    }

    // ============================
    // CREAR CATEGORÍA
    // ============================
    private void crear() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        try {
            Categoria nueva = categoriaService.crear(nombre, descripcion);
            System.out.println("Categoría creada con ID: " + nueva.getId());
        } catch (DatoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ============================
    // EDITAR CATEGORÍA
    // ============================
    private void editar() {
        listar();
        System.out.print("ID a editar: ");
        Long id = leerLong();

        try {
            Categoria actual = categoriaService.buscarPorId(id);
            if (actual == null) {
                throw new EntidadNoEncontradaException("La categoría no existe o está eliminada.");
            }

            System.out.print("Nuevo nombre (enter para mantener): ");
            String nombre = scanner.nextLine();

            System.out.print("Nueva descripción (enter para mantener): ");
            String descripcion = scanner.nextLine();

            // Mantener valores actuales si el usuario deja vacío
            if (nombre.isEmpty()) nombre = actual.getNombre();
            if (descripcion.isEmpty()) descripcion = actual.getDescripcion();

            categoriaService.editar(id, nombre, descripcion);
            System.out.println("Categoría actualizada.");

        } catch (DatoInvalidoException | EntidadNoEncontradaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ============================
    // ELIMINAR CATEGORÍA
    // ============================
    private void eliminar() {
        listar();
        System.out.print("ID a eliminar: ");
        Long id = leerLong();

        System.out.print("Confirmar (S/N): ");
        String conf = scanner.nextLine();

        if (conf.equalsIgnoreCase("S")) {
            try {
                categoriaService.eliminar(id, productoService.listar());
                System.out.println("Categoría eliminada.");
            } catch (EntidadNoEncontradaException | OperacionNoPermitidaException e) {
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
