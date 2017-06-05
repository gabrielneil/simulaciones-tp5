/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciones.tp5;

import Objects.Servidor;
import front.Tabla;
import java.util.Random;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gabrielneil
 */
public class Calculator {

    Controller controller;
    Formulas formulas = new Formulas();
    Tabla tabla;
    Servidor empleado1 = new Servidor();
    Servidor empleado2 = new Servidor();
    Servidor cajero = new Servidor();
    int media = 12;
    int desviacion = 2;
    int entranAComprar = 60;
    int entranAMesa = 40;
    int sientaEnMesa = 50;
    int seRetira = 50;
    double reloj = 0;

    int tiempoTicket = 20;
    int tiempoEspera = 50;
    int tiempoConsumicion1 = 5;
    int tiempoConsumicion2 = 1;
    int tiempoUtilizacionMesa1 = 15;
    int tiempoUtilizacionMesa2 = 5;
    String evento = "Inicio";

    public Calculator(Controller controller) {
        this.controller = controller;
        this.tabla = new Tabla();
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

    //Eventos: 
    //Llegada cliente
    //Atencion en caja 
    //Entrega de pedido
    //Utilizacion de mesa
    //Consumicion de pedido
    //Fin permanencia
    public void setEvento(String evento) {
        this.evento = evento;
    }
    public void setReloj(double tiempo){
        this.reloj += tiempo;
    }
    
    public void initSimulacion() {
        DefaultTableModel model = (DefaultTableModel) tabla._tblSimulacion.getModel();
        
        while (reloj <= 60) {
            
            if (reloj == 0) {
                Random r = new Random();
                float rnd1TiempoLlegada = r.nextFloat();
                float rnd2TiempoLlegada = r.nextFloat();
                double tiempoLlegada = llegadaEntreCliente(rnd1TiempoLlegada, rnd2TiempoLlegada);
                setEvento("Inicio");
                model.addRow(new Object[]{evento, reloj, rnd1TiempoLlegada, rnd2TiempoLlegada, tiempoLlegada, reloj += tiempoLlegada});
                setReloj(tiempoLlegada);
            }
            else{
                double tiempoProxLlegadaCliente =(double) model.getValueAt(model.getRowCount()-1, 5);
                
            }
        }

//        do {
//            //fix this    
//        } while (tabla._tblSimulacion.getRowCount() < 3600);

    }

    //Tiempo de llegada entre clientes en minutos, 
    //recibe como parametro la media y la desviacion en segundos y los randoms para box muller
    //retorna el tiempo de llegada entre cliente en MINUTOS
    public double llegadaEntreCliente(float rnd1, float rnd2) {

        double n1 = Formulas.llegadaCliente(rnd1, rnd2, media, desviacion); //devuelve el tiempo en segundos
        double tiempo = n1 * 60; //Estaba calculado en segundos, lo paso a minutos
        return tiempo;
    }

}
