/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.service;

import Prog2.entities.Categoria;
import Prog2.entities.Producto;
import Prog2.exception.DatoInvalidoException;
import Prog2.exception.EntidadNoEncontradaException;
import Prog2.utils.Validaciones;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author magae
 */

/**
 * Servicio encargado de gestionar las operaciones relacionadas con Producto.
 * Implementa las historias de usuario HU-PROD-01 a HU-PROD-04.
 */
public class ProductoService {

    // Lista interna que almacena todos los productos (activos y eliminados).
    private List<Producto> productos;

    // Constructor
    public ProductoService() {
        this.productos = new ArrayList<>();
    }

    // ============================
    // LISTAR (HU-PROD-01)
    // Devuelve solo productos activos (no eliminados).
    // ============================
    public List<Producto> listar() {
        List<Producto> activos = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }

    // ============================
    // LISTAR POR CATEGORÍA
    // Devuelve productos activos pertenecientes a una categoría activa.
    // ============================
    public List<Producto> listarPorCategoria(Categoria categoria) {
        List<Producto> activosPorCategoria = new ArrayList<>();

        if (categoria == null || categoria.isEliminado()) {
            return activosPorCategoria;
        }

        for (Producto p : productos) {
            if (!p.isEliminado() && p.getCategoria().getId().equals(categoria.getId())) {
                activosPorCategoria.add(p);
            }
        }

        return activosPorCategoria;
    }

    // ============================
    // BUSCAR POR ID
    // Devuelve el producto si existe y no está eliminado.
    // ============================
    public Producto buscarPorId(Long id) {
        for (Producto p : productos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        return null;
    }

    // ============================
    // CREAR (HU-PROD-02)
    // Valida datos, verifica categoría válida y crea un nuevo producto.
    // ============================
    public Producto crear(String nombre, Double precio, String descripcion, int stock,
                          String imagen, boolean disponible, Categoria categoria) {

        Validaciones.validarString(nombre, "Nombre");
        Validaciones.validarString(descripcion, "Descripción");
        Validaciones.validarDoublePositivo(precio, "Precio");
        Validaciones.validarEnteroPositivo(stock, "Stock");
        Validaciones.validarString(imagen, "Imagen");

        // Regla de negocio: la categoría debe existir y no estar eliminada
        if (categoria == null || categoria.isEliminado()) {
            throw new EntidadNoEncontradaException("La categoría no existe o está eliminada.");
        }

        Producto nuevo = new Producto(
            nombre,
            precio,
            descripcion,
            stock,
            imagen,
            disponible,
            categoria
        );

        productos.add(nuevo);
        categoria.agregarProducto(nuevo); // composición

        return nuevo;
    }

    // ============================
    // EDITAR (HU-PROD-03)
    // Permite editar precio, stock o categoría si los parámetros no son null.
    // ============================
    public boolean editar(Long id, Double precio, Integer stock, Categoria categoria) {

        Producto producto = buscarPorId(id);

        if (producto == null) {
            throw new EntidadNoEncontradaException("El producto no existe o está eliminado.");
        }

        // Editar precio si se envía
        if (precio != null) {
            Validaciones.validarDoublePositivo(precio, "Precio");
            producto.setPrecio(precio);
        }

        // Editar stock si se envía
        if (stock != null) {
            Validaciones.validarEnteroPositivo(stock, "Stock");
            producto.setStock(stock);
        }

        // Editar categoría si se envía
        if (categoria != null) {
            if (categoria.isEliminado()) {
                throw new DatoInvalidoException("La categoría está eliminada.");
            }
            producto.setCategoria(categoria);
        }

        return true;
    }

    // ============================
    // ELIMINAR (HU-PROD-04)
    // Baja lógica del producto.
    // ============================
    public boolean eliminar(Long id) {

        Producto producto = buscarPorId(id);

        if (producto == null) {
            throw new EntidadNoEncontradaException("El producto no existe o ya está eliminado.");
        }

        producto.setEliminado(true); // baja lógica
        return true;
    }
}
