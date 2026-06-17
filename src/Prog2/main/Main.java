/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.main;

import Prog2.main.Menu;

/**
 *
 * @author magae
 */

import Prog2.entities.Categoria;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();

        // Datos de prueba para el video
        menu.getCategoriaService().crear("Hamburguesas", "Variedad de hamburguesas");
        menu.getCategoriaService().crear("Bebidas", "Refrescos y jugos naturales");

        Categoria cat1 = menu.getCategoriaService().buscarPorId(1L);
        Categoria cat2 = menu.getCategoriaService().buscarPorId(2L);

        menu.getProductoService().crear("Cheeseburger", 2500.0, "Hamburguesa con queso", 10, "img1.jpg", true, cat1);
        menu.getProductoService().crear("Coca-Cola", 1200.0, "Bebida gaseosa", 20, "img2.jpg", true, cat2);

        menu.getUsuarioService().crear("Juan", "Pérez", "juan@mail.com", "1122334455");
        menu.getUsuarioService().crear("Ana", "López", "ana@mail.com", "1199887766");

        // Iniciar menú
        menu.iniciar();
    }
}


