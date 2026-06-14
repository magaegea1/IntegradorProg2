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
    public Pedido(FormaPago formaPago, Usuario usuario) {
        super();
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.formaPago = formaPago;
        this.usuario = usuario;
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
        return detalles; // importante: NO copyOf()
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

    // Método de la interfaz (vacío)
    @Override
    public void calcularTotal() {
        // la lógica va en PedidoService
    }

    // toString
    @Override
    public String toString() {
        return String.format(
            "Pedido{id=%d, fecha=%s, estado=%s, formaPago=%s, total=%.2f}",
            getId(), fecha, estado, formaPago, total
        );
    }
}