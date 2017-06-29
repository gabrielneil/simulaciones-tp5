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
        Cliente cliente;
        int posicion = 0;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getHoraPartida() == minimo) {
                cliente = lista.get(i);
                posicion = i;
                break;
            }
        }
        return posicion;
    }

    public Cliente siguienteAtender(String evento) {
        double elMasViejo = 0;
        Cliente aux = null;
        int posicion = 0;
        for (int i = 0; i < lista.size(); i++) {
            aux = lista.get(i);
            if ((aux.getEstado().equals(evento) && elMasViejo == 0) || (elMasViejo > aux.getHoraLlegada() && (aux.getEstado().equals(evento)))) {
                elMasViejo = aux.getHoraLlegada();
                posicion = i;
            }
        }
        return lista.get(posicion);
    }

    public double setMenor(double finCliente, double minimo) {
        if (finCliente < minimo || minimo == 0) {
            minimo = finCliente;
        }
        return minimo;
    }

    public int quienReemplaza(String evento) {
        int elMasViejo = 0;
        for (int i = 0; i < lista.size(); i++) {
            Cliente aux = lista.get(i);
            if ((aux.getEstado().equals(evento) && elMasViejo == 0) || (elMasViejo > aux.getHoraLlegada() && (aux.getEstado().equals(evento)))) {
                elMasViejo = i;
            }
        }
        return elMasViejo;
    }

    public double menorProximo(String evento, double minimoAnterior) {

        for (int i = 0; i < lista.size(); i++) {
            Cliente aux = lista.get(i);
            if ((minimoAnterior == 0 && aux.getEstado().equals(evento)) || (minimoAnterior > aux.getHoraPartida() && aux.getEstado().equals(evento))) {
                minimoAnterior = aux.getHoraPartida();
            }
        }
        return minimoAnterior;
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
            if (empleado1.getCola() == 0) {
                empleado1.setLibre();
            } else {
                empleado1.disminuirCola();
                empleado2.disminuirCola();
            }
        } else if (cliente.getQuienMeAtiende().equals("EMPLEADO2")) {
            if (empleado2.getCola() == 0) {
                empleado2.setLibre();
            } else {
                empleado1.disminuirCola();
                empleado2.disminuirCola();
            }
        }
    }

}
