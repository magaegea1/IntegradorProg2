/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author magae
 */

public class Categoria extends Base {

    // Atributos propios
    private String nombre;
    private String descripcion;
    private List<Producto> productos;

    // Constructor completo
    public Categoria(String nombre, String descripcion) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = new ArrayList<>();
    }

    // Constructor vacío (necesario para menú)
    public Categoria() {
        super();
        this.productos = new ArrayList<>();
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    // Setters (sin validaciones internas)
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Método propio (sin validaciones ni lógica cruzada)
    public void agregarProducto(Producto producto) {
        if (producto != null) {
            this.productos.add(producto);
        }
    }

    // toString
    @Override
    public String toString() {
        return String.format(
            "Categoria{id=%d, nombre='%s', descripcion='%s', productos=%d}",
            getId(), nombre, descripcion, productos.size()
        );
    }
}