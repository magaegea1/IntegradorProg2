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
 * Excepción propia utilizada cuando una operación solicitada
 * no está permitida según las reglas de negocio del sistema.
 */
public class OperacionNoPermitidaException extends RuntimeException {

    public OperacionNoPermitidaException(String mensaje) {
        super(mensaje);
    }

    public OperacionNoPermitidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
