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
public class Calculator {

    Controller controller;
    int tiempoTicket;
    int tiempoEspera;
    int tiempoConsumicion1;
    int tiempoConsumicion2;
    int tiempoUtilizacionMesa1;
    int tiempoUtilizacionMesa2;

    public Calculator(Controller controller) {
        this.controller = controller;
    }

    public void cargaTiempos(int tiempoTicket, int tiempoEspera, int tiempoConsumicion1, int tiempoConsumicion2, int tiempoUtilizacionMesa1, int tiempoUtilizacionMesa2) {
        this.tiempoTicket = tiempoTicket;
        this.tiempoEspera = tiempoEspera;
        this.tiempoConsumicion1 = tiempoConsumicion1;
        this.tiempoConsumicion2 = tiempoConsumicion2;
        this.tiempoUtilizacionMesa1 = tiempoUtilizacionMesa1;
        this.tiempoUtilizacionMesa2 = tiempoUtilizacionMesa2;
    }

}
