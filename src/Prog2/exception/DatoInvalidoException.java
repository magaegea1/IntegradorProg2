/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.exception;

/**
 *
 * @author magae
 */

/**
 * Excepción propia utilizada para indicar errores de validación
 * o datos inválidos ingresados por el usuario.
 */

public class DatoInvalidoException extends RuntimeException {

    public DatoInvalidoException(String mensaje) {
        super(mensaje);
    }

    public DatoInvalidoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
