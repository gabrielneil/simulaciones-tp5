/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciones.tp5;

import front.CargaDatos;
import front.CargaTiempos;

/**
 *
 * @author gabrielneil
 */
public class Controller {

    private static Controller controller;
    CargaDatos cargaDatos;
    CargaTiempos cargaTiempos;
    Calculator calculator;

    public Controller() {
        calculator = new Calculator(this);
    }

    public static Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        } else {
        }

        return controller;
    }

    public void cargaDatos() {
        cargaDatos = new CargaDatos();
    }

    public void cargaTiempos() {
        cargaTiempos = new CargaTiempos();
    }

    public void valoresCargaTiempos(int tiempoTicket, int tiempoEspera, int tiempoConsumicion1, int tiempoConsumicion2, int tiempoUtilizacionMesa1, int tiempoUtilizacionMesa2) {
        calculator.cargaTiempos(tiempoTicket, tiempoEspera, tiempoConsumicion1, tiempoConsumicion2, tiempoUtilizacionMesa1, tiempoUtilizacionMesa2);
    }
}
