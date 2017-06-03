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
public class Cliente {

    String estado;
    float horaLlegada;
    float horaPartida;

    public Cliente(String estado, float horaLlegada, float horaPartida) {
        this.estado = estado;
        this.horaLlegada = horaLlegada;
        this.horaPartida = horaPartida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(float horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public float getHoraPartida() {
        return horaPartida;
    }

    public void setHoraPartida(float horaPartida) {
        this.horaPartida = horaPartida;
    }
    
}
