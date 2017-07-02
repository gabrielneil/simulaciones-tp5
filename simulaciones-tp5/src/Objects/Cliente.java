/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Objects;

/**
 * Estados del cliente:
 * Esperando atención en caja -
 * Atendido en caja -
 * Esperando pedido -
 * Consumiendo -
 * Utilizando mesa
 *
 * @author gabrielneil
 */
public class Cliente {
    
    String estado;
    double horaLlegada;
    double horaPartida;
    String quienMeAtiende;
    int posicion;
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
    //este es para el que usa la mesa
    public Cliente(String estado, double horaLlegada, double horaPartida, int posicion) {
        this.estado = estado;
        this.horaLlegada = horaLlegada;
        this.horaPartida = horaPartida;
        this.posicion = posicion;
    }
    //este es para el que viene a comprar
     public Cliente(String estado, double horaLlegada, int posicion) {
        this.estado = estado;
        this.horaLlegada = horaLlegada;
        this.horaPartida = -1;
        this.posicion = posicion;
    }
    
    public void quienMeAtiende(String quienMeAtiende){
        this.quienMeAtiende = quienMeAtiende;
    }
    
    public String getQuienMeAtiende(){
        return quienMeAtiende;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }
    
}
