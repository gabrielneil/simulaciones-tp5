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

/**
 *
 * @author gabrielneil
 */
public class Calculator {

    ArrayList<Cliente> lista = new ArrayList<>();
    Buscar buscar;
    Controller controller;
    Tabla tabla;
    Servidor empleado1 = new Servidor();
    Servidor empleado2 = new Servidor();
    Servidor cajero = new Servidor();
    Cliente c1;
    int desde;
    int hasta;
    int cantidadClientesEnCafeteria = 0;
    Random r = new Random();

    int media = 12;
    int desviacion = 2;
    int entranAComprar = 60;
    int entranAMesa = 40;
    int sientaEnMesa = 50;
    int seRetira = 50;
    double reloj = 0;
    double tiempoPermanenciaAcumulador = 0;
    double tiempoEntrega = 0;
    double finTiempoEntrega = 0;

    float tiempoTicket = 20 / 60;
    float tiempoEspera = 50 / 60;
    int tiempoConsumicion1 = 5;
    int tiempoConsumicion2 = 1;
    int tiempoUtilizacionMesa1 = 15;
    int tiempoUtilizacionMesa2 = 5;
    String evento;

    float rnd1TiempoLlegada;
    float rnd2TiempoLlegada;

    Grafico grafico;
    double cantMinutos = 0;
    double minProximaLlegada = 0;
    double minTerminaAtencionCaja = 0;
    double minTerminaEntrega = 0;
    double minTerminaUsarMesa = 0;
    double minTerminaConsumicion = 0;
    int numeroVuelta = 0;
    int cantClientes = 0;
    double tiempoAcumulado = 0;
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
    public static final String EVN_UTILIZANDO_MESA = "Utilizando mesa";
    public static final String EVN_ATENCION_CAJA = "Esperando atención en caja";
    public static final String EVN_ATENDIDO_CAJA = "Atendido en caja";
    public static final String EVN_FIN_ATENCION = "Fin atención caja";
    public static final String EVN_ATENCION_PEDIDO = "Esperando atención empleado";
    public static final String EVN_ATENDIDO_EMPLEADO = "Atendido empleado";
    public static final String EVN_FIN_ATENCION_EMPLEADO = "Fin atención empleado";
    public static final String EVN_UTILIZACION = "Fin Utilizacion de mesa";
    public static final String EVN_CONSUMIENDO = "Consumiendo";
    public static final String EVN_CONSUMICION = "Fin Consumicion de pedido";
    public static final String EVN_COMPRA = "Compra";

    public Calculator(Controller controller) {
        this.controller = controller;
        this.tabla = new Tabla(controller);
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

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public void setReloj(double tiempo) {
        this.reloj = tiempo;
    }

    public void initSimulacion() {
        grafico = new Grafico(lista, controller);
        buscar = new Buscar(lista);
        grafico.hacerVisible();
        setEvento(NO_EVN);
        double tiempoDeCorte = 60;
        while (reloj <= tiempoDeCorte) {
            simularAvance();
        }
    }

    public void simularAvance() {
        cantMinutos = reloj;

        if (cantMinutos == 0) {
            rnd1TiempoLlegada = r.nextFloat();
            rnd2TiempoLlegada = r.nextFloat();
            System.out.println("entro");
            double tiempoLlegada = Formulas.llegadaCliente(rnd1TiempoLlegada, rnd2TiempoLlegada, media, desviacion);
            minProximaLlegada = reloj + tiempoLlegada;
            if (cantMinutos >= desde) {
                grafico.primeraVuelta(EVN_INICIO, reloj, rnd1TiempoLlegada, rnd2TiempoLlegada, tiempoLlegada, minProximaLlegada, cajero, empleado1, empleado2);
            }
            reloj = minProximaLlegada;
            return;
        }

        boolean evitarTiempoLlegada = (minProximaLlegada == 0) ? true : false;
        boolean evitarTiempoFinAtencionCaja = (minTerminaAtencionCaja == 0) ? true : false;
        boolean evitarTiempoEntregaPedido = (minTerminaEntrega == 0) ? true : false;
        boolean evitarTiempoFinUsoMesa = (minTerminaUsarMesa == 0) ? true : false;
        boolean evitarTiempoConsumicion = (minTerminaConsumicion == 0) ? true : false;

        if (!evitarTiempoLlegada && (minProximaLlegada < minTerminaAtencionCaja || evitarTiempoFinAtencionCaja) && (minProximaLlegada < minTerminaEntrega || evitarTiempoEntregaPedido) && (minProximaLlegada < minTerminaUsarMesa || evitarTiempoFinUsoMesa) && (minProximaLlegada < minTerminaConsumicion || evitarTiempoConsumicion)) {
            //llega cliente
            llegadaCliente();

        } else if (!evitarTiempoFinAtencionCaja && (minTerminaAtencionCaja < minTerminaEntrega || evitarTiempoEntregaPedido) && (minTerminaAtencionCaja < minTerminaUsarMesa || evitarTiempoFinUsoMesa) && (minTerminaAtencionCaja < minTerminaConsumicion || evitarTiempoConsumicion)) {
            // tiempoFinAtencionCaja es el proximo evento - SE MANDA A LA COLA DE LOS DOS CHABONES
            System.out.println("Fin atención caja");
            finAtencionCaja();

        } else if (!evitarTiempoEntregaPedido && (minTerminaEntrega < minTerminaUsarMesa || evitarTiempoFinUsoMesa) && (minTerminaEntrega < minTerminaConsumicion || evitarTiempoConsumicion)) {
            calcularFinAtencionEmpleado();

        } else if ((minTerminaUsarMesa < minTerminaConsumicion || evitarTiempoConsumicion) && !evitarTiempoFinUsoMesa) {
            System.out.println("NO COMIO, SOLO USO LA MESA Y SE VA");
            noComioYUsoMesa();
        } else {
            System.out.println("TERMINO DE CONSUMIR Y SE LAS TOMA");
            finConsumicion();
        }
    }
    Cliente nuevoCliente;

    private void llegadaCliente() {
        setReloj(minProximaLlegada);
        rnd1TiempoLlegada = r.nextFloat();
        rnd2TiempoLlegada = r.nextFloat();
        double proxLlegada = Formulas.llegadaCliente(rnd1TiempoLlegada, rnd1TiempoLlegada, media, desviacion);
        minProximaLlegada = reloj + proxLlegada;
        float rndAccion = r.nextFloat();

        if (rndAccion <= ((float) entranAMesa / 100)) {

            System.out.println("entra a la mesa");
            float rndTiempoUtilizacionMesa = r.nextFloat();
            double tiempoUtilizacionMesa = Formulas.tiempoUtilizacionMesa(tiempoUtilizacionMesa1, tiempoUtilizacionMesa2, rndTiempoUtilizacionMesa);
            double tiempoFinUtilizacionMesa = reloj + tiempoUtilizacionMesa;
            nuevoCliente = new Cliente(EVN_UTILIZANDO_MESA, reloj, tiempoFinUtilizacionMesa);
            lista.add(nuevoCliente);

            if (reloj >= desde && reloj <= hasta) {
                grafico.entraMesa(EVN_LLEGADA, reloj, rnd1TiempoLlegada, rnd2TiempoLlegada, proxLlegada, minProximaLlegada, rndAccion, EVN_UTILIZANDO_MESA, minTerminaAtencionCaja, minTerminaEntrega, rndTiempoUtilizacionMesa, tiempoUtilizacionMesa, tiempoFinUtilizacionMesa, minTerminaConsumicion, cajero, empleado1, empleado2, tiempoAcumulado, cantClientes);
            }

            //si sos el menor, seteate
            minTerminaUsarMesa = lista.get(buscar.quienCortaAntes(EVN_UTILIZANDO_MESA)).getHoraPartida();
        } else {
            System.out.println("entra a comprar");
            nuevoCliente = new Cliente(EVN_ATENCION_CAJA, reloj);

            if (cajero.getEstado().equals("LIBRE")) {
                calcularFinAtencionCajero(nuevoCliente);
            } else {
                cajero.aumentarCola();
                nuevoCliente.setEstado(EVN_ATENCION_CAJA);
            }

            lista.add(nuevoCliente);
            if (reloj >= desde && reloj <= hasta) {
                grafico.entraComprar(EVN_LLEGADA, reloj, rnd1TiempoLlegada, rnd2TiempoLlegada, proxLlegada, minProximaLlegada, rndAccion, EVN_COMPRA, minTerminaAtencionCaja, minTerminaEntrega, minTerminaUsarMesa, minTerminaConsumicion, cajero, empleado1, empleado2, tiempoAcumulado, cantClientes);
            }
        }
    }

    private void finConsumicion() {
        setEvento(EVN_CONSUMICION);
        setReloj(minTerminaConsumicion);
        int posicion = buscar.buscarPosicion(minTerminaConsumicion);
        Cliente cliente = lista.get(posicion);
        cantClientes++;
        tiempoAcumulado += (cliente.getHoraPartida() - cliente.getHoraLlegada());
        lista.remove(posicion);

        int elMasViejo = buscar.quienCortaAntes(EVN_CONSUMIENDO);
        if (elMasViejo != -1) {
            minTerminaConsumicion = lista.get(elMasViejo).getHoraPartida();
        } else {
            minTerminaConsumicion = 0;
        }

        if (reloj >= desde && reloj <= hasta) {
            grafico.finConsumicion(EVN_CONSUMICION, reloj, minProximaLlegada, minTerminaAtencionCaja, minTerminaEntrega, minTerminaUsarMesa, minTerminaConsumicion, cajero, empleado1, empleado2, tiempoAcumulado, cantClientes);
        }

    }

    private void finAtencionCaja() {
        Cliente cliente;
        setEvento(EVN_FIN_ATENCION);
        setReloj(minTerminaAtencionCaja);
        buscar.actualizarCajero(cajero);

        float rndEspera = r.nextFloat();
        tiempoEntrega = 0;
        finTiempoEntrega = 0;

        //busco quien es el minimo que voy a tratar ahora
        cliente = buscar.buscarCliente(EVN_ATENDIDO_CAJA, minTerminaAtencionCaja);

        if (empleado1.getEstado().equals("LIBRE") || empleado2.getEstado().equals("LIBRE")) {

            siguienteAtenderEmpleado(cliente, rndEspera);

            //busco quien va a reemplazar al que se acaba de ir de la caja y se mando a los empleados
            int elMasViejo = buscar.quienReemplaza(EVN_ATENCION_CAJA);
            if (elMasViejo != -1) {
                double tiempoFinAtencionCaja = reloj + tiempoTicket;
                cliente = lista.get(elMasViejo);
                cliente.setHoraPartida(tiempoFinAtencionCaja);
                cliente.quienMeAtiende("CAJERO");
                cliente.setEstado(EVN_ATENDIDO_CAJA);
                cajero.setOcupado();
                minTerminaAtencionCaja = cliente.getHoraPartida();
            } else {
                minTerminaAtencionCaja = 0;
            }
            if (reloj >= desde && reloj <= hasta) {
                grafico.atencionEmpleados(EVN_FIN_ATENCION, reloj, rndEspera, minProximaLlegada, minTerminaAtencionCaja, tiempoEntrega, finTiempoEntrega, minTerminaUsarMesa, minTerminaConsumicion, cajero, empleado1, empleado2, tiempoAcumulado, cantClientes);
            }

        } else {
            empleado1.aumentarCola();
            empleado2.aumentarCola();
            cliente.setEstado(EVN_ATENCION_PEDIDO);

            //busco quien va a reemplazar al que se acaba de ir de la caja y se mando a los empleados
            int elMasViejo = buscar.quienReemplaza(EVN_ATENCION_CAJA);
            if (elMasViejo != -1) {
                double tiempoFinAtencionCaja = reloj + tiempoTicket;
                cliente = lista.get(elMasViejo);
                cliente.setHoraPartida(tiempoFinAtencionCaja);
                cliente.quienMeAtiende("CAJERO");
                cliente.setEstado(EVN_ATENDIDO_CAJA);
                cajero.setOcupado();
                minTerminaAtencionCaja = cliente.getHoraPartida();
            } else {
                minTerminaAtencionCaja = 0;
            }
            if (reloj >= desde && reloj <= hasta) {
                grafico.aColaEmpleados(EVN_FIN_ATENCION, reloj, minProximaLlegada, minTerminaAtencionCaja, minTerminaEntrega, minTerminaUsarMesa, minTerminaConsumicion, cajero, empleado1, empleado2, tiempoAcumulado, cantClientes);
            }
        }
    }

    private void comproYSeRetira(float rndAccion, Cliente clienteParaDesdeYHasta) {

        tiempoAcumulado += (clienteParaDesdeYHasta.getHoraPartida() - clienteParaDesdeYHasta.getHoraLlegada());
        cantClientes++;
        lista.remove(clienteParaDesdeYHasta);
        int elMasViejo = buscar.quienReemplaza(EVN_ATENCION_PEDIDO);
        if (elMasViejo != -1) {
            siguienteParaEmpleado = lista.get(elMasViejo);
            float rndEspera = r.nextFloat();
            siguienteAtenderEmpleado(siguienteParaEmpleado, rndEspera);
        } else {
            //no hay nadie esperando, pero capaz tengo otro atendido por el otro empleado
            int posicion = buscar.quienCortaAntes(EVN_ATENDIDO_EMPLEADO);
            //quiere decir que hay alguien en paralelo
            if (posicion != -1) {
                Cliente buscarParalelo = lista.get(posicion);
                minTerminaEntrega = buscarParalelo.getHoraPartida();

            } else {
                minTerminaEntrega = 0;
            }
        }
        if (reloj >= desde && reloj <= hasta) {
            grafico.comproYSeRetira(EVN_FIN_ATENCION_EMPLEADO, reloj, minProximaLlegada, minTerminaAtencionCaja, minTerminaEntrega, rndAccion, minTerminaUsarMesa, minTerminaConsumicion, cajero, empleado1, empleado2, tiempoAcumulado, cantClientes);
        }
    }

    private void comproYSeSienta(float rndAccion, Cliente cliente) {
        float rndTiempoConsumicion = r.nextFloat();
        double tiempoConsumicion = Formulas.tiempoConsumicion(rndTiempoConsumicion);
        double finConsumicion = tiempoConsumicion + reloj;
        cliente.setEstado(EVN_CONSUMIENDO);
        cliente.setHoraPartida(finConsumicion);

        int posicion = buscar.quienCortaAntes(EVN_ATENDIDO_EMPLEADO);
        //quiere decir que hay alguien en paralelo
        if (posicion != -1) {
            Cliente buscarParalelo = lista.get(posicion);
            minTerminaEntrega = buscarParalelo.getHoraPartida();
        } else {
            minTerminaEntrega = 0;
        }

        if (reloj >= desde && reloj <= hasta) {
            grafico.comproYSeSienta(EVN_FIN_ATENCION_EMPLEADO, reloj, minProximaLlegada, minTerminaAtencionCaja, minTerminaEntrega, rndAccion, EVN_CONSUMIENDO, rndTiempoConsumicion, tiempoConsumicion, finConsumicion, cajero, empleado1, empleado2, tiempoAcumulado, cantClientes);
        }
        minTerminaConsumicion = lista.get(buscar.quienCortaAntes(EVN_CONSUMIENDO)).getHoraPartida();
    }

    public void noComioYUsoMesa() {
        setEvento(EVN_UTILIZACION);
        setReloj(minTerminaUsarMesa);
        Cliente cliente = buscar.buscarCliente(EVN_UTILIZANDO_MESA, minTerminaUsarMesa);
        tiempoAcumulado += (cliente.getHoraPartida() - cliente.getHoraLlegada());
        cantClientes++;

        if (reloj >= desde && reloj <= hasta) {
            grafico.noComioYUsoMesa(EVN_UTILIZACION, reloj, minProximaLlegada, minTerminaAtencionCaja, minTerminaEntrega, minTerminaConsumicion, cajero, empleado1, empleado2, tiempoAcumulado, cantClientes);
        }
        lista.remove(cliente);

        int elMasViejo = buscar.quienCortaAntes(EVN_UTILIZANDO_MESA);
        if (elMasViejo != -1) {
            minTerminaUsarMesa = lista.get(elMasViejo).getHoraPartida();
        } else {
            minTerminaUsarMesa = 0;
        }
    }

    public void calcularFinAtencionCajero(Cliente cliente) {
        double tiempoFinAtencionCaja = reloj + tiempoTicket;
        cliente.setHoraPartida(tiempoFinAtencionCaja);
        cajero.setOcupado();
        cliente.quienMeAtiende("CAJERO");
        cliente.setEstado(EVN_ATENDIDO_CAJA);
        minTerminaAtencionCaja = cliente.getHoraPartida();
    }

    Cliente siguienteParaEmpleado;
    Cliente clienteParaDesdeYHasta;

    public void calcularFinAtencionEmpleado() {
        System.out.println("fin atención del empleado osea que le terminó el pedido. es el proximo evento ");
        Cliente cliente = buscar.buscarCliente(EVN_ATENDIDO_EMPLEADO, minTerminaEntrega);
        setEvento(EVN_FIN_ATENCION_EMPLEADO);
        setReloj(minTerminaEntrega);

        cliente.setEstado(EVN_FIN_ATENCION_EMPLEADO);

        //ver los empleados
        System.out.println("El valor del cliente antes de que se rompa" + cliente.getQuienMeAtiende());
        buscar.actualizarEmpleados(cliente, empleado1, empleado2);

        float rndAccion = r.nextFloat();
        if (rndAccion <= ((float) sientaEnMesa / 100)) {

            int elMasViejo = buscar.quienReemplaza(EVN_ATENCION_PEDIDO);
            if (elMasViejo != -1) {
                siguienteParaEmpleado = lista.get(elMasViejo);
                float rndEspera = r.nextFloat();
                siguienteAtenderEmpleado(siguienteParaEmpleado, rndEspera);

            } else {

                //no hay nadie esperando, pero capaz tengo otro atendido por el otro empleado
                int posicion = buscar.quienCortaAntes(EVN_ATENDIDO_EMPLEADO);
                //quiere decir que hay alguien en paralelo
                if (posicion != -1) {
                    Cliente buscarParalelo = lista.get(posicion);
                    minTerminaEntrega = buscarParalelo.getHoraPartida();

                } else {
                    minTerminaEntrega = 0;
                }
            }
            clienteParaDesdeYHasta = cliente;
            System.out.println("COMPRO Y SE SIENTA EN LA MESA");
            comproYSeSienta(rndAccion, clienteParaDesdeYHasta);

        } else {
            System.out.println("SOLO COMPRO Y SE TOMA EL PALO");
            clienteParaDesdeYHasta = cliente;
            comproYSeRetira(rndAccion, clienteParaDesdeYHasta);
        }
    }

    public void siguienteAtenderEmpleado(Cliente cliente, float rndEspera) {
        tiempoEntrega = Formulas.tiempoEntregaPedido(tiempoEspera, rndEspera);
        finTiempoEntrega = tiempoEntrega + reloj;
        System.out.println("los datos del cliente pero en el otro metodo son son" + cliente);
        cliente.setHoraPartida(finTiempoEntrega);

        if (empleado1.getEstado().equals("LIBRE")) {
            System.out.println("Me atiende el empleado 1");
            empleado1.setOcupado();
            cliente.quienMeAtiende("EMPLEADO1");

        } else {
            System.out.println("Me atiende el empleado 2");
            empleado2.setOcupado();
            cliente.quienMeAtiende("EMPLEADO2");
        }

        cliente.setEstado(EVN_ATENDIDO_EMPLEADO);
        Cliente menor = lista.get(buscar.quienCortaAntes(EVN_ATENDIDO_EMPLEADO));
        minTerminaEntrega = menor.getHoraPartida();
    }

    public void mostrarClientes() {
        grafico.mostrarClientes();
    }
}
