/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.service;

import Prog2.entities.Categoria;
import Prog2.entities.Producto;
import Prog2.utils.Validaciones;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author magae
 */

public class CategoriaService {
    // Atributos
    private List<Categoria> categorias;

    // Constructor
    public CategoriaService() {
        this.categorias = new ArrayList<>();
    }

    // ============================
    // LISTAR (HU-CAT-01)
    // ============================
    public List<Categoria> listar() {
        List<Categoria> activas = new ArrayList<>();
        for (Categoria c : categorias) {
            if (!c.isEliminado()) {
                activas.add(c);
            }
        }
        return activas;
    }

    // ============================
    // CREAR (HU-CAT-02)
    // ============================
    public Categoria crear(String nombre, String descripcion) {

        Validaciones.validarString(nombre, "Nombre de categoría");
        Validaciones.validarString(descripcion, "Descripción de categoría");

        // Validar nombre único
        for (Categoria c : categorias) {
            if (!c.isEliminado() && c.getNombre().equalsIgnoreCase(nombre)) {
                throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
            }
        }

        Categoria nueva = new Categoria(nombre, descripcion);
        categorias.add(nueva);
        return nueva;
    }

    // ============================
    // BUSCAR POR ID
    // ============================
    public Categoria buscarPorId(int id) {
        for (Categoria c : categorias) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    // ============================
    // EDITAR (HU-CAT-03)
    // ============================
    public boolean editar(int id, String nuevoNombre, String nuevaDescripcion) {

        Categoria categoria = buscarPorId(id);

        if (categoria == null || categoria.isEliminado()) {
            return false;
        }

        // Validaciones
        Validaciones.validarString(nuevoNombre, "Nombre de categoría");
        Validaciones.validarString(nuevaDescripcion, "Descripción de categoría");

        // Validar nombre único (excepto la misma categoría)
        for (Categoria c : categorias) {
            if (!c.isEliminado()
                    && c.getNombre().equalsIgnoreCase(nuevoNombre)
                    && c.getId() != id) {
                throw new IllegalArgumentException("Ya existe otra categoría con ese nombre.");
            }
        }

        categoria.setNombre(nuevoNombre);
        categoria.setDescripcion(nuevaDescripcion);

        return true;
    }

    // ============================
    // ELIMINAR (HU-CAT-04)
    // ============================
    public boolean eliminar(int id, List<Producto> productos) {

        Categoria categoria = buscarPorId(id);

        if (categoria == null || categoria.isEliminado()) {
            return false;
        }

        // Regla de negocio: impedir baja si tiene productos asociados
        for (Producto p : productos) {
            if (!p.isEliminado() && p.getCategoria().getId() == id) {
                throw new IllegalStateException(
                    "No se puede eliminar la categoría porque tiene productos asociados."
                );
            }
        }

        categoria.setEliminado(true);
        return true;
    }
}