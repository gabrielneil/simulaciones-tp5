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

    float tiempoTicket = 20*60;
    float tiempoEspera = 50*60;
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
    /**Col 5 - Proxima llegada cliente */
    public static final int COL_TIEMPO_LLEGADA = 5;
    //Col 6: RND accion del cliente
    /** Col 7 - Accion cliente: Compra / Usa mesa. */
    public static final int COL_ACCION_CLIENTE = 7;
    /** Col 8 - Tiempo fin atencion en caja (siempre reloj + 20s en el evento ...)*/
    public static final int COL_TIEMPO_ATENCION = 8;
    //Col 9: RND tiempo espera entrega pedido
    //Col 10: Tiempo espera pedido.
    /** Col 11 -  Tiempo entrega pedido */
    public static final int COL_TIEMPO_ENTREGA = 11;
    //Col 12:RND accion mesa
    /** Col 13 - Accion mesa */
    public static final int COL_ACCION_MESA = 13;
    //Col 14: RND tiempo uso mesa
    //Col 15: tiempo uso mesa
    /** Col 16 - Tiempo fin uso mesa (reloj + tiempo uso mesa*/
    public static final int COL_FIN_USO = 16;
    //Col 17 RND tiempo consumicion
    //Col 18 Tiempo consumuicion
    /** Col 19 - Tiempo fin consumicion */
    public static final int COL_FIN_CONSUMICION = 19;
    /** Col 20 - Estado Cajero */
    public static final int COL_ESTADO_CAJERO = 20;
    /** Col 21 - Cola Cajero */
    public static final int COL_COLA_CAJERO = 21;
    /** Col 22 - Estado Empleado 1*/
    public static final int COL_ESTADO_EMPL1 = 22;
    /** Col 23 - Cola EmpleadoS*/
    public static final int COL_COLA_EMPLEADOS = 23;
    /** Col 24 - Estado Empleado 2*/
    public static final int COL_ESTADO_EMPL2 = 24;
    /** Col 25 - Tiempo permanencia acumulado */
    public static final int COL_TIEMPO_PERMAN_AC = 25;
    /** Col 26 - Cantidad de clientes de la cantina Contador*/
    public static final int COL_CANT_CLIENTES_CONT = 26;
    /** Col 27 - Inicio columnas de clientes. */
    public static final int COL_INICIO_CLIENTES = 27;


    public Calculator(Controller controller) {
        this.controller = controller;
        this.tabla = new Tabla();
    }

    public void cargaTiempos(int tiempoTicket, int tiempoEspera, int tiempoConsumicion1, int tiempoConsumicion2, int tiempoUtilizacionMesa1, int tiempoUtilizacionMesa2) {
        this.tiempoTicket = (float) tiempoTicket*60; //Regla de 3 para pasar a minutos
        this.tiempoEspera = (float)tiempoEspera*60; //Regla de 3 para pasar a minutos
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
        while (reloj <= tiempoDeCorte)
        {
            simularAvance();
        }
      
        //puse 3600 porque si no, no puedo probar nunca nada ya que tira numeros altos la siguiente llegada(despues cambiar)
//        while (reloj <= 3600) {
//            simularAvance();
//        }
    }

//    public void primeraVuelta() {
//       
//        Random r = new Random();
//        float rnd1TiempoLlegada;
//        float rnd2TiempoLlegada;
//        //primera vuelta
//        rnd1TiempoLlegada = r.nextFloat();
//        rnd2TiempoLlegada = r.nextFloat();
//        double tiempoLlegada = Formulas.llegadaCliente(rnd1TiempoLlegada, rnd1TiempoLlegada, media, desviacion);
//        setEvento(EVN_INICIO);
//        
//        model.addRow(new Object[]{
//            evento, //0
//            reloj,  //1
//            rnd1TiempoLlegada, //2
//            rnd2TiempoLlegada, // 3
//            tiempoLlegada, //4
//            reloj + tiempoLlegada, //5
//            null,//6
//            "",//7
//            null,//8
//            null,//9
//            null,//10
//            null,//11
//            null,//12
//            "",//13
//            null,//14
//            null,//15
//            null,//16
//            null,//17
//            null, //18
//            null, //19
//            cajero.getEstado(),//20
//            cajero.getCola(),//21
//            empleado1.getEstado(),//22
//            empleado1.getCola(),//23
//            empleado2.getEstado(),//24
//            0.0,//25
//            0 //26
//        });
//    }

    public void simularAvance() {
        
        Random r = new Random();
        if (NO_EVN.equalsIgnoreCase(evento))
        {
            
            setEvento(EVN_INICIO);
            float rnd1TiempoLlegada;
            float rnd2TiempoLlegada;
            //primera vuelta
            rnd1TiempoLlegada = r.nextFloat();
            rnd2TiempoLlegada = r.nextFloat();
            double tiempoLlegada = Formulas.llegadaCliente(rnd1TiempoLlegada, rnd1TiempoLlegada, media, desviacion);
            model.addRow(new Object[]
            {
                evento, //0
                reloj, //1
                rnd1TiempoLlegada, //2
                rnd2TiempoLlegada, // 3
                tiempoLlegada, //4
                reloj + tiempoLlegada, //5
                null,//6
                "",//7
                null,//8
                null,//9
                null,//10
                null,//11
                null,//12
                "",//13
                null,//14
                null,//15
                null,//16
                null,//17
                null, //18
                null, //19
                cajero.getEstado(),//20
                cajero.getCola(),//21
                empleado1.getEstado(),//22
                empleado1.getCola(),//23
                empleado2.getEstado(),//24
                0.0,//25
                0 //26
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
                && !evitarTiempoLlegada)
//                || //osea es segunda vuelta
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
                double tiempoFinUtilizacionMesa = Formulas.tiempoUtilizacionMesa(tiempoUtilizacionMesa1,tiempoUtilizacionMesa2,rndTiempoUtilizacionMesa);
                Cliente c1 = new Cliente("Utilizando mesa", reloj, reloj + tiempoFinUtilizacionMesa);
                
                //la idea seria que donde hay ceros copia las cosas de la fila de arriba
                lista.add(c1);
                model.addColumn("Cliente: Estado");
                model.addColumn("Cliente: Entrada al sistema");
                model.addColumn("Cliente: Partida del sistema");
                
                model.addRow(new Object[]{
                    evento, 
                    reloj, 
                    rnd1TiempoLlegada, 
                    rnd2TiempoLlegada, 
                    tiempoLlegada,                    
                    reloj + tiempoLlegada,
                    rndAccion,
                    "Utiliza mesa", 
                    (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ATENCION)),
                    null,
                    null, 
                    (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ENTREGA)),
                    null, 
                    "",
                    //14                        //15                    //16 COL_FIN_USO
                    rndTiempoUtilizacionMesa,
                    tiempoFinUtilizacionMesa,
                    reloj + tiempoFinUtilizacionMesa ,
                    null,
                    null,
                    (model.getValueAt(model.getRowCount() - 1, COL_FIN_CONSUMICION)),
                    //20                //21            //22                    //23                //24
                    cajero.getEstado(),
                    cajero.getCola(),
                    empleado1.getEstado(),
                    empleado1.getCola(),
                    empleado2.getEstado(),
                    // mantengo porque no se va aun.
                    (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_PERMAN_AC)),
                    // Se incrementa siempre que entra un cliente
                    ((int)(model.getValueAt(model.getRowCount() - 1, COL_CANT_CLIENTES_CONT))) + 1});
                    
                
            } //false, entra a comprar
            else {
                System.out.println("entra a comprar");
                tiempoFinAtencionCaja = reloj + tiempoTicket;
                if ("LIBRE".equalsIgnoreCase(cajero.getEstado())) 
                {
                    cajero.setOcupado();
                } else {
                    cajero.aumentarCola();
                }
                
                Cliente cl = new Cliente("Comprando", reloj, reloj + tiempoFinAtencionCaja);
                lista.add(cl);
                model.addColumn("Cliente: Estado");
                model.addColumn("Cliente: Entrada al sistema");
                model.addColumn("Cliente: Partida del sistema");
                
                //TODO, revisar todo este add row!!!!
                model.addRow(new Object[]{
                    evento, reloj, rnd1TiempoLlegada, rnd2TiempoLlegada, tiempoLlegada,
                    null, rndAccion, "Compra", tiempoFinAtencionCaja, 0.0, 0.0, 0.0, 0.0, "",
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, cajero.getEstado(),
                    cajero.getCola(), empleado1.getEstado(), empleado1.getCola(), empleado2.getEstado(),
                    empleado2.getCola(),
                    0.0, 0.0,cl.getEstado(),cl.getHoraLlegada(),cl.getHoraPartida()});

            }

        } else if ((tiempoFinAtencionCaja < tiempoEntregaPedido || evitarTiempoEntregaPedido)
                && (tiempoFinAtencionCaja < tiempoFinUsoMesa || evitarTiempoFinAtencionCaja)
                && (tiempoFinAtencionCaja < tiempoFinConsumicion || evitarTiempoConsumicion)
                && !evitarTiempoFinAtencionCaja) {
            
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
            
            if ("LIBRE".equalsIgnoreCase(empleado1.getEstado())) {
                empleado1.setOcupado();
            } else if ("LIBRE".equalsIgnoreCase(empleado2.getEstado())) {
                empleado2.setOcupado();
            } else {
                empleado1.aumentarCola();
            }
            
            model.addRow(new Object[]{
                evento, reloj, 
                null, null, null,
                model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_LLEGADA), null, "",
                model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ATENCION),
                rndEspera, tiempoEntrega, reloj + tiempoEntrega, null, model.getValueAt(model.getRowCount() - 1, COL_ACCION_MESA), null,
                null , model.getValueAt(model.getRowCount() - 1, COL_FIN_USO), null, null, model.getValueAt(model.getRowCount() - 1, COL_FIN_CONSUMICION),
                cajero.getEstado(), cajero.getCola(), empleado1.getEstado(), empleado1.getCola(), empleado2.getEstado(),
                model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_PERMAN_AC), model.getValueAt(model.getRowCount() - 1, COL_CANT_CLIENTES_CONT),
                cl.getEstado(),cl.getHoraLlegada(),cl.getHoraPartida()});


        } else if ((tiempoEntregaPedido < tiempoFinUsoMesa || evitarTiempoFinUsoMesa)
                && (tiempoEntregaPedido < tiempoFinConsumicion || evitarTiempoConsumicion)
                && !evitarTiempoEntregaPedido) {
            // tiempoEntregaPedido es el proximo evento
            setEvento(EVN_ENTREGA);
            setReloj(tiempoEntregaPedido);

            if (empleado1.getCola() == 0 && empleado1.estaOcupado() && empleado2.estaOcupado()) 
            {
                empleado2.setLibre();
            }
            else if (empleado1.getCola() == 0 && empleado2.estaLibre())
            {
                empleado1.setLibre();
            }
            else {
                empleado1.disminuirCola();
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
                
                //TODO:ACA HAY UNA MEZCLA DE USO MESA Y CONSUMICION ... Ayuda
                //aca iria contemplado la parte del cliente que setea su estado a "consumir en mesa"
                float rndTiempoConsumicion = r.nextFloat();
                double finUsoMesa = Formulas.tiempoConsumicion(rndTiempoConsumicion);
                cl.setEstado("Sienta en mesa");
                cl.setHoraPartida(finUsoMesa);
                model.addRow(new Object[]{
                    evento, reloj, 
                    null, null,null,
                    model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_LLEGADA), null, null, (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ATENCION)),
                    null, null, (model.getValueAt(model.getRowCount() - 1, COL_TIEMPO_ENTREGA)), rndAccion, "Sienta en mesa",
                    null, (model.getValueAt(model.getRowCount() - 1, COL_FIN_USO)), rndTiempoConsumicion, finUsoMesa, reloj + rndTiempoConsumicion, cajero.getEstado(),
                    cajero.getCola(), empleado1.getEstado(), empleado1.getCola(), empleado2.getEstado(),
                    empleado2.getCola(),
                    (evento == EVN_CONSUMICION || evento == EVN_UTILIZACION) ? tiempoPermanenciaAcumulador + (cl.getHoraPartida() - cl.getHoraLlegada()) : tiempoPermanenciaAcumulador,
                    (evento == EVN_CONSUMICION || evento == EVN_UTILIZACION) ? cantidadClientesEnCafeteria += 1 : cantidadClientesEnCafeteria});
            } //false se retira
            else {
                //TODO resolver
                //como hacer el tema del retiro del cliente
                model.addRow(new Object[]{
                    evento, reloj, 0.0, 0.0, 0.0,
                    tiempoProxLlegadaCliente, 0.0, "", (model.getValueAt(model.getRowCount() - 1, 8)), 0.0, 0.0, (model.getValueAt(model.getRowCount() - 1, 11)), rndAccion, "Se retira",
                    0.0, (model.getValueAt(model.getRowCount() - 1, 16)), 0.0, 0.0, 0.0, cajero.getEstado(),
                    cajero.getCola(), empleado1.getEstado(), empleado1.getCola(), empleado2.getEstado(),
                    empleado2.getCola(),
                    //TODO: Recordar que evento (la variable) esta seteada arriba asiq no puede ser otra cosa mas que lo q se seteo arriba..
                    (evento == EVN_CONSUMICION || evento == EVN_UTILIZACION) ? tiempoPermanenciaAcumulador + (cl.getHoraPartida() - cl.getHoraLlegada()) : tiempoPermanenciaAcumulador,
                    (evento == EVN_CONSUMICION || evento == EVN_UTILIZACION) ? cantidadClientesEnCafeteria += 1 : cantidadClientesEnCafeteria});
            }
            System.out.println("tiempoentregapedido");
        } else if ((tiempoFinUsoMesa < tiempoFinConsumicion || evitarTiempoConsumicion) 
                && !evitarTiempoFinUsoMesa){
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
            evento, 
                reloj, 
                null, 
                null, 
                null, 
                model.getValueAt(model.getRowCount() -1,COL_TIEMPO_LLEGADA), 
                null, 
                null, 
                model.getValueAt(model.getRowCount() -1,COL_TIEMPO_ATENCION),
                null, 
                null, 
                model.getValueAt(model.getRowCount() -1,COL_TIEMPO_ENTREGA), 
                null, 
                null, 
                null, 
                null,
                null,
                null,
                null, 
                model.getValueAt(model.getRowCount() -1,COL_FIN_CONSUMICION),
                model.getValueAt(model.getRowCount() -1, COL_ESTADO_CAJERO),
                model.getValueAt(model.getRowCount() -1, COL_COLA_CAJERO),
                model.getValueAt(model.getRowCount() -1, COL_ESTADO_EMPL1), 
                model.getValueAt(model.getRowCount() -1, COL_COLA_EMPLEADOS),
                model.getValueAt(model.getRowCount() -1, COL_ESTADO_EMPL2), 
                calcularFinPermanencia(COL_TIEMPO_PERMAN_AC) , 
                model.getValueAt(model.getRowCount() -1, COL_CANT_CLIENTES_CONT)
            });
            //Faltan los clientes
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
                model.getValueAt(model.getRowCount() -1,COL_TIEMPO_LLEGADA), 
                null, 
                null, 
                model.getValueAt(model.getRowCount() -1,COL_TIEMPO_ATENCION),
                null,
                null, 
                model.getValueAt(model.getRowCount() -1,COL_TIEMPO_ENTREGA),
                null, 
                null, 
                null, 
                null,
                model.getValueAt(model.getRowCount() -1,COL_FIN_USO),
                null,
                null,
                model.getValueAt(model.getRowCount() -1, COL_FIN_CONSUMICION),
                model.getValueAt(model.getRowCount() -1, COL_ESTADO_CAJERO),
                model.getValueAt(model.getRowCount() -1, COL_COLA_CAJERO),
                model.getValueAt(model.getRowCount() -1, COL_ESTADO_EMPL1),
                model.getValueAt(model.getRowCount() -1, COL_COLA_EMPLEADOS),
                model.getValueAt(model.getRowCount() -1, COL_ESTADO_EMPL2),
                calcularFinPermanencia(COL_TIEMPO_PERMAN_AC) , 
                model.getValueAt(model.getRowCount() -1, COL_CANT_CLIENTES_CONT)
            });
            //TODO: agregar los clientes de alguna forma
        }

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
