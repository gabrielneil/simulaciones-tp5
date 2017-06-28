/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciones.tp5;

import Objects.Servidor;
import front.Tabla;
import javax.swing.table.DefaultTableModel;
import static simulaciones.tp5.Calculator.EVN_INICIO;

/**
 *
 * @author gneil
 */
public class Grafico {

    DefaultTableModel model;
    Tabla tabla = new Tabla();
    public Grafico() {
         tabla.setVisible(true);
        model = (DefaultTableModel) tabla._tblSimulacion.getModel();
       
    }
    
    public void primeraVuelta(String eventoInicio, double reloj, float rnd1TiempoLlegada, float rnd2TiempoLlegada,double tiempoLlegada , double proxLlegada, Servidor cajero, Servidor empleado1, Servidor empleado2){
            
            model.addRow(new Object[]{
                eventoInicio, //Evento (0)
                reloj, //Reloj (1)
                rnd1TiempoLlegada, //Llegada cliente - RND1 (2)
                rnd2TiempoLlegada, //Llegada cliente - RND2 (3)
                tiempoLlegada, //Tiempo llegada cliente (4)
                tiempoLlegada, //Próxima Llegada cliente (5)
                null, //Acción - RND (6)
                "", //Accion : mesa o a comprar (7)
                null, //Tiempo fin atención caja (8)
                null, //Tiempo espera pedido - RND (9)
                null, //Tiempo espera pedido (10)
                null, //Tiempo entrega de pedido (11)
                null, //Accion mesa: - RND (12)
                "", //Accion mesa (13)
                null, //Tiempo uso de mesa - RND (14)
                null, //Tiempo uso de mesa (15)
                null, //Tiempo fin uso mesa (16)
                null, //Tiempo consumicion - RND (17)
                null, //Tiempo de consumicion (18)
                null, //Tiempo fin de consumicion (19)
                cajero.getEstado(),//Cajero - Estado (20)
                cajero.getCola(), //Cajero - Cola (21)
                empleado1.getEstado(), //Empleado 1 - Estado (22)
                empleado2.getEstado(), //Empleado 2 - Cola (23)
                empleado2.getCola(), //Empleado 2 - Cola (24)
                0.0, //Tiempo de permanencia acumulado (25)
                0 //Cantidad clientes en cafeteria (26)
            });
    }

}
