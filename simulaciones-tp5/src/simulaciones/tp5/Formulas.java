/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciones.tp5;

/**
 *
 * @author gabrielneil
 */
public class Formulas {

    public static double tiempoCompraTicket(float tiempoCompra, float random) {

        return (-tiempoCompra) * Math.log(1 - random);
    }

    public static double tiempoEntregaPedido(float tiempoCompra, float random) {

        return (-tiempoCompra) * Math.log(1 - random);
    }
    
    //box-muller
    public static double llegadaCliente(float rnd1, float rnd2, float media, float desviacion){
        return ((Math.sqrt(-2*Math.log(rnd1))*Math.cos(2*Math.PI*rnd2))*desviacion)+media;
    }
    
    public static double tiempoUtilizacionMesa(float rnd){
        return 10+(rnd*10);
    }
    
    public static double tiempoConsumicion(float rnd){
        return 4+(rnd*2);
    }
}
