/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulaciones.tp5;

import Objects.Cliente;
import Objects.Servidor;
import front.Tabla;
import front.Tabla_clientes;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.util.Collections;

/**
 *
 * @author gneil
 */
public class Grafico {

    DefaultTableModel model;
    DefaultTableModel model_cliente;
    Controller controller;
    Tabla_clientes t;
    Tabla tabla;
    ArrayList<Cliente> lista = new ArrayList<>();

    public Grafico(ArrayList lista, Controller controller) {
        tabla = new Tabla(controller);
        t = new Tabla_clientes(controller);
        this.controller = controller;
        model = (DefaultTableModel) tabla._tblSimulacion.getModel();
        model_cliente = (DefaultTableModel) t._tblSimulacion.getModel();
        this.lista = lista;
    }

    public void hacerVisible() {
        tabla.setVisible(true);
    }

    public void primeraVuelta(String eventoInicio, double reloj, float rnd1TiempoLlegada, float rnd2TiempoLlegada, double tiempoLlegada, double proxLlegada, Servidor cajero, Servidor empleado1, Servidor empleado2) {
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

    public void entraMesa(String eventoLlegadaMesa, double reloj, float rnd1TiempoLlegada, float rnd2TiempoLlegada, double tiempoLlegada, double proxLlegada, float rndAccion, String eventoUtilizacionMesa, double minTerminaAtencionCaja, double minEntregaPedido, float rndTiempoUtilizacionMesa, double tiempoUtilizacionMesa, double tiempoFinUtilizacionMesa, double minTerminaConsumicion, Servidor cajero, Servidor empleado1, Servidor empleado2, double tiempoAcumulado, int cantClientes) {

        model.addRow(new Object[]{
            eventoLlegadaMesa, //Evento (0)
            reloj, //Reloj (1)
            rnd1TiempoLlegada, //Llegada cliente - RND1 (2)
            rnd2TiempoLlegada, //Llegada cliente - RND2 (3)
            tiempoLlegada, //Tiempo llegada cliente (4)
            proxLlegada, //Próxima Llegada cliente (5)
            rndAccion,//Acción - RND (6)
            eventoUtilizacionMesa, //Accion : mesa o a comprar (7)
            (minTerminaAtencionCaja == 0) ? null : minTerminaAtencionCaja,//Tiempo fin atención caja (8)
            null,//Tiempo espera pedido - RND (9)
            null, //Tiempo espera pedido (10)
            (minEntregaPedido == 0) ? null : minEntregaPedido,//Tiempo entrega de pedido(11)
            null,//Accion mesa: - RND (12)
            null, //Accion mesa (13)
            rndTiempoUtilizacionMesa,//Tiempo uso de mesa - RND (14)
            tiempoUtilizacionMesa,//Tiempo uso de mesa (15)
            tiempoFinUtilizacionMesa,//Tiempo fin uso mesa (16)
            null,//Tiempo consumicion - RND (17)
            null,//Tiempo de consumicion (18)
            (minTerminaConsumicion == 0) ? null : minTerminaConsumicion,//Tiempo fin de consumicion (19)
            cajero.getEstado(),//Cajero - Estado (20)
            cajero.getCola(),//Cajero - Cola (21)
            empleado1.getEstado(),//Empleado 1 - Estado (22)
            empleado2.getEstado(),//Empleado 2 - Estado (23)
            empleado2.getCola(),//Empleado 2 - Cola(24)
            tiempoAcumulado, //Tiempo de permanencia acumulado(25)
            cantClientes}); //Cantidad clientes en cafeteria (26)
        cargaClientes();
    }

    public void entraComprar(String eventoLlegadaComprar, double reloj, float rnd1TiempoLlegada, float rnd2TiempoLlegada, double tiempoLlegada, double proxLlegada, float rndAccion, String eventoCompra, double minimoTiempoFinAtencionCaja, double minTerminaEntrega, double minTerminaUsarMesa, double minTerminaConsumicion, Servidor cajero, Servidor empleado1, Servidor empleado2, double tiempoAcumulado, int cantClientes) {
        model.addRow(new Object[]{
            eventoLlegadaComprar, //Evento (0)
            reloj, //Reloj (1)
            rnd1TiempoLlegada, //Llegada cliente - RND1 (2)
            rnd2TiempoLlegada, //Llegada cliente - RND2 (3)
            tiempoLlegada, //Tiempo llegada cliente (4)
            proxLlegada, //Próxima Llegada cliente (5)
            rndAccion, //Acción - RND (6)
            eventoCompra, //Accion : mesa o a comprar (7)
            (minimoTiempoFinAtencionCaja == 0) ? null : minimoTiempoFinAtencionCaja, //Tiempo fin atención caja (8)
            null, //Tiempo espera pedido - RND (9)
            null, //Tiempo espera pedido (10)
            (minTerminaEntrega == 0) ? null : minTerminaEntrega,//Tiempo entrega de pedido(11)
            null, //Accion mesa: - RND (12)
            null, //Accion mesa (13)
            null,//Tiempo uso de mesa - RND (14)
            null, //Tiempo uso de mesa (15)
            (minTerminaUsarMesa == 0) ? null : minTerminaUsarMesa, //Tiempo fin uso mesa (16)
            null, //Tiempo consumicion - RND (17)
            null, //Tiempo de consumicion (18)
            (minTerminaConsumicion == 0) ? null : minTerminaConsumicion, //Tiempo fin de consumicion (19)
            cajero.getEstado(), //Cajero - Estado (20)
            cajero.getCola(), //Cajero - Cola (21)
            empleado1.getEstado(), //Empleado 1 - Estado (22)
            empleado2.getEstado(),//Empleado 2 - Estado (23)
            empleado2.getCola(),//Empleado 2 - Cola (24)
            tiempoAcumulado, //Tiempo de permanencia acumulado(25)
            cantClientes}); //Cantidad clientes en cafeteria (26)
        cargaClientes();
    }

    public void finConsumicion(String eventoFinConsumicion, double reloj, double minProximaLLegada, double minTerminaAtencionCaja, double minTerminaEntrega, double minTerminaUsarMesa, double minTerminaConsumicion, Servidor cajero, Servidor empleado1, Servidor empleado2, double tiempoAcumulado, int cantClientes) {
        model.addRow(new Object[]{
            eventoFinConsumicion,//Evento (0)
            reloj, //Reloj (1)
            null,//Llegada cliente - RND1 (2)
            null,//Llegada cliente - RND2 (3)
            null,//Tiempo llegada cliente (4)
            (minProximaLLegada == 0) ? null : minProximaLLegada, //Próxima Llegada cliente (5)
            null, //Acción - RND (6)
            null, //Accion : mesa o a comprar (7)
            (minTerminaAtencionCaja == 0) ? null : minTerminaAtencionCaja,//Tiempo fin atención caja (8)
            null,//Tiempo espera pedido - RND (9)
            null,//Tiempo espera pedido (10)
            (minTerminaEntrega == 0) ? null : minTerminaEntrega,//Tiempo entrega de pedido (11)
            null,//Accion mesa: - RND (12)
            null, //Accion mesa - se retira (13)
            null,//Tiempo uso de mesa - RND (14)
            null, //Tiempo uso de mesa (15)
            (minTerminaUsarMesa == 0) ? null : minTerminaUsarMesa,//Tiempo fin uso mesa (16)
            null, //Tiempo consumicion - RND (17)
            null,//Tiempo de consumicion (18)
            (minTerminaConsumicion == 0) ? null : minTerminaConsumicion, //Tiempo fin de consumicion (19)
            cajero.getEstado(),//Cajero - Estado (20)
            cajero.getCola(), //Cajero - Cola (21)
            empleado1.getEstado(), //Empleado 1 - Estado (22)
            empleado2.getEstado(),//Empleado 2 - Estado (23)
            empleado2.getCola(),//Empleado 2 - Cola (24)
            tiempoAcumulado, //Tiempo de permanencia (25)
            cantClientes});//Cantidad clientes en cafeteria (26)
        cargaClientes();
    }

    public void atencionEmpleados(String eventoFinAtencion, double reloj, float rndEspera, double minProxLlegada, double minTerminaAtencionCaja, double tiempoEntrega, double finTiempoEntrega, double minTerminaUsarMesa, double minTerminaConsumicion, Servidor cajero, Servidor empleado1, Servidor empleado2, double tiempoAcumulado, int cantClientes) {

        model.addRow(new Object[]{
            eventoFinAtencion,//Evento (0)
            reloj, //Reloj (1)
            null, //Llegada cliente - RND1 (2)
            null, //Llegada cliente - RND2 (3)
            null, //Tiempo llegada cliente (4)
            (minProxLlegada == 0) ? null : minProxLlegada, //Próxima Llegada cliente (5)
            null, //Acción - RND (6)
            "",//Accion : mesa o a comprar (7)
            (minTerminaAtencionCaja == 0) ? null : minTerminaAtencionCaja, //Tiempo fin atención caja (8)
            rndEspera, //Tiempo espera pedido - RND (9)
            tiempoEntrega, //Tiempo espera pedido (10)
            finTiempoEntrega, //Tiempo entrega de pedido (11)
            null, //Accion mesa: - RND (12)
            null, //Accion mesa (13)
            null, //Tiempo uso de mesa - RND (14)
            null,//Tiempo uso de mesa (15)
            (minTerminaUsarMesa == 0) ? null : minTerminaUsarMesa, //Tiempo fin uso mesa (16)
            null, //Tiempo consumicion - RND (17)
            null, //Tiempo de consumicion (18)
            (minTerminaConsumicion == 0) ? null : minTerminaConsumicion,//Tiempo fin de consumicion (19)
            cajero.getEstado(), //Cajero - Estado (20)
            cajero.getCola(), //Cajero - Cola (21)
            empleado1.getEstado(), //Empleado 1 - Estado (22)
            empleado2.getEstado(), //Empleado 2 - Estado (23)
            empleado2.getCola(),//Empleado 2 - Cola (24)
            tiempoAcumulado, //Tiempo de permanencia acumulado(25)
            cantClientes});//Cantidad clientes en cafeteria (26)
        cargaClientes();
    }

    public void aColaEmpleados(String eventoFinAtencion, double reloj, double minProximaLLegada, double minTerminaAtencionCaja, double minTerminaEntrega, double minTerminaUsarMesa, double minTerminaConsumicion, Servidor cajero, Servidor empleado1, Servidor empleado2, double tiempoAcumulado, int cantClientes) {
        model.addRow(new Object[]{
            eventoFinAtencion,//Evento (0)
            reloj, //Reloj (1)
            null, //Llegada cliente - RND1 (2)
            null, //Llegada cliente - RND2 (3)
            null, //Tiempo llegada cliente (4)
            (minProximaLLegada == 0) ? null : minProximaLLegada, //Próxima Llegada cliente (5)
            null, //Acción - RND (6)
            "",//Accion : mesa o a comprar (7)
            null, //Tiempo fin atención caja (8)
            null, //Tiempo espera pedido - RND (9)
            null, //Tiempo espera pedido (10)
            (minTerminaEntrega == 0) ? null : minTerminaEntrega, //Tiempo entrega de pedido (11)
            null, //Accion mesa: - RND (12)
            null, //Accion mesa (13)
            null, //Tiempo uso de mesa - RND (14)
            null,//Tiempo uso de mesa (15)
            (minTerminaUsarMesa == 0) ? null : minTerminaUsarMesa, //Tiempo fin uso mesa (16)
            null, //Tiempo consumicion - RND (17)
            null, //Tiempo de consumicion (18)
            (minTerminaConsumicion == 0) ? null : minTerminaConsumicion,//Tiempo fin de consumicion (19)
            cajero.getEstado(), //Cajero - Estado (20)
            cajero.getCola(), //Cajero - Cola (21)
            empleado1.getEstado(), //Empleado 1 - Estado (22)
            empleado2.getEstado(), //Empleado 2 - Estado (23)
            empleado2.getCola(),//Empleado 2 - Cola (24)
            tiempoAcumulado, //Tiempo de permanencia acumulado(25)
            cantClientes});//Cantidad clientes en cafeteria (26)
        cargaClientes();
    }

    public void comproYSeRetira(String eventoFinAtencion, double reloj, double minProximaLLegada, double minTerminaAtencionCaja, double minTerminaEntrega, float rndAccion, double minTerminaUsarMesa, double minTerminaConsumicion, Servidor cajero, Servidor empleado1, Servidor empleado2, double tiempoAcumulado, int cantClientes) {
        model.addRow(new Object[]{
            eventoFinAtencion,//Evento (0)
            reloj, //Reloj (1)
            null,//Llegada cliente - RND1 (2)
            null,//Llegada cliente - RND2 (3)
            null,//Tiempo llegada cliente (4)
            (minProximaLLegada == 0) ? null : minProximaLLegada, //Próxima Llegada cliente (5)
            null, //Acción - RND (6)
            null, //Accion : mesa o a comprar (7)
            (minTerminaAtencionCaja == 0) ? null : minTerminaAtencionCaja,//Tiempo fin atención caja (8)
            null,//Tiempo espera pedido - RND (9)
            null,//Tiempo espera pedido (10)
            (minTerminaEntrega == 0) ? null : minTerminaEntrega,//Tiempo entrega de pedido (11)
            rndAccion, //Accion mesa: - RND (12)
            "Se retira",//Accion mesa (13)
            null, //Tiempo uso de mesa - RND(14)
            null,//Tiempo uso mesa (15)
            (minTerminaUsarMesa == 0) ? null : minTerminaUsarMesa,//Tiempo fin uso mesa (16)
            null, //Tiempo consumicion - RND (17)
            null,//Tiempo de consumicion (18)
            (minTerminaConsumicion == 0) ? null : minTerminaConsumicion, //Tiempo fin de consumicion (19)
            cajero.getEstado(),//Cajero - Estado (20)
            cajero.getCola(), //Cajero - Cola (21)
            empleado1.getEstado(), //Empleado 1 - Estado (22)
            empleado2.getEstado(),//Empleado 2 - Estado (23)
            empleado2.getCola(),//Empleado 2 - Cola (24)
            tiempoAcumulado, //Tiempo de permanencia acumulado(25)
            cantClientes});//Cantidad clientes en cafeteria (26)
        cargaClientes();
    }

    public void noComioYUsoMesa(String eventoFinMesa, double reloj, double minProximaLLegada, double minTerminaAtencionCaja, double minTerminaEntrega, double minTerminaConsumicion, Servidor cajero, Servidor empleado1, Servidor empleado2, double tiempoAcumulado, int cantClientes) {
        model.addRow(new Object[]{
            eventoFinMesa,//Evento (0)
            reloj, //Reloj (1)
            null,//Llegada cliente - RND1 (2)
            null,//Llegada cliente - RND2 (3)
            null,//Tiempo llegada cliente (4)
            (minProximaLLegada == 0) ? null : minProximaLLegada, //Próxima Llegada cliente (5)
            null, //Acción - RND (6)
            null, //Accion : mesa o a comprar (7)
            (minTerminaAtencionCaja == 0) ? null : minTerminaAtencionCaja,//Tiempo fin atención caja (8)
            null,//Tiempo espera pedido - RND (9)
            null,//Tiempo espera pedido (10)
            (minTerminaEntrega == 0) ? null : minTerminaEntrega,//Tiempo entrega de pedido (11)
            null,//Accion mesa: - RND (12)
            null, //Accion mesa - se retira (13)
            null,//Tiempo uso de mesa - RND (14)
            null, //Tiempo uso de mesa (15)
            null,//Tiempo fin uso mesa (16)
            null, //Tiempo consumicion - RND (17)
            null,//Tiempo de consumicion (18)
            (minTerminaConsumicion == 0) ? null : minTerminaConsumicion, //Tiempo fin de consumicion (19)
            cajero.getEstado(),//Cajero - Estado (20)
            cajero.getCola(), //Cajero - Cola (21)
            empleado1.getEstado(), //Empleado 1 - Estado (22)
            empleado2.getEstado(),//Empleado 2 - Estado (23)
            empleado2.getCola(),//Empleado 2 - Cola (24)
            tiempoAcumulado, //Tiempo de permanencia (25)
            cantClientes});//Cantidad clientes en cafeteria (26)
        cargaClientes();
    }

    public void comproYSeSienta(String eventoEntregaPedido, double reloj, double minProximaLLegada, double minTerminaAtencionCaja, double minTerminaEntrega, float rndAccion, String consumiendo, float rndTiempoConsumicion, double tiempoConsumicion, double finConsumicion, Servidor cajero, Servidor empleado1, Servidor empleado2, double tiempoAcumulado, int cantClientes) {
        model.addRow(new Object[]{
            eventoEntregaPedido,//Evento (0)
            reloj, //Reloj (1)
            null,//Llegada cliente - RND1 (2)
            null,//Llegada cliente - RND2 (3)
            null,//Tiempo llegada cliente (4)
            (minProximaLLegada == 0) ? null : minProximaLLegada, //Próxima Llegada cliente (5)
            null, //Acción - RND (6)
            null, //Accion : mesa o a comprar (7)
            (minTerminaAtencionCaja == 0) ? null : minTerminaAtencionCaja,//Tiempo fin atención caja (8)
            null,//Tiempo espera pedido - RND (9)
            null,//Tiempo espera pedido (10)
            (minTerminaEntrega == 0) ? null : minTerminaEntrega,//Tiempo entrega de pedido (11)
            rndAccion,//Accion mesa: - RND (12)
            consumiendo,//Accion mesa (13)
            null, //Tiempo uso de mesa - RND (14)
            null, //Tiempo uso mesa (15)
            null, //Tiempo fin uso mesa (16)
            rndTiempoConsumicion, //Tiempo consumicion - RND (17)
            tiempoConsumicion,//Tiempo de consumicion (18)
            finConsumicion, //Tiempo fin de consumicion (19)
            cajero.getEstado(),//Cajero - Estado (20)
            cajero.getCola(), //Cajero - Cola (21)
            empleado1.getEstado(), //Empleado 1 - Estado (22)
            empleado2.getEstado(),//Empleado 2 - Estado (23)
            empleado2.getCola(),//Empleado 2 - Cola (24)
            tiempoAcumulado, //Tiempo de permanencia acumulado(25)
            cantClientes});//Cantidad clientes en cafeteria (26)
        cargaClientes();
    }

    public void tiempoPermanencia(double resultado) {
        tabla.setResultado(resultado);
    }

    public void mostrarClientes() {
        t.setVisible(true);
    }

    String arreglo[];

    public void cargaClientes() {
        int cantidadClientes = controller.mostrarCantidadClientes();
        arreglo = new String[3 * cantidadClientes];

        int contador = 0;
        for (int i = 0; i < arreglo.length; i++) {
            int suma = i;
            arreglo[suma] = "Estado (" + contador + ")";
            suma++;
            arreglo[suma] = "Hora Llegada (" + contador + ")";
            suma++;
            if (suma > 3 * cantidadClientes) {
                return;
            }
            arreglo[suma] = "Hora Partida (" + contador + ")";
            i = suma;
            contador++;
        }

        for (int i = 0; i < lista.size(); i++) {
            int posicion = lista.get(i).getPosicion();
            arreglo[(posicion * 3)] = "Estado (" + posicion + ")";
            arreglo[(posicion * 3) + 1] = "Hora Llegada (" + posicion + ")";
            arreglo[(posicion * 3) + 2] = "Hora Partida (" + posicion + ")";
        }
        model_cliente.setColumnIdentifiers(arreglo);

        Object valoresClientes[] = new Object[3 * cantidadClientes];
        for (int i = 0; i < lista.size(); i++) {
            int posicion = lista.get(i).getPosicion();
            valoresClientes[(posicion * 3)] = lista.get(i).getEstado();
            valoresClientes[(posicion * 3) + 1] = lista.get(i).getHoraLlegada();
            valoresClientes[(posicion * 3) + 2] = (lista.get(i).getHoraPartida() == -1) ? null : lista.get(i).getHoraPartida();
        }
        model_cliente.addRow(valoresClientes);
    }
}
