/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.service;

import Prog2.entities.DetallePedido;
import Prog2.entities.Pedido;
import Prog2.entities.Usuario;
import Prog2.enums.Estado;
import Prog2.enums.FormaPago;
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
    public Pedido buscarPorId(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    // ============================
    // CREAR (HU-PED-02)
    // ============================
    public Pedido crear(int idUsuario, List<DetallePedido> detalles, FormaPago formaPago) {

        // 1. Validar usuario
        Usuario usuario = usuarioService.buscarPorId(idUsuario);
        if (usuario == null || usuario.isEliminado()) {
            throw new IllegalArgumentException("El usuario no existe o está eliminado.");
        }

        // 2. Crear pedido vacío
        Pedido nuevo = new Pedido(usuario, formaPago);

        try {
            // 3. Agregar detalles uno por uno
            for (DetallePedido d : detalles) {
                agregarDetalle(nuevo, d.getProducto().getId(), d.getCantidad());
            }

            // 4. Calcular total usando la interfaz Calculable
            nuevo.calcularTotal();

            // 5. Agregar a la colección
            pedidos.add(nuevo);

            return nuevo;

        } catch (Exception e) {
            // 6. Si algo falla, cancelar creación
            return null;
        }
    }


    // ============================
    // AGREGAR DETALLE (interno)
    // ============================
    private void agregarDetalle(Pedido pedido, int idProducto, int cantidad) {
        // 1. Validar producto
        // 2. Validar stock
        // 3. Crear detalle
        // 4. pedido.addDetallePedido(...)
    }

    // ============================
    // ACTUALIZAR ESTADO / FORMA DE PAGO (HU-PED-03)
    // ============================
    public boolean actualizar(int idPedido, Estado nuevoEstado, FormaPago nuevaFormaPago) {

        Pedido p = buscarPorId(idPedido);

        if (p == null || p.isEliminado()) {
            return false;
        }

        // Actualizar estado y/o forma de pago

        return true;
    }

    // ============================
    // ELIMINAR (HU-PED-04)
    // ============================
    public boolean eliminar(int id) {

        Pedido p = buscarPorId(id);

        if (p == null || p.isEliminado()) {
            return false;
        }

        p.setEliminado(true);

        // Opcional: marcar detalles como eliminados también

        return true;
    }
}
/*1. Completar PedidoService — método CREAR()
Este es el más largo, pero lo hacemos juntas paso a paso.

Incluye:

validar usuario

crear pedido vacío

agregar detalles

validar stock

calcular total

manejar errores

agregar a la colección

Yo te voy a dar el código exacto, no te preocupes.

2. Completar método interno agregarDetalle()
Este método es clave porque:

valida producto

valida stock

crea detalle

usa addDetallePedido()

También te lo doy yo.

3. Completar actualizar() (estado y forma de pago)
Este es cortito.

4. Completar eliminar()
Ya lo tenés casi listo.*/