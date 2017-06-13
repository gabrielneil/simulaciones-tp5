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

    String estado = "LIBRE";
    int cola;

    public Servidor() {
        cola = 0;
    }

    public Servidor(String estado, int cola) {
        this.estado = estado;
        this.cola = cola;
    }

    public String getEstado() {
        return estado;
    }

    public int getCola() {
        return cola;
    }

    public void setCola(int cola) {
        this.cola = cola;
    }
    public void aumentarCola() {
        cola++;
    }
    public void setOcupado() {
        this.estado = "OCUPADO";
    }

    public void setLibre() {
        this.estado = "LIBRE";
    }

    public void disminuirCola() {
        cola--;
    }

    public boolean estaLibre()
    {
        return "LIBRE".equals(estado);
    }

    public boolean estaOcupado()
    {
        return "OCUPADO".equals(estado);
    }

}
