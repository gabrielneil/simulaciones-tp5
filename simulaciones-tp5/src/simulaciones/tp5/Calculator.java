/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciones.tp5;

import Objects.Cliente;
import Objects.Servidor;
import front.Tabla;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gabrielneil
 */
public class Calculator {
    ArrayList<Cliente> lista = new ArrayList<>();
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
    //int entranAComprar = 70;
    int entranAComprar = 60;
    //int entranAMesa = 30;
    int entranAMesa = 40;
    int sientaEnMesa = 50;
    int seRetira = 50;
    double reloj = 0;
    double tiempoPermanenciaAcumulador = 0;

    int tiempoTicket = 20;
    float tiempoEspera = 50;
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
    /**
     * Inicio
     * Llegada cliente
     * Fin atención en caja 
     * Entrega de pedido
     * Fin utilización de mesa
     * Fin consumición del pedido
     */
    public static final String EVN_INICIO = "Inicio";
    public static final String EVN_LLEGADA = "Llegada Cliente";
    public static final String EVN_FIN_ATENCION = "Fin atención caja";
    public static final String EVN_ENTREGA = "Entrega de pedido";
    public static final String EVN_UTILIZACION = "Fin Utilizacion de mesa";
    public static final String EVN_CONSUMICION = "Fin Consumicion de pedido";

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
        this.tiempoEspera = (float)tiempoEspera;
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
        masVueltas();
        masVueltas();
      
        //puse 3600 porque si no, no puedo probar nunca nada ya que tira numeros altos la siguiente llegada(despues cambiar)
//        while (reloj <= 3600) {
//            masVueltas();
//        }
    }

    public void primeraVuelta() {
       
        Random r = new Random();
        float rnd1TiempoLlegada;
        float rnd2TiempoLlegada;
        //primera vuelta
        rnd1TiempoLlegada = r.nextFloat();
        rnd2TiempoLlegada = r.nextFloat();
        double tiempoLlegada = Formulas.llegadaCliente(rnd1TiempoLlegada, rnd1TiempoLlegada, media, desviacion);
        setEvento(EVN_INICIO);
        
        model.addRow(new Object[]{evento, reloj, rnd1TiempoLlegada, rnd2TiempoLlegada, tiempoLlegada, reloj + tiempoLlegada, 0.0, "",
            0.0, 0.0, 0.0, 0.0, 0.0, "", 0.0, 0.0, 0.0, 0.0, 0.0, cajero.getEstado(), cajero.getCola(), empleado1.getEstado(), empleado1.getCola(), empleado2.getEstado(), empleado2.getCola(),
            0.0, 0});
        setReloj(tiempoLlegada);
    }

    public void masVueltas() {
        System.out.println("ENTRO AJJAJA");
        Random r = new Random();
        float rnd1TiempoLlegada;
        float rnd2TiempoLlegada;

        System.out.println((double) (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ATENCION)));
        double tiempoProxLlegadaCliente = (double) (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_LLEGADA));
        double tiempoFinAtencionCaja = (double) (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ATENCION));
        double tiempoEntregaPedido = (double) (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ENTREGA));
        double tiempoFinUsoMesa = (double) (model.getValueAt(model.getRowCount() - 1, COL_FIN_USO));
        double tiempoFinConsumicion = (double) (model.getValueAt(model.getRowCount() - 1, COL_FIN_CONSUMICION));

        if (((tiempoProxLlegadaCliente < tiempoFinAtencionCaja)
                && (tiempoProxLlegadaCliente < tiempoEntregaPedido)
                && (tiempoProxLlegadaCliente < tiempoFinUsoMesa)
                && (tiempoProxLlegadaCliente < tiempoFinConsumicion))
                || //osea es segunda vuelta
                (tiempoFinAtencionCaja == 0 && tiempoEntregaPedido == 0 && tiempoFinUsoMesa == 0 && tiempoFinConsumicion == 0)) {

            //tiempoProxLlegadaCliente es el proximo evento
            System.out.println("el próximo");
            setEvento(EVN_LLEGADA);
            setReloj(tiempoProxLlegadaCliente);
            Cliente c1;
            rnd1TiempoLlegada = r.nextFloat();
            rnd2TiempoLlegada = r.nextFloat();
            double tiempoLlegada = Formulas.llegadaCliente(rnd1TiempoLlegada, rnd1TiempoLlegada, media, desviacion);
            float rndAccion = r.nextFloat();

            //true y se calcula el tiempo que tarda en usar la mesa
            if (rndAccion <= ((float) entranAMesa / 100)) {

                System.out.println("entra a la mesa");
                float rndTiempoUtilizacionMesa = r.nextFloat();
                double tiempoFinUtilizacionMesa = formulas.tiempoUtilizacionMesa(tiempoUtilizacionMesa1,tiempoUtilizacionMesa2,rndTiempoUtilizacionMesa);
                c1 = new Cliente("Utilizando mesa", reloj, reloj + tiempoFinUtilizacionMesa);
                //la idea seria que donde hay ceros copia las cosas de la fila de arriba
                lista.add(c1);
                model.addRow(new Object[]{
                    evento, reloj, rnd1TiempoLlegada, rnd2TiempoLlegada, tiempoLlegada,
                    
                    reloj + tiempoLlegada, rndAccion, "Utiliza mesa", (double) (model.getValueAt(model.getRowCount() - 1, 8)),
                    
                    (double) (model.getValueAt(model.getRowCount() - 1, 9)), (double) (model.getValueAt(model.getRowCount() - 1, 10)), 
                    
                    (double) (model.getValueAt(model.getRowCount() - 1, 11)), (double) (model.getValueAt(model.getRowCount() - 1, 12)), "",
                    
                    rndTiempoUtilizacionMesa, tiempoFinUtilizacionMesa, (double) (model.getValueAt(model.getRowCount() - 1, 16)), (double) (model.getValueAt(model.getRowCount() - 1, 17)), (double) (model.getValueAt(model.getRowCount() - 1, 18)), cajero.getEstado(),
                    
                    cajero.getCola(), empleado1.getEstado(), empleado1.getCola(), empleado2.getEstado(),
                    
                    empleado2.getCola(),
                    
                    (evento == EVN_CONSUMICION || evento == EVN_UTILIZACION) ? tiempoPermanenciaAcumulador + (c1.getHoraPartida() - c1.getHoraLlegada()) : tiempoPermanenciaAcumulador,
                    (evento == EVN_CONSUMICION || evento == EVN_UTILIZACION) ? cantidadClientesEnCafeteria += 1 : cantidadClientesEnCafeteria});
                   

            } //false, entra a comprar
            else {
                System.out.println("entra a comprar");
                tiempoFinAtencionCaja = reloj + tiempoTicket;
                if (cajero.getEstado() == "LIBRE") {
                    cajero.setOcupado();
                } else {
                    cajero.aumentarCola();
                }

                model.addRow(new Object[]{
                    evento, reloj, rnd1TiempoLlegada, rnd2TiempoLlegada, tiempoLlegada,
                    reloj + tiempoLlegada, rndAccion, "Compra", tiempoFinAtencionCaja, 0.0, 0.0, 0.0, 0.0, "",
                    0.0, 0.0, 0.0, 0.0, 0.0, cajero.getEstado(),
                    cajero.getCola(), empleado1.getEstado(), empleado1.getCola(), empleado2.getEstado(),
                    empleado2.getCola(),
                    0.0, 0});

            }

        } else if ((tiempoFinAtencionCaja < tiempoEntregaPedido)
                && (tiempoFinAtencionCaja < tiempoFinUsoMesa)
                && (tiempoFinAtencionCaja < tiempoFinConsumicion)) {
            
            // tiempoFinAtencionCaja es el proximo evento
            setEvento(EVN_FIN_ATENCION);
            setReloj(tiempoFinAtencionCaja);
            
            if (cajero.getCola() == 0) {
                cajero.setLibre();
            } else {
                cajero.disminuirCola();
            }
            
            float rndEspera = r.nextFloat();
            double tiempoEntrega = Formulas.tiempoEntregaPedido(tiempoEspera, rndEspera);
            
            model.addRow(new Object[]{
                evento, reloj, 0.0, 0.0, 0.0,
                tiempoProxLlegadaCliente, 0.0, "", rndEspera, tiempoEntrega, reloj + tiempoEntrega, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, cajero.getEstado(),
                cajero.getCola(), empleado1.getEstado(), empleado1.getCola(), empleado2.getEstado(),
                empleado2.getCola(),
                0.0, 0.0});

        } else if ((tiempoEntregaPedido < tiempoFinUsoMesa)
                && (tiempoEntregaPedido < tiempoFinConsumicion)) {
            // tiempoEntregaPedido es el proximo evento
            System.out.println("tiempoentregapedido");
        } else if (tiempoFinUsoMesa < tiempoFinConsumicion) {
            // tiempoUsoMesa es el proximo evento
            System.out.println("tiempofinusomesa");
            setEvento(EVN_UTILIZACION);
            setReloj(tiempoFinUsoMesa);
            /*
             * Copiar proxima llegada de arriba --
             * Copiar fin atencion en caja de arriba
             * Copiar tiempo entrega pedido de arriba
             * Mantengo estado Cajero
             * Tiempo permanencia = tiempo permanencia anterior + [sacar de arriba](diferencia entre tiempo de partida y tiempo de llegada del cliente que se va en este momento del reloj)
             * Matar cliente que se va en este momento TODO (bah)
             * Mantener clientes anteriores TODO
             */
            model.addRow(new Object[]{
            evento, reloj, null, null, null, (double) model.getValueAt(model.getRowCount() -1,COL_TIEMPO_LLEGADA), null, null, (double) model.getValueAt(model.getRowCount() -1,COL_TIEMPO_ATENCION),
                null, null, (double) model.getValueAt(model.getRowCount() -1,COL_TIEMPO_ENTREGA), null, null, null, null, null, null, null, (double) model.getValueAt(model.getRowCount() -1,COL_FIN_CONSUMICION),
                (String) model.getValueAt(model.getRowCount() -1, 19), (int) model.getValueAt(model.getRowCount() -1, 20), (String) model.getValueAt(model.getRowCount() -1, 21), (int) model.getValueAt(model.getRowCount() -1, 22),
                (String) model.getValueAt(model.getRowCount() -1, 23), calcularFinPermanencia(24) , (int) model.getValueAt(model.getRowCount() -1, 25), 
            });
            
        } else {
            // tiempoFinConsumicion es el proximo evento
            System.out.println("tiempofinusomesa");
            setEvento(EVN_CONSUMICION);
            setReloj(tiempoFinConsumicion);
           
            /*
             * Copiar proxima llegada de arriba --
             * Copiar fin atencion en caja de arriba
             * Copiar tiempo entrega pedido de arriba
             * Mantengo estado Cajero
             * Tiempo permanencia = tiempo permanencia anterior + [sacar de arriba](diferencia entre tiempo de partida y tiempo de llegada del cliente que se va en este momento del reloj)
             * Matar cliente que se va en este momento TODO (bah)
             * Mantener clientes anteriores TODO
             */
            model.addRow(new Object[]{
            evento, 
                reloj,
                null,
                null, 
                null, 
                (double) model.getValueAt(model.getRowCount() -1,COL_TIEMPO_LLEGADA), 
                null, 
                null, 
                (double) model.getValueAt(model.getRowCount() -1,COL_TIEMPO_ATENCION),
                null,
                null, 
                (double) model.getValueAt(model.getRowCount() -1,COL_TIEMPO_ENTREGA),
                null, 
                null, 
                null, 
                (double) model.getValueAt(model.getRowCount() -1,COL_FIN_USO),
                null,
                null,
                null,
                (String) model.getValueAt(model.getRowCount() -1, 19),
                (int) model.getValueAt(model.getRowCount() -1, 20),
                (String) model.getValueAt(model.getRowCount() -1, 21),
                (int) model.getValueAt(model.getRowCount() -1, 22),
                (String) model.getValueAt(model.getRowCount() -1, 23),
                (int) model.getValueAt(model.getRowCount() -1, 24),
                calcularFinPermanencia(25) , 
                (int) model.getValueAt(model.getRowCount() -1, 26)
            });
            //TODO: agregar los clientes de alguna forma
        }

    }

    //Tiempo de llegada entre clientes en minutos, 
    //recibe como parametro la media y la desviacion en segundos y los randoms para box muller
    //retorna el tiempo de llegada entre cliente en MINUTOS
    public double llegadaEntreCliente(float rnd1, float rnd2) {

        double n1 = Formulas.llegadaCliente(rnd1, rnd2, media, desviacion); //devuelve el tiempo en segundos
        double tiempo = n1 / 60; //Estaba calculado en segundos, lo paso a minutos
        return tiempo;
    }

    private double calcularFinPermanencia(int colTiempoPermanencia)
    {
        double ret = (double) model.getValueAt(model.getRowCount() -1, colTiempoPermanencia);
        //Tiempo permanencia = tiempo permanencia anterior + 
        //[sacar de arriba](diferencia entre tiempo de partida y tiempo de llegada del cliente que se va en este momento del reloj)
        for (Cliente cl : lista)
        {
            if (cl.getHoraPartida() == reloj)
            {
                double dif = cl.getHoraPartida() - cl.getHoraLlegada();
                lista.remove(cl);
                return ret + dif;
            }
        }
        return ret;
    }
}
