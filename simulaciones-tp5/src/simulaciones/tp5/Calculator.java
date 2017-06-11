/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciones.tp5;

import Objects.Cliente;
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
    DefaultTableModel model;
    int cantidadClientesEnCafeteria = 0;

    int media = 12;
    int desviacion = 2;
    int entranAComprar = 70;
    int entranAMesa = 30;
    int sientaEnMesa = 50;
    int seRetira = 50;
    double reloj = 0;

    int tiempoTicket = 20;
    int tiempoEspera = 50;
    int tiempoConsumicion1 = 5;
    int tiempoConsumicion2 = 1;
    int tiempoUtilizacionMesa1 = 15;
    int tiempoUtilizacionMesa2 = 5;
    String evento = EVN_INICIO;

    //Eventos: 
    //Llegada cliente
    //Atencion en caja 
    //Entrega de pedido
    //Utilizacion de mesa
    //Consumicion de pedido
    //Fin permanencia
    public static final String EVN_INICIO = "Inicio";
    public static final String EVN_LLEGADA = "Llegada Cliente";
    public static final String EVN_ATENCION = "Atencion en caja";
    public static final String EVN_ENTREGA = "Entrga de pedido";
    public static final String EVN_UTILIZACION = "Utilizacion de mesa";
    public static final String EVN_CONSUMICION = "Consumicion de pedido";
    public static final String EVN_FIN = "Fin Permanencia";

    /*
     * Indices de las columnas
     */
    public static final int COL_TIEMPO_LLEGADA = 5;
    public static final int COL_TIEMPO_ATENCION = 8;
    public static final int COL_TIEMPO_ENTREGA = 11;
    public static final int COL_FIN_USO = 15;
    public static final int COL_FIN_CONSUMICION = 18;

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

    public void setReloj(double tiempo) {
        this.reloj = tiempo;
    }

    public void initSimulacion() {
        tabla.setVisible(true);
        model = (DefaultTableModel) tabla._tblSimulacion.getModel();
        primeraVuelta();
        segundaVuelta();

        //puse 3600 porque si no, no puedo probar nunca nada ya que tira numeros altos la siguiente llegada(despues cambiar)
        while (reloj <= 3600) {
            masVueltas();
        }
    }

    public void primeraVuelta() {
        Random r = new Random();
        float rnd1TiempoLlegada;
        float rnd2TiempoLlegada;
        //primera vuelta
        rnd1TiempoLlegada = r.nextFloat();
        rnd2TiempoLlegada = r.nextFloat();
        double tiempoLlegada = llegadaEntreCliente(rnd1TiempoLlegada, rnd2TiempoLlegada);
        setEvento(EVN_INICIO);
        model.addRow(new Object[]{evento, reloj, rnd1TiempoLlegada, rnd2TiempoLlegada, tiempoLlegada, reloj += tiempoLlegada});
        setReloj(tiempoLlegada);
    }

    public void segundaVuelta() {
        Random r = new Random();
        float rnd1TiempoLlegada;
        float rnd2TiempoLlegada;
        //segunda vuelta
        double tiempoProxLlegadaCliente = (double) model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_LLEGADA);
        rnd1TiempoLlegada = r.nextFloat();
        rnd2TiempoLlegada = r.nextFloat();
        double tiempoLlegada = llegadaEntreCliente(rnd1TiempoLlegada, rnd2TiempoLlegada);
        setEvento(EVN_LLEGADA);
        model.addRow(new Object[]{evento, tiempoProxLlegadaCliente, rnd1TiempoLlegada, rnd2TiempoLlegada, tiempoLlegada, reloj += tiempoLlegada});
    }

    public void masVueltas() {
        Random r = new Random();
        float rnd1TiempoLlegada;
        float rnd2TiempoLlegada;
        if (evento != EVN_INICIO) {
            //tercera vuelta o más
            double tiempoProxLlegadaCliente = (double) model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_LLEGADA);
            double tiempoFinAtencionCaja = (double) model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ATENCION);
            double tiempoEntregaPedido = (double) model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ENTREGA);
            double tiempoFinUsoMesa = (double) model.getValueAt(model.getRowCount() - 1, COL_FIN_USO);
            double tiempoFinConsumicion = (double) model.getValueAt(model.getRowCount() - 1, COL_FIN_CONSUMICION);

            if ((tiempoProxLlegadaCliente < tiempoFinAtencionCaja)
                    && (tiempoProxLlegadaCliente < tiempoEntregaPedido)
                    && (tiempoProxLlegadaCliente < tiempoFinUsoMesa)
                    && (tiempoProxLlegadaCliente < tiempoFinConsumicion)) {
                //tiempoProxLlegadaCliente es el proximo evento
                setEvento(EVN_LLEGADA);
                Cliente c1;
                rnd1TiempoLlegada = r.nextFloat();
                rnd2TiempoLlegada = r.nextFloat();
                double tiempoLlegada = llegadaEntreCliente(rnd1TiempoLlegada, rnd2TiempoLlegada);
                float rndAccion = r.nextFloat();
                //true y se calcula el tiempo que tarda en usar la mesa
                if (rndAccion < ((entranAMesa - 1) / 100)) {
                    float rndTiempoUtilizacionMesa = r.nextFloat();
                    double tiempoFinUtilizacionMesa = finUtilizacionDeMesa(rndTiempoUtilizacionMesa);
                    c1 = new Cliente("Utilizando mesa", reloj, reloj + tiempoFinUtilizacionMesa);
                    cantidadClientesEnCafeteria++;
                    cajero.setLibre();
                    empleado1.setLibre();
                    empleado2.setLibre();
                    //la idea seria que donde hay ceros copia las cosas de la fila de arriba
                    model.addRow(new Object[]{
                        evento, reloj, rnd1TiempoLlegada, rnd2TiempoLlegada, tiempoLlegada,
                        reloj += tiempoLlegada, rndAccion, "Utiliza mesa", 0, 0, 0, 0, 0, 0,
                        rndTiempoUtilizacionMesa, tiempoFinUtilizacionMesa, 0, 0, 0, cajero.getEstado(),
                        cajero.getCola(), empleado1.getEstado(), empleado1.getCola(), empleado2.getEstado(),
                        empleado2.getCola(), 0, c1.getEstado(), c1.getHoraLlegada(), c1.getHoraPartida()});

                    setReloj(tiempoProxLlegadaCliente);
                }//false, entra a comprar
                else {
                    tiempoFinAtencionCaja = reloj += 20;
                    cajero.setOcupado();
                    setReloj(tiempoProxLlegadaCliente);
                }

            } else if ((tiempoFinAtencionCaja < tiempoEntregaPedido)
                    && (tiempoFinAtencionCaja < tiempoFinUsoMesa)
                    && (tiempoFinAtencionCaja < tiempoFinConsumicion)) {
                // tiempoFinAtencionCaja es el proximo evento

            } else if ((tiempoEntregaPedido < tiempoFinUsoMesa)
                    && (tiempoEntregaPedido < tiempoFinConsumicion)) {
                // tiempoEntregaPedido es el proximo evento

            } else if (tiempoFinUsoMesa < tiempoFinConsumicion) {
                // tiempoUsoMesa es el proximo evento

            } else {
                // tiempoFinConsumicion es el proximo evento

            }
        }
    }

    //Tiempo de llegada entre clientes en minutos, 
    //recibe como parametro la media y la desviacion en segundos y los randoms para box muller
    //retorna el tiempo de llegada entre cliente en MINUTOS
    public double llegadaEntreCliente(float rnd1, float rnd2) {

        double n1 = Formulas.llegadaCliente(rnd1, rnd2, media, desviacion); //devuelve el tiempo en segundos
        double tiempo = n1 * 60; //Estaba calculado en segundos, lo paso a minutos
        return tiempo;
    }

    public double finUtilizacionDeMesa(float rnd) {
        double n = Formulas.tiempoUtilizacionMesa(rnd);
        double tiempo = n * 60; //Estaba calculado en segundos, lo paso a minutos
        return tiempo;
    }

}
