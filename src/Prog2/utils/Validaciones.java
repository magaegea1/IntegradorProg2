/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prog2.utils;

/**
 *
 * @author magae
 */

/**
 * Clase para crear métodos que sirven para validar, es una clase utilitaria
 */
public class Validaciones {
    
    // Contructor que impide que se puedan instanciar objetos en esta clase
    private Validaciones() {
    }

    // Métodos
    // Validar String sirve para validar nombre, apellido, mail, descripción
    // celular, constraseña
    public static void validarString(String dato, String campo) {
        if (dato == null || dato.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo: " + campo + ", no puede estar vacío.");
        }
    }
    
    // Validar Double sirve para validar cantidades que deban reales positivos como precio
    public static void validarDoublePositivo(Double cantidad, String campo) {
        if (cantidad == null || cantidad < 0) {
            throw new IllegalArgumentException("El campo: " + campo + ", debe ser un número positivo");
        }
    }
    
    // Validar entero sirve para validar cantidades que deban ser enteros positivos como stock
    public static void validarEnteroPositivo(int cantidad, String campo) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("El campo: " + campo + ", no puede ser negativo");
        }        
    }   
        
}