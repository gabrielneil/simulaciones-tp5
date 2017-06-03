/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author gabrielneil
 */
public class Servidor {
    String estado;
    int cola;

    public Servidor() {
        estado = "LIBRE";
        cola = 0;
    }

    public Servidor(String estado, int cola) {
        this.estado = estado;
        this.cola = cola;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCola() {
        return cola;
    }

    public void setCola(int cola) {
        this.cola = cola;
    }
    
    
    
}
