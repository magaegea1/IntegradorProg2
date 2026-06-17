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
public class ProductoService {
    // Atributos
    private List<Producto> productos;

    // Constructor
    public ProductoService() {
        this.productos = new ArrayList<>();
    }

    // Métodos
    // CRUD 
    
    // ============================
    // LISTAR (HU-PROD-01)
    // ============================
    //  1.- Listar
    public List<Producto> listar() {
        List<Producto> activos = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }

    //  2.- Listar por categoría
    public List<Producto> listarPorCategoria(Categoria categoria) {
        List<Producto> activosPorCategoria = new ArrayList<>();

        if (categoria == null || categoria.isEliminado()) {
            return activosPorCategoria;
        }

        for (Producto p : productos) {
            if (!p.isEliminado() && p.getCategoria().getId() == categoria.getId()) {
                activosPorCategoria.add(p);
            }
        }

        return activosPorCategoria;
    }

    
    // ============================
    // BUSCAR POR ID
    // ============================
    public Producto buscarPorId(Long id) {
        for (Producto p : productos) {
            if (p.getId().equals(id))
                return p;
        }
        return null;
    }


    // ============================
    // CREAR (HU-PROD-02)
    // ============================
 
    public Producto crear(String nombre, Double precio, String descripcion, int stock,
                          String imagen, boolean disponible, Categoria categoria) {

        // Validaciones básicas
        Validaciones.validarString(nombre, "Nombre");
        Validaciones.validarString(descripcion, "Descripción");
        Validaciones.validarDoublePositivo(precio, "Precio");
        Validaciones.validarEnteroPositivo(stock, "Stock");
        Validaciones.validarString(imagen, "Imagen");

        // Validar categoría
        if (categoria == null || categoria.isEliminado()) {
            throw new IllegalArgumentException("La categoría no existe o está eliminada.");
        }

        // Crear producto
        Producto nuevo = new Producto(
                nombre,
                precio,
                descripcion,
                stock,
                imagen,
                disponible,
                categoria
        );

        // Agregar a la colección de productos
        productos.add(nuevo);

        // VINCULAR A LA CATEGORÍA
        categoria.agregarProducto(nuevo);

        return nuevo;
    }


    // ============================
    // EDITAR HU-PROD-03
    // ============================
    // Cambiar tipos primitivos a tipos objeto para permitir null

    public boolean editar(Long id, Double precio, Integer stock, Categoria categoria) {

        Producto producto = buscarPorId(id);

        if (producto == null || producto.isEliminado()) {
            return false;
        }
                
        // Validar sólo si no es null
        // Editar Precio
        if (precio != null) {
            Validaciones.validarDoublePositivo(precio, "Precio");
            // Editar precio del  producto
            producto.setPrecio(precio);
        }
        
        // Editar Stock
        if (stock != null) {
            Validaciones.validarEnteroPositivo(stock, "Stock");
            // Editar stock del  producto
            producto.setStock(stock);
        }
        
        // Editar categoría
        if (categoria != null){ 
            if (categoria.isEliminado()){
            throw new IllegalArgumentException("La categoría no existe o está eliminada.");
            }
            producto.setCategoria(categoria);
        }

        return true;
    }
    
    // ============================
    // ELIMINAR (HU-PROD-02)
    // ============================
    // Servicio producto no se encarga de revisar si un producto está en algún pedido porque
    // no tiene acceso por la arquitectura del diseño

    public boolean eliminar(Long id) {
        Producto producto = buscarPorId(id);

        if (producto == null || producto.isEliminado()) {
            return false;
        }

        producto.setEliminado(true);
        return true;
    }
    
    
}
