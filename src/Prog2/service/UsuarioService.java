/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.service;

import Prog2.entities.Usuario;
import Prog2.enums.Rol;
import Prog2.utils.Validaciones;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author magae
 */
public class UsuarioService {
    // Atributos
    private List<Usuario> usuarios;

    // Constructor
    public UsuarioService() {
        this.usuarios = new ArrayList<>();
    }

    // ============================
    // LISTAR (HU-USR-01)
    // ============================
    public List<Usuario> listar() {
        List<Usuario> activos = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (!u.isEliminado()) {
                activos.add(u);
            }
        }
        return activos;
    }

    // ============================
    // CREAR HU-USR-02
    // ============================

    public Usuario crear(String nombre, String apellido, String mail, String celular) {

        Validaciones.validarString(nombre, "Nombre/s del usuario");
        Validaciones.validarString(apellido, "Apellido/s del usuario");
        Validaciones.validarEmailBasico(mail);
        Validaciones.validarString(celular, "Celular del usuario");
       
        
        // Validar mail único
        if (existeMail(mail)) {
            throw new IllegalArgumentException("Ya existe un usuario con ese mail.");
        }

        // Contraseña inicial = celular del usuario. 
        // Luego podrá cambiarla cuando el sistema implemente login (no requerido en este TPI).

        String contraseniaInicial = celular; 

        Usuario nuevo = new Usuario(
            nombre,
            apellido,
            mail,
            celular,
            contraseniaInicial,
            Rol.USUARIO
        );
        
        usuarios.add(nuevo);
        
        return nuevo;
    }
    
    // Método para saber si ya existe el mail
    private boolean existeMail(String mail) {
        for (Usuario u : usuarios) {
            if(!u.isEliminado() && u.getMail().equalsIgnoreCase(mail))
                return true;
        }
        return false;
    }

    // ============================
    // BUSCAR POR ID
    // ============================
    public Usuario buscarPorId(Long id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    // ============================
    // EDITAR (HU-USR-03)
    // ============================

    public boolean editar(Long id, String nuevoNombre, String nuevoApellido, 
        String nuevoMail, String nuevoCelular) {

        Usuario u = buscarPorId(id);

        if (u == null || u.isEliminado()) {
            return false;
        }

        if (nuevoNombre != null) {
            // Validaciones
            Validaciones.validarString(nuevoNombre, "Nombre/s del usuario");
            u.setNombre(nuevoNombre);            
        }
        
        if (nuevoApellido != null) {
            // Validaciones
            Validaciones.validarString(nuevoApellido, "Apellido/s del usuario");
            u.setApellido(nuevoApellido);            
        }

        if (nuevoMail != null) {
            // Valida el formato válido del mail
            Validaciones.validarEmailBasico(nuevoMail);
            
            // Valida que el usuario no haya ingresado el mismo mail que ya tenía
            if (!nuevoMail.equalsIgnoreCase(u.getMail())) {
                // Valida que el mail sea único
                if (existeMail(nuevoMail)) {
                    throw new IllegalArgumentException("Ya existe un usuario con ese mail.");
                }
                u.setMail(nuevoMail);
            }
        }

        if (nuevoCelular != null) {
            // Validaciones
            Validaciones.validarString(nuevoCelular, "Celular del usuario");
            u.setCelular(nuevoCelular);            
        }

        return true;
    }

    // ============================
    // ELIMINAR (HU-USR-04)
    // ============================

    public boolean eliminar(Long id) {

        Usuario u = buscarPorId(id);

        if (u == null || u.isEliminado()) {
            return false;
        }

        u.setEliminado(true);
        return true;
    }
}