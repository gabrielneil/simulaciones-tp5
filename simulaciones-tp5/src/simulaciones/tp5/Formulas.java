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

    
    public double tiempoCompraTicket(float tiempoCompra, float random){
        
    return (-tiempoCompra)*Math.log(1-random);
    }
}
