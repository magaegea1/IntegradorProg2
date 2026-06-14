/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.entities;

import Prog2.enums.Rol;
import Prog2.utils.Validaciones;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author magae
 */

public class Usuario extends Base {
    // Atributos
    private String nombre;  
    private String apellido;  
    private String mail;  
    private String celular;  
    private String contrasenia;  
    private Rol rol;  
    private List<Pedido> pedidos;
    
    // Constructor
    public Usuario(String nombre, String apellido, String mail, String celular,
            String contrasenia, Rol rol) {
        
        super();
            
        Validaciones.validarString(nombre, "nombre");
        Validaciones.validarString(apellido, "apellido");
        Validaciones.validarString(mail, "mail");
        Validaciones.validarString(celular, "celular");
        Validaciones.validarString(contrasenia, "contraseña");

        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contrasenia = contrasenia;

        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede ser nulo");
        }

        this.rol = rol;

        this.pedidos = new ArrayList<>();
    }
    
    // Métodos
    // Getters

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getMail() {
        return mail;
    }

    public String getCelular() {
        return celular;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }
    
    // Setters

    public void setNombre(String nombre) {
        Validaciones.validarString(nombre, "nombre");
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        Validaciones.validarString(apellido, "apellido");
        this.apellido = apellido;
    }

    public void setMail(String mail) {
        Validaciones.validarString(mail, "mail");
        this.mail = mail;
    }

    public void setCelular(String celular) {
        Validaciones.validarString(celular, "celular");
        this.celular = celular;
    }

    public void setContrasenia(String contrasenia) {
        Validaciones.validarString(contrasenia, "contraseña");
        this.contrasenia = contrasenia;
    }

    public void setRol(Rol rol) {
        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede ser nulo");
        }
        this.rol = rol;
    }
    
    // Métodos propios *******************Revisar métodos
    public void agregarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser nulo");
        }
        this.pedidos.add(pedido);
    }

    
    // Método para mostrar los pedidos
    public String mostrarPedidos() {
        StringBuilder pedidoDatos = new StringBuilder();

        for (Pedido p : pedidos) {
                       
            pedidoDatos.append("Fecha: ").append(p.getFecha())
                       .append(" | Estado: ").append(p.getEstado())
                       .append(" | FormaPago: ").append(p.getFormaPago())
                       .append("\n");
            }

        return String.format(
            "Usuario{id=%d, nombre='%s', apellido='%s', rol='%s', pedidos:\n%s}",
            getId(), nombre, apellido, rol, pedidoDatos.toString()
        );

    }
    
    // toString

    @Override
    public String toString() {
        return String.format(
            "Usuario{nombre='%s', apellido='%s', mail='%s', celular='%s', contraseña='%s', rol='%s', pedidos=%d}",
            nombre, apellido, mail, celular, contrasenia, rol, pedidos.size()
        );
    }
}
