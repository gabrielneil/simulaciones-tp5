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
    double horaLlegada;
    double horaPartida;

    public Cliente() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(double horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public double getHoraPartida() {
        return horaPartida;
    }

    public void setHoraPartida(double horaPartida) {
        this.horaPartida = horaPartida;
    }

    public Cliente(String estado, double horaLlegada, double horaPartida) {
        this.estado = estado;
        this.horaLlegada = horaLlegada;
        this.horaPartida = horaPartida;
    }

    
}
