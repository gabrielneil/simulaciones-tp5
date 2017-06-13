/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package front;

import javax.swing.JTextField;
import simulaciones.tp5.Controller;

/**
 *
 * @author gabrielneil
 */
public class CargaTiempos extends javax.swing.JFrame {

    Controller controller = Controller.getInstance();

    /**
     * Creates new form CargaTiempos
     */
    public CargaTiempos() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tiempoTicket_txt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tiempoEspera_txt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tiempoConsumicion1_txt = new javax.swing.JTextField();
        tiempoConsumicion2_txt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tiempoUtilizacionMesa1_txt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tiempoUtilizacionMesa2_txt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Tiempo de espera de pedido:");

        jLabel2.setText("Tiempo ticket de compra: ");

        jLabel3.setText("Tiempo de consumición de pedido:");

        tiempoTicket_txt.setText("20");

        jLabel5.setText("segundos");

        tiempoEspera_txt.setText("50");

        jLabel6.setText("segundos");

        tiempoConsumicion1_txt.setText("5");

        tiempoConsumicion2_txt.setText("1");

        jLabel7.setText("±");

        tiempoUtilizacionMesa1_txt.setText("15");

        jLabel8.setText("Tiempo de utilización de mesas:");

        tiempoUtilizacionMesa2_txt.setText("5");
        tiempoUtilizacionMesa2_txt.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                tiempoUtilizacionMesa2_txtActionPerformed(evt);
            }
        });

        jLabel9.setText("±");

        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setText("minutos");

        jLabel10.setText("minutos");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(33, 33, 33)
                        .addComponent(tiempoEspera_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(47, 47, 47)
                        .addComponent(tiempoTicket_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tiempoUtilizacionMesa1_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tiempoUtilizacionMesa2_txt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tiempoConsumicion1_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)
                                .addGap(1, 1, 1)
                                .addComponent(tiempoConsumicion2_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4)))))
                .addContainerGap(70, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(45, 45, 45)
                .addComponent(jButton1)
                .addGap(131, 131, 131))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tiempoTicket_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tiempoEspera_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tiempoConsumicion1_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tiempoConsumicion2_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tiempoUtilizacionMesa1_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tiempoUtilizacionMesa2_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tiempoUtilizacionMesa2_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tiempoUtilizacionMesa2_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tiempoUtilizacionMesa2_txtActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        if (Integer.parseInt(tiempoTicket_txt.getText()) > 0 && Integer.parseInt(tiempoEspera_txt.getText()) > 0 && Integer.parseInt(tiempoConsumicion1_txt.getText()) > 0 && Integer.parseInt(tiempoConsumicion2_txt.getText()) > 0 && Integer.parseInt(tiempoUtilizacionMesa1_txt.getText()) > 0 && Integer.parseInt(tiempoUtilizacionMesa2_txt.getText()) > 0) {
            controller.valoresCargaTiempos(Integer.parseInt(tiempoTicket_txt.getText()), Integer.parseInt(tiempoEspera_txt.getText()), Integer.parseInt(tiempoConsumicion1_txt.getText()), Integer.parseInt(tiempoConsumicion2_txt.getText()), Integer.parseInt(tiempoUtilizacionMesa1_txt.getText()), Integer.parseInt(tiempoUtilizacionMesa2_txt.getText()));
            controller.getCargaDatos().setVisible(true);
        } else {
            System.out.println("Alguno de los valores es inválido.");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CargaTiempos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CargaTiempos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CargaTiempos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CargaTiempos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CargaTiempos().setVisible(true);
            }
        });
    }

    public Integer getTiempoConsumicion1_txt() {
        return Integer.parseInt(tiempoConsumicion1_txt.getText());
    }

    public Integer getTiempoConsumicion2_txt() {
        return Integer.parseInt(tiempoConsumicion2_txt.getText());
    }

    public Integer getTiempoEspera_txt() {
        return Integer.parseInt(tiempoEspera_txt.getText());
    }

    public Integer getTiempoTicket_txt() {
        return Integer.parseInt(tiempoTicket_txt.getText());
    }

    public Integer getTiempoUtilizacionMesa1_txt() {
        return Integer.parseInt(tiempoUtilizacionMesa1_txt.getText());
    }

    public Integer getTiempoUtilizacionMesa2_txt() {
        return Integer.parseInt(tiempoUtilizacionMesa2_txt.getText());
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField tiempoConsumicion1_txt;
    private javax.swing.JTextField tiempoConsumicion2_txt;
    private javax.swing.JTextField tiempoEspera_txt;
    private javax.swing.JTextField tiempoTicket_txt;
    private javax.swing.JTextField tiempoUtilizacionMesa1_txt;
    private javax.swing.JTextField tiempoUtilizacionMesa2_txt;
    // End of variables declaration//GEN-END:variables
}
