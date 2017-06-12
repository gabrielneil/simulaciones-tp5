/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package front;

import java.text.SimpleDateFormat;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import simulaciones.tp5.*;

/**
 *
 * @author gabrielneil
 */
public class Tabla extends javax.swing.JFrame {

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public Tabla() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        simulationTable = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        _scpTabla = new javax.swing.JScrollPane();
        _tblSimulacion = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Vector Estado");

        _tblSimulacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Evento(0)", "Reloj(1)", "RND1(2)", "RND2(3)", "Llegada entre cliente(4)", "Próxima llegada(5)", "RND(6)", "Acción(7)", "Fin atención en caja(8)", "RND(9)", "Tiempo espera pedido(10)", "Tiempo entrega pedido(11)", "RND(12)", "Acción - Mesa(13)", "RND(14)", "Tiempo de uso de mesa(15)", "RND(16)", "Tiempo de consumición(17)", "Tiempo fin de consumición(18)", "Estado(19)", "Cola(20)", "Estado(21)", "Cola(22)", "Estado(23)", "Cola(24)", "Tiempo permanencia(25)", "Cantidad clientes en cafetería(26)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        _tblSimulacion.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        _tblSimulacion.getTableHeader().setReorderingAllowed(false);
        _scpTabla.setViewportView(_tblSimulacion);
        if (_tblSimulacion.getColumnModel().getColumnCount() > 0) {
            _tblSimulacion.getColumnModel().getColumn(0).setPreferredWidth(70);
            _tblSimulacion.getColumnModel().getColumn(1).setPreferredWidth(50);
            _tblSimulacion.getColumnModel().getColumn(2).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(2).setPreferredWidth(50);
            _tblSimulacion.getColumnModel().getColumn(3).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(3).setPreferredWidth(50);
            _tblSimulacion.getColumnModel().getColumn(4).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(4).setPreferredWidth(200);
            _tblSimulacion.getColumnModel().getColumn(5).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(5).setPreferredWidth(200);
            _tblSimulacion.getColumnModel().getColumn(6).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(6).setPreferredWidth(50);
            _tblSimulacion.getColumnModel().getColumn(7).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(7).setPreferredWidth(200);
            _tblSimulacion.getColumnModel().getColumn(8).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(8).setPreferredWidth(200);
            _tblSimulacion.getColumnModel().getColumn(9).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(9).setPreferredWidth(50);
            _tblSimulacion.getColumnModel().getColumn(10).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(10).setPreferredWidth(180);
            _tblSimulacion.getColumnModel().getColumn(11).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(11).setPreferredWidth(180);
            _tblSimulacion.getColumnModel().getColumn(12).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(12).setPreferredWidth(50);
            _tblSimulacion.getColumnModel().getColumn(13).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(13).setPreferredWidth(200);
            _tblSimulacion.getColumnModel().getColumn(14).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(14).setPreferredWidth(50);
            _tblSimulacion.getColumnModel().getColumn(15).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(15).setPreferredWidth(200);
            _tblSimulacion.getColumnModel().getColumn(16).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(16).setPreferredWidth(50);
            _tblSimulacion.getColumnModel().getColumn(17).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(17).setPreferredWidth(200);
            _tblSimulacion.getColumnModel().getColumn(18).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(18).setPreferredWidth(200);
            _tblSimulacion.getColumnModel().getColumn(19).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(19).setPreferredWidth(100);
            _tblSimulacion.getColumnModel().getColumn(20).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(20).setPreferredWidth(100);
            _tblSimulacion.getColumnModel().getColumn(21).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(21).setPreferredWidth(100);
            _tblSimulacion.getColumnModel().getColumn(22).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(22).setPreferredWidth(100);
            _tblSimulacion.getColumnModel().getColumn(23).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(23).setPreferredWidth(100);
            _tblSimulacion.getColumnModel().getColumn(24).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(24).setPreferredWidth(100);
            _tblSimulacion.getColumnModel().getColumn(25).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(25).setPreferredWidth(200);
            _tblSimulacion.getColumnModel().getColumn(26).setResizable(false);
            _tblSimulacion.getColumnModel().getColumn(26).setPreferredWidth(200);
        }
        _tblSimulacion.getAccessibleContext().setAccessibleName("");
        _tblSimulacion.getAccessibleContext().setAccessibleDescription("");

        jButton1.setText("Volver");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(_scpTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 2665, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(_scpTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1))
        );

        simulationTable.addTab("Simulacion", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(simulationTable))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(simulationTable, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JScrollPane _scpTabla;
    public javax.swing.JTable _tblSimulacion;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTabbedPane simulationTable;
    // End of variables declaration//GEN-END:variables

}
