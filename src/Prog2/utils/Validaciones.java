/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.utils;

import Prog2.exception.DatoInvalidoException;

/**
 *
 * @author magae
 */

/**
 * Clase utilitaria para validaciones de datos de entrada.
 * Contiene métodos estáticos y no puede ser instanciada.
 */
public class Validaciones {

    // Constructor privado para evitar instanciación
    private Validaciones() {}

     // Valida que un String no sea nulo ni esté vacío.
    public static void validarString(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new DatoInvalidoException(campo + " no puede estar vacío.");
        }
    }

     // Valida que un número Double sea positivo o cero.
    public static void validarDoublePositivo(Double valor, String campo) {
        if (valor == null || valor < 0) {
            throw new DatoInvalidoException(campo + " debe ser un número positivo.");
        }
    }

     // Valida que un número entero sea positivo o cero.
    public static void validarEnteroPositivo(Integer valor, String campo) {
        if (valor == null || valor < 0) {
            throw new DatoInvalidoException(campo + " debe ser un entero positivo.");
        }
    }

    // Validación básica de formato de email.
    public static void validarEmailBasico(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new DatoInvalidoException("El email no puede estar vacío.");
        }

        int arroba = email.indexOf("@");
        int punto = email.lastIndexOf(".");

        if (arroba < 1 || punto < arroba + 2 || punto == email.length() - 1) {
            throw new DatoInvalidoException("Formato de email inválido.");
        }
    }
}
