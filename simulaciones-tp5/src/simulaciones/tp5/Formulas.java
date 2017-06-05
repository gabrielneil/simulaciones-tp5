/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciones.tp5;

import java.util.Random;

/**
 *
 * @author gabrielneil
 */
public class Formulas {

    Random rnd = new Random();

    public double tiempoCompraTicket(float tiempoCompra, float random) {
        return (-tiempoCompra) * Math.log(1 - random);
    }

    public double tiempoEntregaPedido(float tiempoCompra, float random) {
        return (-tiempoCompra) * Math.log(1 - random);
    }

    public double tiempoLlegadaCliente(float tiempoActual) {
        return tiempoActual + (((-2 * Math.log(rnd.nextFloat()) * Math.cos(2 * Math.PI * rnd.nextFloat())) * 2) + 10);
    }
//    public double tiempoConsumición(){
//    
//    }
}
