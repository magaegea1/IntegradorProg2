/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.entities;

/**
 *
 * @author magae
 */

public class DetallePedido extends Base {

    // Atributos
    private int cantidad;
    private Double subtotal;
    private Producto producto;

    // Constructor completo
    public DetallePedido(int cantidad, Double subtotal, Producto producto) {
        super();
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.producto = producto;
    }

    // Constructor vacío para el menú
    public DetallePedido() {
        super();
    }

    // Getters
    public int getCantidad() {
        return cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    // Setters (sin lógica de negocio)
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    // toString seguro
    @Override
    public String toString() {
        return String.format(
            "DetallePedido{id=%d, producto='%s', cantidad=%d, subtotal=%.2f}",
            getId(),
            producto != null ? producto.getNombre() : "Sin producto",
            cantidad,
            subtotal != null ? subtotal : 0.0
        );
    }
}