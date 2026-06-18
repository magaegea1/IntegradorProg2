/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.service;

import Prog2.entities.DetallePedido;
import Prog2.entities.Pedido;
import Prog2.entities.Producto;
import Prog2.entities.Usuario;
import Prog2.enums.Estado;
import Prog2.enums.FormaPago;
import Prog2.exception.DatoInvalidoException;
import Prog2.exception.EntidadNoEncontradaException;
import Prog2.exception.StockInsuficienteException;
import Prog2.utils.Validaciones;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author magae
 */

public class PedidoService {

    // Atributos
    private List<Pedido> pedidos;
    private UsuarioService usuarioService;
    private ProductoService productoService;

    // Constructor
    public PedidoService(UsuarioService usuarioService, ProductoService productoService) {
        this.pedidos = new ArrayList<>();
        this.usuarioService = usuarioService;
        this.productoService = productoService;
    }

    // ============================
    // LISTAR (HU-PED-01)
    // ============================
    public List<Pedido> listar() {
        List<Pedido> activos = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isEliminado()) {
                activos.add(p);
            }
        }
        return activos;
    }

    // ============================
    // BUSCAR POR ID
    // ============================
    public Pedido buscarPorId(Long id) {
        for (Pedido p : pedidos) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    // ============================
    // CREAR (HU-PED-02)
    // ============================
    public Pedido crear(Long idUsuario, List<DetallePedido> detalles, FormaPago formaPago) {

        // 1. Validar usuario
        Usuario usuario = usuarioService.buscarPorId(idUsuario);
        if (usuario == null || usuario.isEliminado()) {
            throw new EntidadNoEncontradaException("El usuario no existe o está eliminado.");
        }

        // 2. Crear pedido vacío
        Pedido nuevo = new Pedido(usuario, formaPago);

        // 3. Agregar detalles uno por uno
        for (DetallePedido d : detalles) {
            agregarDetalle(nuevo, d.getProducto().getId(), d.getCantidad());
        }

        // 4. Calcular total
        calcularTotal(nuevo);

        // 5. Guardar pedido
        pedidos.add(nuevo);

        return nuevo;
    }

    // ============================
    // AGREGAR DETALLE (interno)
    // ============================
    private void agregarDetalle(Pedido pedido, Long idProducto, int cantidad) {

        // 1. Validar producto
        Producto prod = productoService.buscarPorId(idProducto);
        if (prod == null || prod.isEliminado()) {
            throw new EntidadNoEncontradaException("El producto no existe o está eliminado.");
        }

        // 2. Validar cantidad
        if (cantidad <= 0) {
            throw new DatoInvalidoException("La cantidad debe ser mayor a 0.");
        }

        // 3. Validar stock
        if (prod.getStock() < cantidad) {
            throw new StockInsuficienteException(
                "Stock insuficiente para el producto: " + prod.getNombre()
            );
        }

        // 4. Crear detalle
        double subtotal = prod.getPrecio() * cantidad;
        DetallePedido det = new DetallePedido(cantidad, subtotal, prod);

        // 5. Agregar al pedido (composición)
        pedido.getDetalles().add(det);
        pedido.addDetallePedido(cantidad, subtotal, prod);

        // 6. Descontar stock
        prod.setStock(prod.getStock() - cantidad);
    }

    // ============================
    // CALCULAR TOTAL
    // ============================
    private void calcularTotal(Pedido pedido) {
        double total = 0.0;

        for (DetallePedido det : pedido.getDetalles()) {
            total += det.getSubtotal();
        }

        pedido.setTotal(total);
    }

    // ============================
    // ACTUALIZAR (HU-PED-03)
    // ============================
    public boolean actualizar(Long idPedido, Estado nuevoEstado, FormaPago nuevaFormaPago) {

        Pedido p = buscarPorId(idPedido);

        if (p == null || p.isEliminado()) {
            throw new EntidadNoEncontradaException("El pedido no existe o está eliminado.");
        }

        if (nuevoEstado != null) {
            p.setEstado(nuevoEstado);
        }

        if (nuevaFormaPago != null) {
            p.setFormaPago(nuevaFormaPago);
        }

        return true;
    }

    // ============================
    // ELIMINAR (HU-PED-04)
    // ============================
    public boolean eliminar(Long id) {

        Pedido p = buscarPorId(id);

        if (p == null || p.isEliminado()) {
            throw new EntidadNoEncontradaException("El pedido no existe o ya está eliminado.");
        }

        p.setEliminado(true);
        return true;
    }
}
