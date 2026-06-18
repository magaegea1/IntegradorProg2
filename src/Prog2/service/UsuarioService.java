/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.service;

import Prog2.entities.Usuario;
import Prog2.enums.Rol;
import Prog2.exception.DatoInvalidoException;
import Prog2.exception.EntidadNoEncontradaException;
import Prog2.utils.Validaciones;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author magae
 */

/**
 * Servicio encargado de gestionar las operaciones relacionadas con Usuario.
 * Implementa las historias de usuario HU-USR-01 a HU-USR-04.
 */

public class UsuarioService {
    
    // Lista interna que almacena todos los usuarios (activos y eliminados).
    private List<Usuario> usuarios;
    
    // Constructor
    public UsuarioService() {
        this.usuarios = new ArrayList<>();
    }

    // ============================
    // LISTAR (HU-USR-01)
    // Devuelve solo usuarios activos (no eliminados).
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
    // CREAR (HU-USR-02)
    // Valida datos, verifica mail único y crea un nuevo usuario.
    // La contraseña inicial es el celular, según la consigna.
    // ============================
    public Usuario crear(String nombre, String apellido, String mail, String celular) {

        Validaciones.validarString(nombre, "Nombre del usuario");
        Validaciones.validarString(apellido, "Apellido del usuario");
        Validaciones.validarEmailBasico(mail);
        Validaciones.validarString(celular, "Celular del usuario");

        // Regla de negocio: mail único entre usuarios activos
        if (existeMail(mail)) {
            throw new DatoInvalidoException("Ya existe un usuario con ese mail.");
        }

        // Contraseña inicial = celular (según HU-USR-02)
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

    // ============================
    // MÉTODO AUXILIAR
    // Verifica si existe un mail en usuarios activos.
    // ============================
    private boolean existeMail(String mail) {
        for (Usuario u : usuarios) {
            if (!u.isEliminado() && u.getMail().equalsIgnoreCase(mail)) {
                return true;
            }
        }
        return false;
    }

    // ============================
    // BUSCAR POR ID
    // Devuelve el usuario si existe y no está eliminado.
    // ============================
    public Usuario buscarPorId(Long id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id) && !u.isEliminado()) {
                return u;
            }
        }
        return null;
    }

    // ============================
    // EDITAR (HU-USR-03)
    // Permite editar nombre, apellido, mail o celular si los parámetros no son null.
    // ============================
    public boolean editar(Long id, String nuevoNombre, String nuevoApellido,
                          String nuevoMail, String nuevoCelular) {

        Usuario u = buscarPorId(id);

        if (u == null) {
            throw new EntidadNoEncontradaException("El usuario no existe o está eliminado.");
        }

        // Editar nombre
        if (nuevoNombre != null) {
            Validaciones.validarString(nuevoNombre, "Nombre del usuario");
            u.setNombre(nuevoNombre);
        }

        // Editar apellido
        if (nuevoApellido != null) {
            Validaciones.validarString(nuevoApellido, "Apellido del usuario");
            u.setApellido(nuevoApellido);
        }

        // Editar mail
        if (nuevoMail != null) {
            Validaciones.validarEmailBasico(nuevoMail);

            // Si el mail es distinto, validar unicidad
            if (!nuevoMail.equalsIgnoreCase(u.getMail())) {
                if (existeMail(nuevoMail)) {
                    throw new DatoInvalidoException("Ya existe un usuario con ese mail.");
                }
                u.setMail(nuevoMail);
            }
        }

        // Editar celular
        if (nuevoCelular != null) {
            Validaciones.validarString(nuevoCelular, "Celular del usuario");
            u.setCelular(nuevoCelular);
        }

        return true;
    }

    // ============================
    // ELIMINAR (HU-USR-04)
    // Baja lógica del usuario.
    // ============================
    public boolean eliminar(Long id) {

        Usuario u = buscarPorId(id);

        if (u == null) {
            throw new EntidadNoEncontradaException("El usuario no existe o ya está eliminado.");
        }

        u.setEliminado(true); // baja lógica
        return true;
    }
}

