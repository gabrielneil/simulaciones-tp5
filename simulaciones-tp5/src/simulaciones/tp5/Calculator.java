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
    public static final String EVN_FIN_ATENCION = "Fin atención caja";
    public static final String EVN_ENTREGA = "Entrega de pedido";
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
        double tiempoLlegada = llegadaEntreCliente(rnd1TiempoLlegada, rnd2TiempoLlegada);
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
            rnd1TiempoLlegada = r.nextFloat();
            rnd2TiempoLlegada = r.nextFloat();
            double tiempoLlegada = llegadaEntreCliente(rnd1TiempoLlegada, rnd2TiempoLlegada);
            float rndAccion = r.nextFloat();

            //true y se calcula el tiempo que tarda en usar la mesa
            if (rndAccion <= ((float) entranAMesa / 100)) {

                System.out.println("entra a la mesa");
                float rndTiempoUtilizacionMesa = r.nextFloat();
                double tiempoFinUtilizacionMesa = finUtilizacionDeMesa(rndTiempoUtilizacionMesa);
                Cliente c1 = new Cliente("Utilizando mesa", reloj, reloj + tiempoFinUtilizacionMesa);
                //la idea seria que donde hay ceros copia las cosas de la fila de arriba
                lista.add(c1);
                model.addColumn("Cliente: Estado");
                model.addColumn("Cliente: Entrada al sistema");
                model.addColumn("Cliente: Partida del sistema");
                
                model.addRow(new Object[]{
                    evento, reloj, rnd1TiempoLlegada, rnd2TiempoLlegada, tiempoLlegada,
                    reloj + tiempoLlegada, rndAccion, "Utiliza mesa", (double) (model.getValueAt(model.getRowCount() - 1, 8)), (double) (model.getValueAt(model.getRowCount() - 1, 9)), (double) (model.getValueAt(model.getRowCount() - 1, 10)), (double) (model.getValueAt(model.getRowCount() - 1, 11)), (double) (model.getValueAt(model.getRowCount() - 1, 12)), "",
                    rndTiempoUtilizacionMesa, tiempoFinUtilizacionMesa, (double) (model.getValueAt(model.getRowCount() - 1, 16)), (double) (model.getValueAt(model.getRowCount() - 1, 17)), (double) (model.getValueAt(model.getRowCount() - 1, 18)), cajero.getEstado(),
                    cajero.getCola(), empleado1.getEstado(), empleado1.getCola(), empleado2.getEstado(),
                    empleado2.getCola(),
                    (evento == EVN_FIN) ? tiempoPermanenciaAcumulador + (c1.getHoraPartida() - c1.getHoraLlegada()) : tiempoPermanenciaAcumulador,
                    (evento == EVN_FIN) ? cantidadClientesEnCafeteria += 1 : cantidadClientesEnCafeteria,c1.getEstado(),c1.getHoraLlegada(),c1.getHoraPartida()});

            } //false, entra a comprar
            else {
                System.out.println("entra a comprar");
                tiempoFinAtencionCaja = reloj + tiempoTicket;
                if (cajero.getEstado() == "LIBRE") {
                    cajero.setOcupado();
                } else {
                    cajero.aumentarCola();
                }
                
                Cliente cl = new Cliente("Comprando", reloj, reloj + tiempoFinAtencionCaja);
                lista.add(cl);
                model.addColumn("Cliente: Estado");
                model.addColumn("Cliente: Entrada al sistema");
                model.addColumn("Cliente: Partida del sistema");
                
                model.addRow(new Object[]{
                    evento, reloj, rnd1TiempoLlegada, rnd2TiempoLlegada, tiempoLlegada,
                    reloj + tiempoLlegada, rndAccion, "Compra", tiempoFinAtencionCaja, 0.0, 0.0, 0.0, 0.0, "",
                    0.0, 0.0, 0.0, 0.0, 0.0, cajero.getEstado(),
                    cajero.getCola(), empleado1.getEstado(), empleado1.getCola(), empleado2.getEstado(),
                    empleado2.getCola(),
                    0.0, 0.0,cl.getEstado(),cl.getHoraLlegada(),cl.getHoraPartida()});

            }

        } else if ((tiempoFinAtencionCaja < tiempoEntregaPedido)
                && (tiempoFinAtencionCaja < tiempoFinUsoMesa)
                && (tiempoFinAtencionCaja < tiempoFinConsumicion)) {
            
            // tiempoFinAtencionCaja es el proximo evento
            Cliente cl = new Cliente();
            setEvento(EVN_FIN_ATENCION);
            setReloj(tiempoFinAtencionCaja);

            if (cajero.getCola() == 0) {
                cajero.setLibre();
            } else {
                cajero.disminuirCola();
            }
            int posicion = 0;
            for (int i = 0; i < lista.size(); i++) {
                if(lista.get(i).getHoraPartida()==tiempoFinAtencionCaja){
                    cl = lista.get(i);
                    posicion = i;
                    break;
                }
            }
            
            float rndEspera = r.nextFloat();
            double tiempoEntrega = Formulas.tiempoEntregaPedido(tiempoEspera, rndEspera);
            
            cl.setEstado(evento);
            cl.setHoraPartida(reloj+tiempoEntrega);
            lista.set(posicion, cl);
            
            model.addRow(new Object[]{
                evento, reloj, 0.0, 0.0, 0.0,
                tiempoProxLlegadaCliente, 0.0, "", rndEspera, tiempoEntrega, reloj + tiempoEntrega, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, cajero.getEstado(),
                cajero.getCola(), empleado1.getEstado(), empleado1.getCola(), empleado2.getEstado(),
                empleado2.getCola(),
                0.0, 0.0,cl.getEstado(),cl.getHoraLlegada(),cl.getHoraPartida()});

            if (empleado1.getEstado() == "LIBRE") {
                empleado1.setOcupado();
            } else if (empleado2.getEstado() == "LIBRE") {
                empleado2.setOcupado();
            } else {
                empleado1.aumentarCola();
                empleado2.aumentarCola();
            }

        } else if ((tiempoEntregaPedido < tiempoFinUsoMesa)
                && (tiempoEntregaPedido < tiempoFinConsumicion)) {
            // tiempoEntregaPedido es el proximo evento

            setEvento(EVN_ENTREGA);
            setReloj(tiempoEntregaPedido);

            if (empleado1.getCola() == 0 || empleado2.getCola() == 0) {
                empleado2.setLibre();
            } else {
                empleado1.disminuirCola();
                empleado2.disminuirCola();
            }
            Cliente cl = new Cliente();
            int posicion = 0;
            for (int i = 0; i < lista.size(); i++) {
                if(lista.get(i).getHoraPartida()==tiempoEntregaPedido){
                    cl = lista.get(i);
                    posicion = i;
                    break;
                }
            }
            float rndAccion = r.nextFloat();
            //true y se sienta en mesa
            if (rndAccion <= ((float) sientaEnMesa / 100)) {
                //aca iria contemplado la parte del cliente que setea su estado a "consumir en mesa"
                float rndTiempoConsumicion = r.nextFloat();
                double finUsoMesa = Formulas.tiempoConsumicion(rndTiempoConsumicion);
                cl.setEstado("Sienta en mesa");
                cl.setHoraPartida(finUsoMesa);
                model.addRow(new Object[]{
                    evento, reloj, 0.0, 0.0, 0.0,
                    tiempoProxLlegadaCliente, 0.0, "", (double) (model.getValueAt(model.getRowCount() - 1, 8)), 0.0, 0.0, (double) (model.getValueAt(model.getRowCount() - 1, 11)), rndAccion, "Sienta en mesa",
                    0.0, (double) (model.getValueAt(model.getRowCount() - 1, 16)), rndTiempoConsumicion, finUsoMesa, reloj + rndTiempoConsumicion, cajero.getEstado(),
                    cajero.getCola(), empleado1.getEstado(), empleado1.getCola(), empleado2.getEstado(),
                    empleado2.getCola(),
                    (evento == EVN_FIN) ? tiempoPermanenciaAcumulador + (c1.getHoraPartida() - c1.getHoraLlegada()) : tiempoPermanenciaAcumulador,
                    (evento == EVN_FIN) ? cantidadClientesEnCafeteria += 1 : cantidadClientesEnCafeteria});
            } //false se retira
            else {
                //como hacer el tema del retiro del cliente
                model.addRow(new Object[]{
                    evento, reloj, 0.0, 0.0, 0.0,
                    tiempoProxLlegadaCliente, 0.0, "", (double) (model.getValueAt(model.getRowCount() - 1, 8)), 0.0, 0.0, (double) (model.getValueAt(model.getRowCount() - 1, 11)), rndAccion, "Se retira",
                    0.0, (double) (model.getValueAt(model.getRowCount() - 1, 16)), 0.0, 0.0, 0.0, cajero.getEstado(),
                    cajero.getCola(), empleado1.getEstado(), empleado1.getCola(), empleado2.getEstado(),
                    empleado2.getCola(),
                    (evento == EVN_FIN) ? tiempoPermanenciaAcumulador + (c1.getHoraPartida() - c1.getHoraLlegada()) : tiempoPermanenciaAcumulador,
                    (evento == EVN_FIN) ? cantidadClientesEnCafeteria += 1 : cantidadClientesEnCafeteria});
            }
        } else if (tiempoFinUsoMesa < tiempoFinConsumicion) {
            // tiempoUsoMesa es el proximo evento
            setEvento(EVN_FIN);
            setReloj(tiempoFinUsoMesa);
            cantidadClientesEnCafeteria += 1;
            //double tiempoPermanencia = ((double) (model.getValueAt(model.getRowCount() - 1, 25)))+ cliente;
            //hasta acá llegue, tengo que meter una fila más , pero debo saber cuanto tiempo lleva este chabon adentro...
        } else {
            // tiempoFinConsumicion es el proximo evento

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
