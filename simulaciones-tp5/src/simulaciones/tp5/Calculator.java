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
    Tabla tabla;
    Servidor empleado1 = new Servidor();
    Servidor empleado2 = new Servidor();
    Servidor cajero = new Servidor();
    Cliente c1;
    int desde;
    int hasta;
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

    float tiempoTicket = 20 * 60;
    float tiempoEspera = 50 * 60;
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
     * Inicio Llegada cliente Fin atención en caja Entrega de pedido Fin
     * utilización de mesa Fin consumición del pedido
     */
    private static final String NO_EVN = "NULL";
    public static final String EVN_INICIO = "Inicio";
    public static final String EVN_LLEGADA = "Llegada Cliente";
    public static final String EVN_FIN_ATENCION = "Fin atención caja";
    public static final String EVN_ENTREGA = "Entrega de pedido";
    public static final String EVN_UTILIZACION = "Fin Utilizacion de mesa";
    public static final String EVN_CONSUMICION = "Fin Consumicion de pedido";

    /*
    * Indices de las columnas
     */
    //Col 0: Evento
    //Col 1: Reloj
    //Col 2: RND 1
    //Col 3: RND 2
    //Col 4: tiempo entre llegadas
    /**
     * Col 5 - Proxima llegada cliente
     */
    public static final int COL_TIEMPO_LLEGADA = 5;
    //Col 6: RND accion del cliente
    /**
     * Col 7 - Accion cliente: Compra / Usa mesa.
     */
    public static final int COL_ACCION_CLIENTE = 7;
    /**
     * Col 8 - Tiempo fin atencion en caja (siempre reloj + 20s en el evento
     * ...)
     */
    public static final int COL_TIEMPO_ATENCION = 8;
    //Col 9: RND tiempo espera entrega pedido
    //Col 10: Tiempo espera pedido.
    /**
     * Col 11 - Tiempo entrega pedido
     */
    public static final int COL_TIEMPO_ENTREGA = 11;
    //Col 12:RND accion mesa
    /**
     * Col 13 - Accion mesa
     */
    public static final int COL_ACCION_MESA = 13;
    //Col 14: RND tiempo uso mesa
    //Col 15: tiempo uso mesa
    /**
     * Col 16 - Tiempo fin uso mesa (reloj + tiempo uso mesa
     */
    public static final int COL_FIN_USO = 16;
    //Col 17 RND tiempo consumicion
    //Col 18 Tiempo consumuicion
    /**
     * Col 19 - Tiempo fin consumicion
     */
    public static final int COL_FIN_CONSUMICION = 19;
    /**
     * Col 20 - Estado Cajero
     */
    public static final int COL_ESTADO_CAJERO = 20;
    /**
     * Col 21 - Cola Cajero
     */
    public static final int COL_COLA_CAJERO = 21;
    /**
     * Col 22 - Estado Empleado 1
     */
    public static final int COL_ESTADO_EMPL1 = 22;
    /**
     * Col 23 - Cola EmpleadoS
     */
    public static final int COL_COLA_EMPLEADOS = 23;
    /**
     * Col 24 - Estado Empleado 2
     */
    public static final int COL_ESTADO_EMPL2 = 24;
    /**
     * Col 25 - Tiempo permanencia acumulado
     */
    public static final int COL_TIEMPO_PERMAN_AC = 25;
    /**
     * Col 26 - Cantidad de clientes de la cantina Contador
     */
    public static final int COL_CANT_CLIENTES_CONT = 26;
    /**
     * Col 27 - Inicio columnas de clientes.
     */
    public static final int COL_INICIO_CLIENTES = 27;

    public Calculator(Controller controller) {
        this.controller = controller;
        this.tabla = new Tabla();
    }

    public void cargaTiempos(int tiempoTicket, int tiempoEspera, int tiempoConsumicion1, int tiempoConsumicion2, int tiempoUtilizacionMesa1, int tiempoUtilizacionMesa2) {
        this.tiempoTicket = (float) tiempoTicket * 60; //Regla de 3 para pasar a minutos
        this.tiempoEspera = (float) tiempoEspera * 60; //Regla de 3 para pasar a minutos
        this.tiempoConsumicion1 = tiempoConsumicion1;
        this.tiempoConsumicion2 = tiempoConsumicion2;
        this.tiempoUtilizacionMesa1 = tiempoUtilizacionMesa1;
        this.tiempoUtilizacionMesa2 = tiempoUtilizacionMesa2;
    }

    public void cargaDatos(int media, int desviacion, int entranAComprar, int entranAMesa, int sientaEnMesa, int seRetira, int desde, int hasta) {
        this.media = media;
        this.desviacion = desviacion;
        this.entranAComprar = entranAComprar;
        this.entranAMesa = entranAMesa;
        this.sientaEnMesa = sientaEnMesa;
        this.seRetira = seRetira;
        this.desde = desde;
        this.hasta = hasta;
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
//        primeraVuelta();
        setEvento(NO_EVN);
        double tiempoDeCorte = 5;
        int cantFilas = 1;
        while (reloj <= tiempoDeCorte) {
            simularAvance();
            cantFilas++;
        }
    }

    public void simularAvance() {

        Random r = new Random();
        if (NO_EVN.equalsIgnoreCase(evento)) {
            setEvento(EVN_INICIO);
            float rnd1TiempoLlegada;
            float rnd2TiempoLlegada;
            //primera vuelta
            rnd1TiempoLlegada = r.nextFloat();
            rnd2TiempoLlegada = r.nextFloat();
            double tiempoLlegada = Formulas.llegadaCliente(rnd1TiempoLlegada, rnd1TiempoLlegada, media, desviacion);

            model.addRow(new Object[]{
                evento, //Evento (0)
                reloj, //Reloj (1)
                rnd1TiempoLlegada, //Llegada cliente - RND1 (2)
                rnd2TiempoLlegada, //Llegada cliente - RND2 (3)
                tiempoLlegada, //Tiempo llegada cliente (4)
                reloj + tiempoLlegada, //Próxima Llegada cliente (5)
                null, //Acción - RND (6)
                "", //Accion : mesa o a comprar (7)
                null, //Tiempo fin atención caja (8)
                null, //Tiempo espera pedido - RND (9)
                null, //Tiempo espera pedido (10)
                null, //Accion mesa: - RND (11)
                null, //Accion mesa (12)
                "", //Tiempo uso de mesa - RND (13)
                null, //Tiempo uso de mesa (14)
                null, //Tiempo fin uso mesa (15)
                null, //Tiempo consumicion - RND (16)
                null, //Tiempo de consumicion (17)
                null, //Tiempo fin de consumicion (18)
                null, //Cajero - Estado (19)
                cajero.getEstado(),//Cajero - Cola (20)
                cajero.getCola(), //Empleado 1 - Estado (21)
                empleado1.getEstado(), //Empleado 1 - Cola (21)
                empleado1.getCola(), //Empleado 2 - Estado (21)
                empleado2.getEstado(), //Empleado 2 - Cola (21)
                0.0, //Tiempo de permanencia acumulado (25)
                0 //Cantidad clientes en cafeteria (26)
            });
            return;
        }

        boolean evitarTiempoLlegada = model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_LLEGADA) == null;
        double tiempoProxLlegadaCliente = (evitarTiempoLlegada ? 0.0 : (double) (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_LLEGADA)));

        boolean evitarTiempoFinAtencionCaja = model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ATENCION) == null;
        double tiempoFinAtencionCaja = (evitarTiempoFinAtencionCaja ? 0.0 : (double) (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ATENCION)));

        boolean evitarTiempoEntregaPedido = model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ENTREGA) == null;
        double tiempoEntregaPedido = evitarTiempoEntregaPedido ? 0.0 : (double) (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ENTREGA));

        boolean evitarTiempoFinUsoMesa = model.getValueAt(model.getRowCount() - 1, COL_FIN_USO) == null;
        double tiempoFinUsoMesa = evitarTiempoFinUsoMesa ? 0.0 : (double) (model.getValueAt(model.getRowCount() - 1, COL_FIN_USO));

        boolean evitarTiempoConsumicion = model.getValueAt(model.getRowCount() - 1, COL_FIN_CONSUMICION) == null;
        double tiempoFinConsumicion = evitarTiempoConsumicion ? 0.0 : (double) (model.getValueAt(model.getRowCount() - 1, COL_FIN_CONSUMICION));

        if (((tiempoProxLlegadaCliente < tiempoFinAtencionCaja || evitarTiempoFinAtencionCaja)
                && (tiempoProxLlegadaCliente < tiempoEntregaPedido || evitarTiempoEntregaPedido)
                && (tiempoProxLlegadaCliente < tiempoFinUsoMesa || evitarTiempoFinUsoMesa)
                && (tiempoProxLlegadaCliente < tiempoFinConsumicion || evitarTiempoConsumicion))
                && !evitarTiempoLlegada) //                || //osea es segunda vuelta
        //                (tiempoFinAtencionCaja == 0 && tiempoEntregaPedido == 0 && tiempoFinUsoMesa == 0 && tiempoFinConsumicion == 0))
        {

            //tiempoProxLlegadaCliente es el proximo evento
            System.out.println("el próximo");
            setEvento(EVN_LLEGADA);
            setReloj(tiempoProxLlegadaCliente);

            float rnd1TiempoLlegada;
            float rnd2TiempoLlegada;
            rnd1TiempoLlegada = r.nextFloat();
            rnd2TiempoLlegada = r.nextFloat();
            double tiempoLlegada = Formulas.llegadaCliente(rnd1TiempoLlegada, rnd1TiempoLlegada, media, desviacion);
            float rndAccion = r.nextFloat();

            //true y se calcula el tiempo que tarda en usar la mesa
            if (rndAccion <= ((float) entranAMesa / 100)) {

                System.out.println("entra a la mesa");
                float rndTiempoUtilizacionMesa = r.nextFloat();
                double tiempoFinUtilizacionMesa = Formulas.tiempoUtilizacionMesa(tiempoUtilizacionMesa1, tiempoUtilizacionMesa2, rndTiempoUtilizacionMesa);
                c1 = new Cliente("Utilizando mesa", reloj, reloj + tiempoFinUtilizacionMesa);

                lista.add(c1);
//                model.addColumn("Cliente: Estado");
//                model.addColumn("Cliente: Entrada al sistema");
//                model.addColumn("Cliente: Partida del sistema");

                model.addRow(new Object[]{
                    evento, //Evento (0)
                    reloj, //Reloj (1)
                    rnd1TiempoLlegada, //Llegada cliente - RND1 (2)
                    rnd2TiempoLlegada, //Llegada cliente - RND2 (3)
                    tiempoLlegada, //Tiempo llegada cliente (4)
                    reloj + tiempoLlegada, //Próxima Llegada cliente (5)
                    rndAccion,//Acción - RND (6)
                    "Utiliza mesa", //Accion : mesa o a comprar (7)
                    null,//Tiempo fin atención caja (8)
                    null,//Tiempo espera pedido - RND (9)
                    null, //Tiempo espera pedido (10)
                    null,//Accion mesa: - RND (11)
                    null, //Accion mesa (12)
                    rndTiempoUtilizacionMesa,//Tiempo uso de mesa - RND (13)
                    tiempoFinUtilizacionMesa,//Tiempo uso de mesa (14)
                    c1.getHoraPartida(),//Tiempo fin uso mesa (15)
                    null,//Tiempo consumicion - RND (16)
                    null,//Tiempo de consumicion (17)
                    null,//Tiempo fin de consumicion (18)
                    cajero.getEstado(),//Cajero - Estado (19)
                    cajero.getCola(),//Cajero - Cola (20)
                    empleado1.getEstado(),//Empleado 1 - Estado (21)
                    empleado1.getCola(),//Empleado 1 - Cola (22)
                    empleado2.getEstado(),//Empleado 2 - Estado (23)
                    empleado2.getCola(),//Empleado 2 - Cola(24)
                    // mantengo porque no se va aun.
                    (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_PERMAN_AC)), //Tiempo de permanencia acumulado(25)
                    // Se incrementa siempre que entra un cliente
                    ((int) (model.getValueAt(model.getRowCount() - 1, COL_CANT_CLIENTES_CONT))) + 1}); //Cantidad clientes en cafeteria (26)

            } //false, entra a comprar
            else {
                System.out.println("entra a comprar");
                tiempoFinAtencionCaja = reloj + tiempoTicket;

               

                c1 = new Cliente("Comprando", reloj, reloj + tiempoFinAtencionCaja);
                 if ("LIBRE".equalsIgnoreCase(cajero.getEstado())) {
                    cajero.setOcupado();
                    c1.quienMeAtiende("CAJERO");
                } else {
                    cajero.aumentarCola();
                }
                lista.add(c1);
//                model.addColumn("Cliente: Estado");
//                model.addColumn("Cliente: Entrada al sistema");
//                model.addColumn("Cliente: Partida del sistema");

//TODO, revisar todo este add row!!!!   --- Faltaba un campo
                model.addRow(new Object[]{
                    evento, //Evento (0)
                    reloj, //Reloj (1)
                    rnd1TiempoLlegada, //Llegada cliente - RND1 (2)
                    rnd2TiempoLlegada, //Llegada cliente - RND2 (3)
                    tiempoLlegada, //Tiempo llegada cliente (4)
                    reloj + tiempoLlegada, //Próxima Llegada cliente (5)
                    rndAccion, //Acción - RND (6)
                    "Compra", //Accion : mesa o a comprar (7)
                    tiempoFinAtencionCaja, //Tiempo fin atención caja (8)
                    null, //Tiempo espera pedido - RND (9)
                    null, //Tiempo espera pedido (10)
                    null, //Accion mesa: - RND (11)
                    null, //Accion mesa (12)
                    "",//Tiempo uso de mesa - RND (13)
                    null, //Tiempo uso de mesa (14)
                    null, //Tiempo fin uso mesa (15)
                    null, //Tiempo consumicion - RND (16)
                    null, //Tiempo de consumicion (17)
                    null, //Tiempo fin de consumicion (18)
                    cajero.getEstado(), //Cajero - Estado (19)
                    cajero.getCola(), //Cajero - Cola (20)
                    empleado1.getEstado(), //Empleado 1 - Estado (21)
                    empleado1.getCola(), //Empleado 1 - Cola (22)
                    empleado2.getEstado(),//Empleado 2 - Estado (23)
                    empleado2.getCola(),//Empleado 2 - Cola (24)
                    (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_PERMAN_AC)), //Tiempo de permanencia (25)
                    ((int) (model.getValueAt(model.getRowCount() - 1, COL_CANT_CLIENTES_CONT))) + 1}); //Cantidad clientes en cafeteria (26)
            }

        } else if ((tiempoFinAtencionCaja < tiempoEntregaPedido || evitarTiempoEntregaPedido)
                && (tiempoFinAtencionCaja < tiempoFinUsoMesa || evitarTiempoFinAtencionCaja)
                && (tiempoFinAtencionCaja < tiempoFinConsumicion || evitarTiempoConsumicion)
                && !evitarTiempoFinAtencionCaja) {

            // tiempoFinAtencionCaja es el proximo evento
            setEvento(EVN_FIN_ATENCION);
            setReloj(tiempoFinAtencionCaja);
            float rndEspera = r.nextFloat();
            double tiempoEntrega = Formulas.tiempoEntregaPedido(tiempoEspera, rndEspera);

            if (cajero.getCola() == 0) {
                cajero.setLibre();
            } else {
                cajero.disminuirCola();
            }

            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getHoraPartida() == tiempoFinAtencionCaja) {
                    c1 = lista.get(i);
                    c1.setEstado(evento);
                    c1.setHoraPartida(reloj + tiempoEntrega);
                    break;
                }
            }

            if ("LIBRE".equalsIgnoreCase(empleado1.getEstado())) {
                empleado1.setOcupado();
                c1.quienMeAtiende("EMPLEADO1");

            } else if ("LIBRE".equalsIgnoreCase(empleado2.getEstado())) {
                empleado2.setOcupado();
                c1.quienMeAtiende("EMPLEADO2");
            } else {
                empleado1.aumentarCola();
                empleado2.aumentarCola();
            }

            model.addRow(new Object[]{
                evento,//Evento (0)
                reloj, //Reloj (1)
                null, //Llegada cliente - RND1 (2)
                null, //Llegada cliente - RND2 (3)
                null, //Tiempo llegada cliente (4)
                model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_LLEGADA), //Próxima Llegada cliente (5)
                null, //Acción - RND (6)
                "",//Accion : mesa o a comprar (7)
                model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ATENCION), //Tiempo fin atención caja (8)(me parece que trae el mismo que estoy manipulando)
                rndEspera, //Tiempo espera pedido - RND (9)
                reloj + tiempoEntrega, //Tiempo espera pedido (10)
                null, //Accion mesa: - RND (11)
                model.getValueAt(model.getRowCount() - 1, COL_ACCION_MESA), //Accion mesa (12)
                null, //Tiempo uso de mesa - RND (13)
                null,//Tiempo uso de mesa (14)
                null, //Tiempo fin uso mesa (15)
                null, //Tiempo consumicion - RND (16)
                null, //Tiempo de consumicion (17)
                null,//Tiempo fin de consumicion (18)
                cajero.getEstado(), //Cajero - Estado (19)
                cajero.getCola(), //Cajero - Cola (20)
                empleado1.getEstado(), //Empleado 1 - Estado (21)
                empleado1.getCola(), //Empleado 1 - Cola (22)
                empleado2.getEstado(), //Empleado 2 - Estado (23)
                empleado2.getCola(),//Empleado 2 - Cola (24)
                model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_PERMAN_AC), //Tiempo de permanencia (25)
                model.getValueAt(model.getRowCount() - 1, COL_CANT_CLIENTES_CONT)});//Cantidad clientes en cafeteria (26)

        } else if ((tiempoEntregaPedido < tiempoFinUsoMesa || evitarTiempoFinUsoMesa)
                && (tiempoEntregaPedido < tiempoFinConsumicion || evitarTiempoConsumicion)
                && !evitarTiempoEntregaPedido) {
            // tiempoEntregaPedido es el proximo evento -- QUEDE acá
            setEvento(EVN_ENTREGA);
            setReloj(tiempoEntregaPedido);
            int posicion = 0;
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getHoraPartida() == tiempoEntregaPedido) {
                    c1 = lista.get(i);
                    posicion = i;
                    break;
                }
            }

            if (c1.getQuienMeAtiende().equals("EMPLEADO1") && empleado1.getCola() == 0) {
                empleado1.setLibre();
            } else if (c1.getQuienMeAtiende().equals("EMPLEADO2") && empleado2.getCola() == 0) {
                empleado2.setLibre();
            }

            float rndAccion = r.nextFloat();

            if (rndAccion <= ((float) sientaEnMesa / 100)) {
                //COMPRÓ Y SE SIENTA EN LA MESA
                float rndTiempoConsumicion = r.nextFloat();
                double finUsoMesa = Formulas.tiempoConsumicion(rndTiempoConsumicion);
                c1.setEstado("Sienta en mesa");
                c1.setHoraPartida(reloj + finUsoMesa);
                model.addRow(new Object[]{
                    evento,//Evento (0)
                    reloj, //Reloj (1)
                    null,//Llegada cliente - RND1 (2)
                    null,//Llegada cliente - RND2 (3)
                    null,//Tiempo llegada cliente (4)
                    model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_LLEGADA), //Próxima Llegada cliente (5)
                    null, //Acción - RND (6)
                    null, //Accion : mesa o a comprar (7)
                    (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ATENCION)),//Tiempo fin atención caja (8)
                    null,//Tiempo espera pedido - RND (9)
                    null,//Tiempo espera pedido (10)
                    (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ENTREGA)),//Accion mesa: - RND (11)
                    rndAccion, //Accion mesa - se sienta en mesa(12)
                    "Sienta en mesa",//Tiempo uso de mesa - RND (13)
                    null, //Tiempo uso de mesa (14)
                    (model.getValueAt(model.getRowCount() - 1, COL_FIN_USO)), //Tiempo fin uso mesa (15)
                    rndTiempoConsumicion, //Tiempo consumicion - RND (16)
                    finUsoMesa,//Tiempo de consumicion (17)
                    c1.getHoraPartida(), //Tiempo fin de consumicion (18)
                    cajero.getEstado(),//Cajero - Estado (19)
                    cajero.getCola(), //Cajero - Cola (20)
                    empleado1.getEstado(), //Empleado 1 - Estado (21)
                    empleado1.getCola(),//Empleado 1 - Cola (22)
                    empleado2.getEstado(),//Empleado 2 - Estado (23)
                    empleado2.getCola(),//Empleado 2 - Cola (24)
                    model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_PERMAN_AC), //Tiempo de permanencia (25)
                    model.getValueAt(model.getRowCount() - 1, COL_CANT_CLIENTES_CONT)});//Cantidad clientes en cafeteria (26)
            } //false se retira
            else {
                //ESTE ES EL QUE SE RETIRA Y SOLO COMPRÓ
                System.out.println("SOLO COMPRO Y SE TOMA EL PALO");
                model.addRow(new Object[]{
                    evento,//Evento (0)
                    reloj, //Reloj (1)
                    null,//Llegada cliente - RND1 (2)
                    null,//Llegada cliente - RND2 (3)
                    null,//Tiempo llegada cliente (4)
                    model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_LLEGADA), //Próxima Llegada cliente (5)
                    null, //Acción - RND (6)
                    null, //Accion : mesa o a comprar (7)
                    (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ATENCION)),//Tiempo fin atención caja (8)
                    null,//Tiempo espera pedido - RND (9)
                    null,//Tiempo espera pedido (10)
                    (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ENTREGA)),//Accion mesa: - RND (11)
                    rndAccion, //Accion mesa - se retira (12)
                    "Se retira",//Tiempo uso de mesa - RND (13)
                    null, //Tiempo uso de mesa (14)
                    null,//Tiempo fin uso mesa (15)
                    null, //Tiempo consumicion - RND (16)
                    null,//Tiempo de consumicion (17)
                    null, //Tiempo fin de consumicion (18)
                    cajero.getEstado(),//Cajero - Estado (19)
                    cajero.getCola(), //Cajero - Cola (20)
                    empleado1.getEstado(), //Empleado 1 - Estado (21)
                    empleado1.getCola(),//Empleado 1 - Cola (22)
                    empleado2.getEstado(),//Empleado 2 - Estado (23)
                    empleado2.getCola(),//Empleado 2 - Cola (24)
                    (double) model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_PERMAN_AC) + (c1.getHoraPartida() - c1.getHoraLlegada()), //Tiempo de permanencia (25)
                    (int) model.getValueAt(model.getRowCount() - 1, COL_CANT_CLIENTES_CONT) + 1});//Cantidad clientes en cafeteria (26)
                lista.remove(posicion);
            }

        } else if ((tiempoFinUsoMesa < tiempoFinConsumicion || evitarTiempoConsumicion)
                && !evitarTiempoFinUsoMesa) {
            // TERMINO DE USAR LA MESA Y SE VA
            System.out.println("NO COMIO, SOLO USO LA MESA Y SE VA");
            setEvento(EVN_UTILIZACION);
            setReloj(tiempoFinUsoMesa);
            int posicion = 0;
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getHoraPartida() == tiempoEntregaPedido) {
                    c1 = lista.get(i);
                    posicion = i;
                    break;
                }
            }
            model.addRow(new Object[]{
                evento,//Evento (0)
                reloj, //Reloj (1)
                null,//Llegada cliente - RND1 (2)
                null,//Llegada cliente - RND2 (3)
                null,//Tiempo llegada cliente (4)
                model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_LLEGADA), //Próxima Llegada cliente (5)
                null, //Acción - RND (6)
                null, //Accion : mesa o a comprar (7)
                (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ATENCION)),//Tiempo fin atención caja (8)
                null,//Tiempo espera pedido - RND (9)
                null,//Tiempo espera pedido (10)
                (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ENTREGA)),//Accion mesa: - RND (11)
                null, //Accion mesa - se retira (12)
                null,//Tiempo uso de mesa - RND (13)
                null, //Tiempo uso de mesa (14)
                null,//Tiempo fin uso mesa (15)
                null, //Tiempo consumicion - RND (16)
                null,//Tiempo de consumicion (17)
                null, //Tiempo fin de consumicion (18)
                cajero.getEstado(),//Cajero - Estado (19)
                cajero.getCola(), //Cajero - Cola (20)
                empleado1.getEstado(), //Empleado 1 - Estado (21)
                empleado1.getCola(),//Empleado 1 - Cola (22)
                empleado2.getEstado(),//Empleado 2 - Estado (23)
                empleado2.getCola(),//Empleado 2 - Cola (24)
                (double) model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_PERMAN_AC) + (c1.getHoraPartida() - c1.getHoraLlegada()), //Tiempo de permanencia (25)
                (int) model.getValueAt(model.getRowCount() - 1, COL_CANT_CLIENTES_CONT) + 1});//Cantidad clientes en cafeteria (26)
            lista.remove(posicion);
            //Faltan los clientes
        } else {
            // tiempoFinConsumicion es el proximo evento
            System.out.println("TERMINO DE CONSUMIR Y SE LAS TOMA");
            setEvento(EVN_CONSUMICION);
            setReloj(tiempoFinConsumicion);

            int posicion = 0;
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getHoraPartida() == tiempoEntregaPedido) {
                    c1 = lista.get(i);
                    posicion = i;
                    break;
                }
            }
            model.addRow(new Object[]{
                evento,//Evento (0)
                reloj, //Reloj (1)
                null,//Llegada cliente - RND1 (2)
                null,//Llegada cliente - RND2 (3)
                null,//Tiempo llegada cliente (4)
                model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_LLEGADA), //Próxima Llegada cliente (5)
                null, //Acción - RND (6)
                null, //Accion : mesa o a comprar (7)
                (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ATENCION)),//Tiempo fin atención caja (8)
                null,//Tiempo espera pedido - RND (9)
                null,//Tiempo espera pedido (10)
                (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ENTREGA)),//Accion mesa: - RND (11)
                null, //Accion mesa - se retira (12)
                null,//Tiempo uso de mesa - RND (13)
                null, //Tiempo uso de mesa (14)
                null,//Tiempo fin uso mesa (15)
                null, //Tiempo consumicion - RND (16)
                null,//Tiempo de consumicion (17)
                null, //Tiempo fin de consumicion (18)
                cajero.getEstado(),//Cajero - Estado (19)
                cajero.getCola(), //Cajero - Cola (20)
                empleado1.getEstado(), //Empleado 1 - Estado (21)
                empleado1.getCola(),//Empleado 1 - Cola (22)
                empleado2.getEstado(),//Empleado 2 - Estado (23)
                empleado2.getCola(),//Empleado 2 - Cola (24)
                (double) model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_PERMAN_AC) + (c1.getHoraPartida() - c1.getHoraLlegada()), //Tiempo de permanencia (25)
                (int) model.getValueAt(model.getRowCount() - 1, COL_CANT_CLIENTES_CONT) + 1});//Cantidad clientes en cafeteria (26)
            lista.remove(posicion);
        }

    }

    private double calcularFinPermanencia(int colTiempoPermanencia) {
        double ret = (double) model.getValueAt(model.getRowCount() - 1, colTiempoPermanencia);
        //Tiempo permanencia = tiempo permanencia anterior +
        //[sacar de arriba](diferencia entre tiempo de partida y tiempo de llegada del cliente que se va en este momento del reloj)
        for (Cliente cl : lista) {
            if (cl.getHoraPartida() == reloj) {
                double dif = cl.getHoraPartida() - cl.getHoraLlegada();
                lista.remove(cl);
                return ret + dif;
            }
        }
        return ret;
    }
}
