/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.main;

import Prog2.entities.Usuario;
import Prog2.service.UsuarioService;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author magae
 */

public class MenuUsuarios {

    private Scanner scanner;
    private UsuarioService usuarioService;

    public MenuUsuarios(Scanner scanner, UsuarioService usuarioService) {
        this.scanner = scanner;
        this.usuarioService = usuarioService;
    }

    public void iniciar() {
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

    private void crear() {
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

    private void editar() {
        listar();
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

    private void eliminar() {
        listar();
        System.out.print("ID a eliminar: ");
        Long id = leerLong();

        System.out.print("Confirmar (S/N): ");
        String conf = scanner.nextLine();

        if (conf.equalsIgnoreCase("S")) {
            boolean ok = usuarioService.eliminar(id);
            System.out.println(ok ? "Usuario eliminado." : "No se pudo eliminar.");
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
}

