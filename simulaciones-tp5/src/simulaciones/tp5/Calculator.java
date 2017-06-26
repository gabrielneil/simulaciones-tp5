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
    public static final int COL_COLA_EMPLEADOS = 24;
    /**
     * Col 24 - Estado Empleado 2
     */
    public static final int COL_ESTADO_EMPL2 = 23;
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
        this.tiempoTicket = (float) tiempoTicket / 60; //Regla de 3 para pasar a minutos
        this.tiempoEspera = (float) tiempoEspera / 60; //Regla de 3 para pasar a minutos
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
            if (numeroVuelta ==7) {
            break;
        }
            cantFilas++;
        }
    }
    
    double minProximaLLegada = 0;
    double minTerminaAtencionCaja = 0;
    double minTerminaEntrega = 0;
    double minTerminaUsarMesa = 0;
    double minTerminaConsumicion = 0;
    int numeroVuelta=0;
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
            minProximaLLegada = reloj + tiempoLlegada;
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
            return;
        }
        System.out.println(numeroVuelta);
        
        boolean evitarTiempoLlegada = (minProximaLLegada == 0)? true:false;
        System.out.println(evitarTiempoLlegada);
        
        boolean evitarTiempoFinAtencionCaja = (minTerminaAtencionCaja == 0)? true:false;
        System.out.println(evitarTiempoFinAtencionCaja);
        
        boolean evitarTiempoEntregaPedido = (minTerminaEntrega == 0)? true:false;
        System.out.println(evitarTiempoEntregaPedido);
        
        boolean evitarTiempoFinUsoMesa = (minTerminaUsarMesa == 0)? true:false;
        System.out.println(evitarTiempoFinUsoMesa);
        
        boolean evitarTiempoConsumicion = (minTerminaConsumicion == 0)? true:false;
        System.out.println(evitarTiempoConsumicion);
        numeroVuelta++;
        
        if (!evitarTiempoLlegada && 
                (minProximaLLegada < minTerminaAtencionCaja || evitarTiempoFinAtencionCaja) &&
                (minProximaLLegada < minTerminaEntrega || evitarTiempoEntregaPedido)&&
                (minProximaLLegada < minTerminaUsarMesa || evitarTiempoFinUsoMesa)&&
                (minProximaLLegada < minTerminaConsumicion || evitarTiempoConsumicion)) 
        {

            //tiempoProxLlegadaCliente es el proximo evento
            System.out.println("el próximo");
            setEvento(EVN_LLEGADA);
            setReloj(minProximaLLegada);
            
            float rnd1TiempoLlegada;
            float rnd2TiempoLlegada;
            rnd1TiempoLlegada = r.nextFloat();
            rnd2TiempoLlegada = r.nextFloat();
            double tiempoLlegada = Formulas.llegadaCliente(rnd1TiempoLlegada, rnd1TiempoLlegada, media, desviacion);
            minProximaLLegada = reloj + tiempoLlegada;
            float rndAccion = r.nextFloat();

            //true y se calcula el tiempo que tarda en usar la mesa
            if (rndAccion <= ((float) entranAMesa / 100)) {

                System.out.println("entra a la mesa");
                float rndTiempoUtilizacionMesa = r.nextFloat();
                double tiempoUtilizacionMesa = Formulas.tiempoUtilizacionMesa(tiempoUtilizacionMesa1, tiempoUtilizacionMesa2, rndTiempoUtilizacionMesa);
                double tiempoFinUtilizacionMesa = reloj + tiempoUtilizacionMesa;
                c1 = new Cliente("Utilizando mesa", reloj, tiempoFinUtilizacionMesa);
                lista.add(c1);
                
                    //si sos el menor, seteate
                    if (tiempoFinUtilizacionMesa < minTerminaUsarMesa || minTerminaUsarMesa == 0 ) {
                        minTerminaUsarMesa = tiempoFinUtilizacionMesa; 
                    }
//                model.addColumn("Cliente: Estado");
//                model.addColumn("Cliente: Entrada al sistema");
//                model.addColumn("Cliente: Partida del sistema");

                model.addRow(new Object[]{
                    evento, //Evento (0)
                    reloj, //Reloj (1)
                    rnd1TiempoLlegada, //Llegada cliente - RND1 (2)
                    rnd2TiempoLlegada, //Llegada cliente - RND2 (3)
                    tiempoLlegada, //Tiempo llegada cliente (4)
                    minProximaLLegada, //Próxima Llegada cliente (5)
                    rndAccion,//Acción - RND (6)
                    "Utiliza mesa", //Accion : mesa o a comprar (7)
                    null,//Tiempo fin atención caja (8)
                    null,//Tiempo espera pedido - RND (9)
                    null, //Tiempo espera pedido (10)
                    null,//Tiempo entrega de pedido(11)
                    null,//Accion mesa: - RND (12)
                    null, //Accion mesa (13)
                    rndTiempoUtilizacionMesa,//Tiempo uso de mesa - RND (14)
                    tiempoUtilizacionMesa,//Tiempo uso de mesa (15)
                    tiempoFinUtilizacionMesa,//Tiempo fin uso mesa (16)
                    null,//Tiempo consumicion - RND (17)
                    null,//Tiempo de consumicion (18)
                    null,//Tiempo fin de consumicion (19)
                    cajero.getEstado(),//Cajero - Estado (20)
                    cajero.getCola(),//Cajero - Cola (21)
                    empleado1.getEstado(),//Empleado 1 - Estado (22)
                    empleado2.getEstado(),//Empleado 2 - Estado (23)
                    empleado2.getCola(),//Empleado 2 - Cola(24)
                    // mantengo porque no se va aun.
                    (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_PERMAN_AC)), //Tiempo de permanencia acumulado(25)
                    // Se incrementa siempre que entra un cliente
                    ((int) (model.getValueAt(model.getRowCount() - 1, COL_CANT_CLIENTES_CONT))) + 1}); //Cantidad clientes en cafeteria (26)

            } //false, entra a comprar
            else {
                System.out.println("entra a comprar");
                double tiempoFinAtencionCaja = reloj + tiempoTicket;
                
                if (tiempoFinAtencionCaja < minTerminaAtencionCaja || minTerminaAtencionCaja == 0 ) {
                        minTerminaAtencionCaja = tiempoFinAtencionCaja; 
                    }
                
                c1 = new Cliente("Comprando", reloj, tiempoFinAtencionCaja);
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
                model.addRow(new Object[]{
                    evento, //Evento (0)
                    reloj, //Reloj (1)
                    rnd1TiempoLlegada, //Llegada cliente - RND1 (2)
                    rnd2TiempoLlegada, //Llegada cliente - RND2 (3)
                    tiempoLlegada, //Tiempo llegada cliente (4)
                    minProximaLLegada, //Próxima Llegada cliente (5)
                    rndAccion, //Acción - RND (6)
                    "Compra", //Accion : mesa o a comprar (7)
                    tiempoFinAtencionCaja, //Tiempo fin atención caja (8)
                    null, //Tiempo espera pedido - RND (9)
                    null, //Tiempo espera pedido (10)
                    null,//Tiempo entrega de pedido(11)
                    null, //Accion mesa: - RND (12)
                    null, //Accion mesa (13)
                    null,//Tiempo uso de mesa - RND (14)
                    null, //Tiempo uso de mesa (15)
                    minTerminaUsarMesa, //Tiempo fin uso mesa (16)
                    null, //Tiempo consumicion - RND (17)
                    null, //Tiempo de consumicion (18)
                    null, //Tiempo fin de consumicion (19)
                    cajero.getEstado(), //Cajero - Estado (20)
                    cajero.getCola(), //Cajero - Cola (21)
                    empleado1.getEstado(), //Empleado 1 - Estado (22)
                    empleado2.getEstado(),//Empleado 2 - Estado (23)
                    empleado2.getCola(),//Empleado 2 - Cola (24)
                    (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_PERMAN_AC)), //Tiempo de permanencia acumulado(25)
                    ((int) (model.getValueAt(model.getRowCount() - 1, COL_CANT_CLIENTES_CONT))) + 1}); //Cantidad clientes en cafeteria (26)
            }

        } else if
                (!evitarTiempoFinAtencionCaja && 
                (minTerminaAtencionCaja < minTerminaEntrega || evitarTiempoEntregaPedido)&&
                (minTerminaAtencionCaja < minTerminaUsarMesa || evitarTiempoFinUsoMesa)&&
                (minTerminaAtencionCaja < minTerminaConsumicion || evitarTiempoConsumicion)) 
        {
            System.out.println("Fin atención caja");
            // tiempoFinAtencionCaja es el proximo evento - SE MANDA A LA COLA DE LOS DOS CHABONES
            setEvento(EVN_FIN_ATENCION);
            setReloj(minTerminaAtencionCaja);
            float rndEspera = r.nextFloat();
            double tiempoEntrega = Formulas.tiempoEntregaPedido(tiempoEspera, rndEspera);
            double finTiempoEntrega = tiempoEntrega + reloj;

            if (cajero.getCola() == 0) {
                cajero.setLibre();
            } else {
                cajero.disminuirCola();
            }
            int posicion = 0;
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getHoraPartida() == minTerminaAtencionCaja) {
                    posicion = i;
                    System.out.println("el numero de posicion es"+ i);
                    c1 = lista.get(i);
                    c1.setEstado(evento);
                    c1.setHoraPartida(finTiempoEntrega);
                    c1.changeIgnorar();
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
                minProximaLLegada, //Próxima Llegada cliente (5)
                null, //Acción - RND (6)
                "",//Accion : mesa o a comprar (7)
                model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ATENCION), //Tiempo fin atención caja (8)(me parece que trae el mismo que estoy manipulando)
                rndEspera, //Tiempo espera pedido - RND (9)
                tiempoEntrega, //Tiempo espera pedido (10)
                finTiempoEntrega, //Tiempo entrega de pedido (11)
                null, //Accion mesa: - RND (12)
                null, //Accion mesa (13)
                null, //Tiempo uso de mesa - RND (14)
                null,//Tiempo uso de mesa (15)
                minTerminaUsarMesa, //Tiempo fin uso mesa (16)
                null, //Tiempo consumicion - RND (17)
                null, //Tiempo de consumicion (18)
                minTerminaConsumicion,//Tiempo fin de consumicion (19)
                cajero.getEstado(), //Cajero - Estado (20)
                cajero.getCola(), //Cajero - Cola (21)
                empleado1.getEstado(), //Empleado 1 - Estado (22)
                empleado2.getEstado(), //Empleado 2 - Estado (23)
                empleado2.getCola(),//Empleado 2 - Cola (24)
                model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_PERMAN_AC), //Tiempo de permanencia acumulado(25)
                model.getValueAt(model.getRowCount() - 1, COL_CANT_CLIENTES_CONT)});//Cantidad clientes en cafeteria (26)

            double menorProximo = 0;
            for (int i = 0; i < lista.size(); i++) {
                Cliente aux= lista.get(i);
                if ((menorProximo == 0 && aux.getEstado().equals("Comprando")) || (menorProximo > aux.getHoraPartida() && aux.getEstado().equals("Comprando") && !aux.ignorar())) {
                    System.out.println("AHI ENTRO Y SE ESTA FIJANDO");
                    menorProximo = lista.get(i).getHoraPartida();
                }
            }
            
            minTerminaAtencionCaja = menorProximo;
            c1.changeIgnorar();
            if (minTerminaEntrega> finTiempoEntrega) {
                minTerminaEntrega = finTiempoEntrega;
            }
            
        } else if(!evitarTiempoEntregaPedido && 
                (minTerminaEntrega < minTerminaUsarMesa || evitarTiempoFinUsoMesa)&&
                (minTerminaEntrega < minTerminaConsumicion || evitarTiempoConsumicion)) 
        {
            // tiempoEntregaPedido es el proximo evento 
            setEvento(EVN_ENTREGA);
            setReloj(minTerminaEntrega);
            int posicion = 0;
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getHoraPartida() == minTerminaEntrega) {
                    posicion = i;
                    System.out.println("el numero de posicion es"+ i);
                    c1 = lista.get(i);
                    c1.changeIgnorar();
                    break;
                }
            }

            if (c1.getQuienMeAtiende().equals("EMPLEADO1")) {
                if (empleado1.getCola() == 0) {
                    empleado1.setLibre();
                }else{
                    empleado1.disminuirCola();
                }
            } else if (c1.getQuienMeAtiende().equals("EMPLEADO2")) {
                if (empleado2.getCola() == 0) {
                    empleado2.setLibre();
                }else{
                    empleado2.disminuirCola();
                }
            }

            float rndAccion = r.nextFloat();

            if (rndAccion <= ((float) sientaEnMesa / 100)) {
                //COMPRÓ Y SE SIENTA EN LA MESA
                float rndTiempoConsumicion = r.nextFloat();
                double tiempoConsumicion = Formulas.tiempoConsumicion(rndTiempoConsumicion);
                double finConsumicion = tiempoConsumicion + reloj;
                c1.setEstado("Sienta en mesa");
//              minTerminaConsumicion
                c1.setHoraPartida(finConsumicion);
                model.addRow(new Object[]{
                    EVN_ENTREGA,//Evento (0)
                    reloj, //Reloj (1)
                    null,//Llegada cliente - RND1 (2)
                    null,//Llegada cliente - RND2 (3)
                    null,//Tiempo llegada cliente (4)
                    minProximaLLegada, //Próxima Llegada cliente (5)
                    null, //Acción - RND (6)
                    null, //Accion : mesa o a comprar (7)
                    minTerminaAtencionCaja,//Tiempo fin atención caja (8)
                    null,//Tiempo espera pedido - RND (9)
                    null,//Tiempo espera pedido (10)
                    minTerminaEntrega,//Tiempo entrega de pedido (11)
                    rndAccion,//Accion mesa: - RND (12)
                    c1.getEstado(),//Accion mesa (13)
                    null, //Tiempo uso de mesa - RND (14)
                    null, //Tiempo uso mesa (15)
                    minTerminaUsarMesa, //Tiempo fin uso mesa (16)
                    rndTiempoConsumicion, //Tiempo consumicion - RND (17)
                    tiempoConsumicion,//Tiempo de consumicion (18)
                    finConsumicion, //Tiempo fin de consumicion (19)
                    cajero.getEstado(),//Cajero - Estado (20)
                    cajero.getCola(), //Cajero - Cola (21)
                    empleado1.getEstado(), //Empleado 1 - Estado (22)
                    empleado2.getEstado(),//Empleado 2 - Estado (23)
                    empleado2.getCola(),//Empleado 2 - Cola (24)
                    model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_PERMAN_AC), //Tiempo de permanencia acumulado(25)
                    model.getValueAt(model.getRowCount() - 1, COL_CANT_CLIENTES_CONT)});//Cantidad clientes en cafeteria (26)
            
                double menorProximo = 0;
                for (int i = 0; i < lista.size(); i++) {
                Cliente aux= lista.get(i);
                if ((menorProximo == 0 && aux.getEstado().equals("Sienta en mesa")) || (menorProximo > aux.getHoraPartida() && aux.getEstado().equals("Sienta en mesa") && !aux.ignorar())) {
                    System.out.println("AHI ENTRO Y SE ESTA FIJANDO");
                    menorProximo = lista.get(i).getHoraPartida();
                }
            }
            
            minTerminaConsumicion = menorProximo;
            c1.changeIgnorar();
            if (minTerminaConsumicion > finConsumicion) {
                minTerminaConsumicion = finConsumicion;
                }
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
                    minProximaLLegada, //Próxima Llegada cliente (5)
                    null, //Acción - RND (6)
                    null, //Accion : mesa o a comprar (7)
                    minTerminaAtencionCaja,//Tiempo fin atención caja (8)
                    null,//Tiempo espera pedido - RND (9)
                    null,//Tiempo espera pedido (10)
                    minTerminaEntrega,//Tiempo entrega de pedido (11)
                    rndAccion, //Accion mesa: - RND (12)
                    "Se retira",//Accion mesa (13)
                    null, //Tiempo uso de mesa - RND(14)
                    null,//Tiempo uso mesa (15)
                    minTerminaUsarMesa,//Tiempo fin uso mesa (16)
                    null, //Tiempo consumicion - RND (17)
                    null,//Tiempo de consumicion (18)
                    minTerminaConsumicion, //Tiempo fin de consumicion (19)
                    cajero.getEstado(),//Cajero - Estado (20)
                    cajero.getCola(), //Cajero - Cola (21)
                    empleado1.getEstado(), //Empleado 1 - Estado (22)
                    empleado2.getEstado(),//Empleado 2 - Estado (23)
                    empleado2.getCola(),//Empleado 2 - Cola (24)
                    (double) model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_PERMAN_AC) + (c1.getHoraPartida() - c1.getHoraLlegada()), //Tiempo de permanencia acumulado(25)
                    (int) model.getValueAt(model.getRowCount() - 1, COL_CANT_CLIENTES_CONT)});//Cantidad clientes en cafeteria (26)
                lista.remove(posicion);
            }

        } else if ((minTerminaUsarMesa < minTerminaConsumicion || evitarTiempoConsumicion)
                && !evitarTiempoFinUsoMesa) {
            // TERMINO DE USAR LA MESA Y SE VA -- VOY ACA YA TOMANDO FORMA
            System.out.println("NO COMIO, SOLO USO LA MESA Y SE VA");
            setEvento(EVN_UTILIZACION);
            setReloj(minTerminaUsarMesa);
            int posicion = 0;
            
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getHoraPartida() == minTerminaUsarMesa) {
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
                minProximaLLegada, //Próxima Llegada cliente (5)
                null, //Acción - RND (6)
                null, //Accion : mesa o a comprar (7)
                minTerminaAtencionCaja,//Tiempo fin atención caja (8)
                null,//Tiempo espera pedido - RND (9)
                null,//Tiempo espera pedido (10)
                minTerminaEntrega,//Tiempo entrega de pedido (11)
                null,//Accion mesa: - RND (12)
                null, //Accion mesa - se retira (13)
                null,//Tiempo uso de mesa - RND (14)
                null, //Tiempo uso de mesa (15)
                null,//Tiempo fin uso mesa (16)
                null, //Tiempo consumicion - RND (17)
                null,//Tiempo de consumicion (18)
                minTerminaConsumicion, //Tiempo fin de consumicion (19)
                cajero.getEstado(),//Cajero - Estado (20)
                cajero.getCola(), //Cajero - Cola (21)
                empleado1.getEstado(), //Empleado 1 - Estado (22)
                empleado2.getEstado(),//Empleado 2 - Estado (23)
                empleado2.getCola(),//Empleado 2 - Cola (24)
                (double) model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_PERMAN_AC) + (c1.getHoraPartida() - c1.getHoraLlegada()), //Tiempo de permanencia (25)
                (int) model.getValueAt(model.getRowCount() - 1, COL_CANT_CLIENTES_CONT) + 1});//Cantidad clientes en cafeteria (26)
            
            lista.remove(posicion);
               double menorProximo = 0;
                for (int i = 0; i < lista.size(); i++) {
                Cliente aux= lista.get(i);
                if ((menorProximo == 0 && aux.getEstado().equals("Utilizando mesa")) || (menorProximo > aux.getHoraPartida() && aux.getEstado().equals("Utilizando mesa"))) {
                    System.out.println("AHI ENTRO Y SE ESTA FIJANDO");
                    menorProximo = lista.get(i).getHoraPartida();
                }
            }

                minTerminaUsarMesa = menorProximo;
            }
    //Faltan los clientes
         else {
            // tiempoFinConsumicion es el proximo evento AKAKAKAKAKAKAKAKAKAKAKAKAKAKAKAKAKAKAKAKAKAKAKAKAKAKAKAKAKAKAKAK
            System.out.println("TERMINO DE CONSUMIR Y SE LAS TOMA");
            setEvento(EVN_CONSUMICION);
            setReloj(minTerminaConsumicion);

            int posicion = 0;
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getHoraPartida() == minTerminaConsumicion) {
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
                minProximaLLegada, //Próxima Llegada cliente (5)
                null, //Acción - RND (6)
                null, //Accion : mesa o a comprar (7)
                minTerminaAtencionCaja,//Tiempo fin atención caja (8)
                null,//Tiempo espera pedido - RND (9)
                null,//Tiempo espera pedido (10)
                minTerminaEntrega,//Tiempo entrega de pedido (11)
                null,//Accion mesa: - RND (12)
                null, //Accion mesa - se retira (13)
                null,//Tiempo uso de mesa - RND (14)
                null, //Tiempo uso de mesa (15)
                minTerminaUsarMesa,//Tiempo fin uso mesa (16)
                null, //Tiempo consumicion - RND (17)
                null,//Tiempo de consumicion (18)
                minTerminaConsumicion, //Tiempo fin de consumicion (19)
                cajero.getEstado(),//Cajero - Estado (20)
                cajero.getCola(), //Cajero - Cola (21)
                empleado1.getEstado(), //Empleado 1 - Estado (22)
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
