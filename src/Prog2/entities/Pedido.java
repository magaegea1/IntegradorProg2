/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import Prog2.enums.Estado;
import Prog2.enums.FormaPago;
import Prog2.interfaces.Calculable;

/**
 *
 * @author magae
 */

public class Pedido extends Base implements Calculable {

    // Atributos
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private List<DetallePedido> detalles;
    private Usuario usuario;

    // Constructor completo
    public Pedido(Usuario usuario, FormaPago formaPago) {
        super();
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.usuario = usuario;
        this.formaPago = formaPago;
        this.detalles = new ArrayList<>();
        this.total = 0.0;
    }

    // Constructor vacío para menú
    public Pedido() {
        super();
        this.detalles = new ArrayList<>();
        this.total = 0.0;
    }

    // Getters
    public LocalDate getFecha() {
        return fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public Double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    // Setters
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    // ============================================================
    // IMPLEMENTACIÓN DE Calculable (OBLIGATORIO)
    // ============================================================
    @Override
    public void calcularTotal() {
        double suma = 0.0;
        for (DetallePedido det : detalles) {
            if (!det.isEliminado()) {
                suma += det.getSubtotal();
            }
        }
        this.total = suma;
    }

    // ============================================================
    // MÉTODO OBLIGATORIO DEL UML: addDetallePedido
    // ============================================================
    public void addDetallePedido(int cantidad, Double subtotal, Producto producto) {
        DetallePedido det = new DetallePedido(cantidad, subtotal, producto);
        this.detalles.add(det);
    }

    // ============================================================
    // MÉTODO OBLIGATORIO DEL UML: findDetallePedidoByProducto
    // ============================================================
    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        for (DetallePedido d : detalles) {
            if (d.getProducto() != null && d.getProducto().equals(producto)) {
                return d;
            }
        }
        return null;
    }

    // ============================================================
    // MÉTODO OBLIGATORIO DEL UML: deleteDetallePedidoByProducto
    // ============================================================
    public void deleteDetallePedidoByProducto(Producto producto) {
        detalles.removeIf(d -> d.getProducto() != null && d.getProducto().equals(producto));
    }

    // ============================================================
    // COMPOSICIÓN (OPCIONAL PERO PROFESIONAL)
    // Si el pedido se elimina, sus detalles también.
    // ============================================================
    public void eliminarDetalles() {
        for (DetallePedido d : detalles) {
            d.setEliminado(true);
        }
    }

    // toString
    @Override
    public String toString() {
        return String.format(
            "Pedido{id=%d, fecha=%s, estado=%s, formaPago=%s, total=%.2f, detalles=%d, usuario=%s}",
            getId(),
            fecha,
            estado,
            formaPago,
            total,
            detalles.size(),
            usuario != null ? usuario.getNombre() + " " + usuario.getApellido() : "Sin usuario"
        );
    }
}
