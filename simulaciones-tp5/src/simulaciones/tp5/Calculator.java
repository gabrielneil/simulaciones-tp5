/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciones.tp5;

import front.Tabla;

/**
 *
 * @author gabrielneil
 */
public class Calculator {
    Controller controller;
    Formulas formulas = new Formulas();
    Tabla tabla = new Tabla();
    int media = 12;
    int desviacion = 2;
    int entranAComprar = 60;
    int entranAMesa = 40;
    int sientaEnMesa = 50;
    int seRetira = 50;

    int tiempoTicket = 20;
    int tiempoEspera = 50;
    int tiempoConsumicion1 = 5;
    int tiempoConsumicion2 = 1;
    int tiempoUtilizacionMesa1 = 15;
    int tiempoUtilizacionMesa2 = 5;

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

    public void cargaDatos(int media, int desviacion, int entranAComprar, int entranAMesa, int sientaEnMesa, int seRetira) {
        this.media = media;
        this.desviacion = desviacion;
        this.entranAComprar = entranAComprar;
        this.entranAMesa = entranAMesa;
        this.sientaEnMesa = sientaEnMesa;
        this.seRetira = seRetira;
    }

    void initSimulacion() {
        float proximoCliente;
        
        for (int i = 0; i < 10; i++) {
            proximoCliente = (float) formulas.tiempoLlegadaCliente(i);
        }
//        do {
//        //fix this    
//        } while (tabla._tblSimulacion.getRowCount()<3600);
        
    }

}
