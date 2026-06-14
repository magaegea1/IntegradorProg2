/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package Prog2.entities;

import java.time.LocalDateTime;

/**
 *
 * @author magae
 */
/**
 * Clase base abstracta para todas las entidades del sistema.
 * Provee ID autogenerado, marca de eliminado y timestamp de creación.
 */

public abstract class Base {
    
    // Atributos
    private static long contadorId = 1; // autoincremento interno
    
    private Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;
    
    // Constructor sin parámetros → genera ID automáticamente
    protected Base() {
        this.id = nextId();
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }
    
    // Generador sincronizado para evitar problemas en hilos
    private static synchronized Long nextId() {
        return contadorId++;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public boolean isEliminado() {
        return eliminado;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    // Setters
    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    // Métodos
    //toString
    @Override
    public abstract String toString();
}
