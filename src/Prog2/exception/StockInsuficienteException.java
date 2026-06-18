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
 * Excepción propia utilizada cuando se intenta realizar una operación
 * que requiere stock disponible y la cantidad solicitada supera el stock actual.
 */
public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }

    public StockInsuficienteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
