/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciones.tp5;

import Objects.Cliente;
import Objects.Servidor;
import java.util.ArrayList;

/**
 *
 * @author gneil
 */
public class Buscar {

    ArrayList<Cliente> lista;

    public Buscar(ArrayList lista) {
        this.lista = lista;
    }

    public Cliente buscarCliente(String evento, double minimo) {
        Cliente aux = null;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getHoraPartida() == minimo && lista.get(i).getEstado().equals(evento)) {
                aux = lista.get(i);
                break;
            }
        }
        return aux;
    }

    public int buscarPosicion(double minimo) {
        int posicion = 0;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getHoraPartida() == minimo) {
                posicion = i;
                break;
            }
        }
        return posicion;
    }

    public double setMenor(double finCliente, double minimo) {
        if (finCliente < minimo || minimo == 0) {
            minimo = finCliente;
        }
        return minimo;
    }

    public int quienReemplaza(String evento) {
        int elMasViejo = -1;
        double tiempoLlegada = 0;
        for (int i = 0; i < lista.size(); i++) {
            Cliente aux = lista.get(i);
            if ((aux.getEstado().equals(evento) && tiempoLlegada == 0) || (tiempoLlegada > aux.getHoraLlegada() && (aux.getEstado().equals(evento)))) {
                tiempoLlegada = aux.getHoraLlegada();
                elMasViejo = i;
            }
        }
        return elMasViejo;
    }

    public void actualizarCajero(Servidor cajero) {
        if (cajero.getCola() == 0) {
            cajero.setLibre();
        } else {
            cajero.disminuirCola();
        }
    }

    public void actualizarEmpleados(Cliente cliente, Servidor empleado1, Servidor empleado2) {

        if (cliente.getQuienMeAtiende().equals("EMPLEADO1")) {

            empleado1.setLibre();
        } else {
            empleado2.setLibre();
        }

        if (empleado1.getCola() > 0 && empleado2.getCola() > 0) {
            empleado1.disminuirCola();
            empleado2.disminuirCola();
        }
    }

    public int quienCortaAntes(String evento) {
        int menorPosicion = -1;
        double menorTiempo = 0;
        for (int i = 0; i < lista.size(); i++) {
            
            if((menorTiempo == 0 && lista.get(i).getEstado().equals(evento))||(menorTiempo> lista.get(i).getHoraPartida() && lista.get(i).getEstado().equals(evento)))  {
                menorTiempo = lista.get(i).getHoraPartida();
                menorPosicion = i;
            }
        }
        return menorPosicion;
    }

}
