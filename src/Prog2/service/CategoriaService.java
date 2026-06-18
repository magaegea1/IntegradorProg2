/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.service;

import Prog2.entities.Categoria;
import Prog2.entities.Producto;
import Prog2.exception.DatoInvalidoException;
import Prog2.exception.EntidadNoEncontradaException;
import Prog2.exception.OperacionNoPermitidaException;
import Prog2.utils.Validaciones;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author magae
 */

/**
 * Servicio encargado de gestionar las operaciones relacionadas con Categoría.
 * Implementa las historias de usuario HU-CAT-01 a HU-CAT-04.
 */

public class CategoriaService {
    
    // Lista interna que almacena todas las categorías (activas y eliminadas).
    private List<Categoria> categorias;
    
    // Constructor
    public CategoriaService() {
        this.categorias = new ArrayList<>();
    }

    // ============================
    // LISTAR (HU-CAT-01)
    // Devuelve solo categorías activas (no eliminadas).
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
    // Valida datos, verifica nombre único y crea una nueva categoría.
    // ============================
    public Categoria crear(String nombre, String descripcion) {

        Validaciones.validarString(nombre, "Nombre de categoría");
        Validaciones.validarString(descripcion, "Descripción de categoría");

        // Regla de negocio: nombre único entre categorías activas
        for (Categoria c : categorias) {
            if (!c.isEliminado() && c.getNombre().equalsIgnoreCase(nombre)) {
                throw new DatoInvalidoException("Ya existe una categoría con ese nombre.");
            }
        }

        Categoria nueva = new Categoria(nombre, descripcion);
        categorias.add(nueva);
        return nueva;
    }

    // ============================
    // BUSCAR POR ID
    // Devuelve la categoría si existe y no está eliminada.
    // ============================
    public Categoria buscarPorId(Long id) {
        for (Categoria c : categorias) {
            if (c.getId().equals(id) && !c.isEliminado()) {
                return c;
            }
        }
        return null;
    }

    // ============================
    // EDITAR (HU-CAT-03)
    // Valida existencia, datos y nombre único antes de modificar.
    // ============================
    public boolean editar(Long id, String nuevoNombre, String nuevaDescripcion) {

        Categoria categoria = buscarPorId(id);

        if (categoria == null) {
            throw new EntidadNoEncontradaException("La categoría no existe o está eliminada.");
        }

        Validaciones.validarString(nuevoNombre, "Nombre de categoría");
        Validaciones.validarString(nuevaDescripcion, "Descripción de categoría");

        // Regla de negocio: nombre único excepto para la misma categoría
        for (Categoria c : categorias) {
            if (!c.isEliminado()
                    && c.getNombre().equalsIgnoreCase(nuevoNombre)
                    && !c.getId().equals(id)) {
                throw new DatoInvalidoException("Ya existe otra categoría con ese nombre.");
            }
        }

        categoria.setNombre(nuevoNombre);
        categoria.setDescripcion(nuevaDescripcion);

        return true;
    }

    // ============================
    // ELIMINAR (HU-CAT-04)
    // Baja lógica. No permite eliminar si tiene productos asociados.
    // ============================
    public boolean eliminar(Long id, List<Producto> productos) {

        Categoria categoria = buscarPorId(id);

        if (categoria == null) {
            throw new EntidadNoEncontradaException("La categoría no existe o ya está eliminada.");
        }

        // Regla de negocio: impedir baja si tiene productos asociados
        for (Producto p : productos) {
            if (!p.isEliminado() && p.getCategoria().getId().equals(id)) {
                throw new OperacionNoPermitidaException(
                    "No se puede eliminar la categoría porque tiene productos asociados."
                );
            }
        }

        categoria.setEliminado(true); // baja lógica
        return true;
    }
}
