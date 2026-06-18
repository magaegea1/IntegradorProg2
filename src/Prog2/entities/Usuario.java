/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.entities;

import Prog2.enums.Rol;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author magae
 */

/**
 * Entidad que representa un usuario del sistema.
 * Hereda ID, eliminado y createdAt desde Base.
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

    // Constructor completo
    public Usuario(String nombre, String apellido, String mail, String celular,
                   String contrasenia, Rol rol) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.pedidos = new ArrayList<>();
    }

    // Constructor vacío necesario para menú
    public Usuario() {
        super();
        this.nombre = "";
        this.apellido = "";
        this.mail = "";
        this.celular = "";
        this.contrasenia = "";
        this.rol = Rol.USUARIO; // valor por defecto
        this.pedidos = new ArrayList<>();
    }

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
        this.nombre = nombre; 
    }
    
    public void setApellido(String apellido) { 
        this.apellido = apellido; 
    }
    
    public void setMail(String mail) { 
        this.mail = mail; 
    }
    
    public void setCelular(String celular) { 
        this.celular = celular; 
    }
    
    public void setContrasenia(String contrasenia) { 
        this.contrasenia = contrasenia; 
    }
    
    public void setRol(Rol rol) { 
        this.rol = rol; 
    }

    /**
     * Agrega un pedido a la lista del usuario.
     * No valida null porque el Service ya lo hace.
     */
    
    public void agregarPedido(Pedido pedido) {
        if (pedido != null) {
            this.pedidos.add(pedido);
        }
    }

    // toString sin contraseña
    @Override
    public String toString() {
        return String.format(
            "Usuario{id=%d, nombre='%s', apellido='%s', mail='%s', celular='%s', rol='%s', pedidos=%d}",
            getId(), nombre, apellido, mail, celular,
            (rol != null ? rol : "SIN ROL"),
            pedidos.size()
        );
    }
}
