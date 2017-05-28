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

    public Controller() {
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
}
