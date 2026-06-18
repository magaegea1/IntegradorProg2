/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.entities;

/**
 *
 * @author magae
 */

/**
 * Entidad que representa un producto dentro del sistema.
 * Hereda ID, eliminado y createdAt desde Base.
 */
public class Producto extends Base {

    // Atributos
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;
    private Categoria categoria;

    // Constructor completo
    public Producto(String nombre, Double precio, String descripcion, int stock,
                    String imagen, boolean disponible, Categoria categoria) {
        super();
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.imagen = imagen;
        this.disponible = disponible;
        this.categoria = categoria;
    }

    // Constructor vacío necesario para menú
    public Producto() {
        super();
        this.nombre = "";
        this.descripcion = "";
        this.imagen = "";
        this.categoria = null;
        this.disponible = true;
        this.stock = 0;
        this.precio = 0.0;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getStock() {
        return stock;
    }

    public String getImagen() {
        return imagen;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    // toString
    @Override
    public String toString() {
        return "Producto{" +
                "\n\tID = " + getId() +
                "\n\tNombre = " + nombre +
                "\n\tPrecio = " + precio +
                "\n\tDescripcion = " + descripcion +
                "\n\tStock = " + stock +
                "\n\tImagen = " + imagen +
                "\n\tDisponible = " + disponible +
                "\n\tCategoria = " + (categoria != null ? categoria.getNombre() : "Sin categoría") +
                "\n}";
    }
}
