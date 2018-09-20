/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umg.proyectoido;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author cristianrp
 */
public class IngresoDatos extends javax.swing.JFrame {

    /**
     * Creates new form IngresoDatos
     */
    public IngresoDatos() {
        initComponents();
    }

    private int nOrigen = 0;
    private int nDestino = 0;
    private int counter = 0, counterAux = 0;
    ArrayList<Object> objectList = new ArrayList<>();

    private void esquinaNoroeste() {
        try {
            int initMatriz[][] = null;
            int finalMatriz[][] = null;
            char orientation = ' ';
            int i = 0, j = 0, sumatoriaX = 0, sumatoriaY = 0, resta = 0;
            initMatriz = new int[nOrigen + 1][nDestino + 1];
            finalMatriz = new int[nOrigen + 1][nDestino + 1];
            for (int x = 0; x < (nOrigen + 1); x++) {
                for (int y = 0; y < (nDestino + 1); y++) {
                    initMatriz[x][y] = Integer.parseInt(jtDatos.getValueAt(x, y + 1).toString());
                    finalMatriz[x][y] = 0;
                }
            }
            int sumX = 0, sumY = 0, total = 0;
            if (counterAux == 1) {
                objectList.remove(nDestino + 1);
                counterAux = 0;
            }
            for (int y = 0; y < nDestino; y++) {
                sumX += initMatriz[nOrigen][y];
            }
            for (int x = 0; x < nOrigen; x++) {
                sumY += initMatriz[x][nDestino];
            }
            if (initMatriz[nOrigen][0] == initMatriz[0][nDestino]) {
                orientation = 'm';
                finalMatriz[0][0] = initMatriz[nOrigen][0];
                i++;
                j++;
            } else if (initMatriz[nOrigen][0] < initMatriz[0][nDestino]) {
                orientation = 'd';
                finalMatriz[0][0] = initMatriz[nOrigen][0];
                sumatoriaX = initMatriz[nOrigen][0];
                j++;
            } else {
                orientation = 'a';
                finalMatriz[0][0] = initMatriz[0][nDestino];
                sumatoriaY = initMatriz[0][nDestino];
                i ++;
            }
            
            while (i < nOrigen || j < nDestino) {
                if (orientation == 'd') {
                    resta = initMatriz[i][nDestino] - sumatoriaX;
                    if (resta == initMatriz[nOrigen][j]) {
                        orientation = 'm';
                        finalMatriz[i][j] = resta;
                        sumatoriaX = 0;
                        sumatoriaY = 0;
                        i++;
                        j++;
                    } else if (resta < initMatriz[nOrigen][j]) {
                        orientation = 'a';
                        finalMatriz[i][j] = resta;
                        sumatoriaY += resta;
                        sumatoriaX = 0;
                        i++;
                    } else {
                        orientation = 'd';
                        finalMatriz[i][j] = initMatriz[nOrigen][j];
                        sumatoriaX += initMatriz[nOrigen][j];
                        sumatoriaY = 0;
                        j++;
                    }
                } else if (orientation == 'a') {
                    resta = initMatriz[nOrigen][j] - sumatoriaY;
                    if (resta == initMatriz[i][nDestino]) {
                        orientation = 'm';
                        finalMatriz[i][j] = resta;
                        sumatoriaX = 0;
                        sumatoriaY = 0;
                        i ++;
                        j ++;
                    } else if (resta < initMatriz[i][nDestino]) {
                        orientation = 'd';
                        finalMatriz[i][j] = resta;
                        sumatoriaX += resta;
                        sumatoriaY = 0;
                        j++;
                    } else {
                        orientation = 'a';
                        finalMatriz[i][j] = initMatriz[i][nDestino];
                        sumatoriaY += initMatriz[i][nDestino];
                        sumatoriaX = 0;
                        i++;
                    }
                } else if (orientation == 'm') {
                    if (initMatriz[nOrigen][j] == initMatriz[i][nDestino]) {
                        orientation = 'm';
                        finalMatriz[i][j] = initMatriz[i][nDestino];
                        sumatoriaX = 0;
                        sumatoriaY = 0;
                        i++;
                        j++;
                    } else if (initMatriz[nOrigen][j] < initMatriz[i][nDestino]) {
                        orientation = 'd';
                        finalMatriz[i][j] = initMatriz[nOrigen][j];
                        sumatoriaX = initMatriz[nOrigen][j];
                        sumatoriaY = 0;
                        j++;
                    } else {
                        orientation = 'a';
                        finalMatriz[i][j] = initMatriz[i][nDestino];
                        sumatoriaY = initMatriz[i][nDestino];
                        sumatoriaX = 0;
                        i++;
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Error! :(");
                }
            }
            DefaultTableModel finalModel = new DefaultTableModel();
            if (sumX == sumY) {
                finalModel.setRowCount(nOrigen + 1);
                finalModel.setColumnCount(nDestino + 2);
                initMatriz[nOrigen][nDestino] = sumX;
                total = sumX;
                counterAux = 0;
            } else if (sumX < sumY) {
                finalModel.setRowCount(nOrigen + 1);
                finalModel.setColumnCount(nDestino + 3);
                objectList.add(nDestino + 1, "Ajuste");
                initMatriz[nOrigen][nDestino] = sumY - sumX;
                total = sumY;
                counterAux = 1;
            } else {
                finalModel.setRowCount(nOrigen + 2);
                finalModel.setColumnCount(nDestino + 2);
                initMatriz[nOrigen][nDestino] = sumX - sumY;
                total = sumX;
                counterAux = 2;
            }
            finalModel.setColumnIdentifiers(objectList.toArray());
            for (int p = 0; p < (nOrigen + 1); p++) {
                finalModel.setValueAt("X" + (p + 1), p, 0);
                for (int q = 0; q < (nDestino + 1); q++) 
                    finalModel.setValueAt(finalMatriz[p][q], p, q + 1);
            } 
            finalModel.setValueAt("Demanda", nOrigen, 0);
            if (counterAux == 2) {
                finalModel.setValueAt("Ajuste", finalModel.getRowCount() - 2, 0);
                finalModel.setValueAt("Demanda", finalModel.getRowCount() - 1, 0);
            }
            if (counterAux == 0) 
                finalModel.setValueAt("X" + (finalModel.getRowCount() - 1), finalModel.getRowCount() - 2, 0);
            for (int m = 0; m < finalModel.getRowCount() - 2; m ++) 
                finalModel.setValueAt(initMatriz[nOrigen][m], finalModel.getRowCount() - 1, m + 1);
            for (int n = 0; n < finalModel.getRowCount() - 1; n++)
                finalModel.setValueAt(initMatriz[n][nDestino], n, finalModel.getColumnCount() - 1);
            JOptionPane.showMessageDialog(this, "Esta equilibrado! :) " + sumX + ", " + sumY);
            finalModel.setValueAt(total, finalModel.getRowCount() - 1, finalModel.getColumnCount() - 1);
            jtSolucion.setModel(finalModel);
            int suma = 0;
            for (int r = 0; r < nOrigen; r++) {
                for (int s = 0; s < nDestino; s++)
                    suma += initMatriz[r][s] * finalMatriz[r][s];
            }
            jlResult.setText("z= " + suma);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getClass());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbMetodo = new javax.swing.JComboBox<>();
        spDestino = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        spOrigen = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtDatos = new javax.swing.JTable();
        btnResolver = new javax.swing.JButton();
        jlResult = new javax.swing.JLabel();
        btnSetSize = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtSolucion = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cbMetodo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Esquina Noroeste", "Costo Minimo", "Vogel" }));

        jLabel1.setText("Destino");

        jLabel2.setText("Origen");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos"));

        jtDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jtDatos);

        btnResolver.setText("Resolver");
        btnResolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResolverActionPerformed(evt);
            }
        });

        jlResult.setText("z = ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnResolver))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jlResult)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnResolver)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlResult)
                .addContainerGap())
        );

        btnSetSize.setText("Aceptar");
        btnSetSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetSizeActionPerformed(evt);
            }
        });

        jtSolucion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jtSolucion);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(cbMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSetSize)
                        .addGap(0, 27, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(spOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSetSize))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSetSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetSizeActionPerformed
        try {
            nOrigen = Integer.parseInt(spOrigen.getValue().toString());
            nDestino = Integer.parseInt(spDestino.getValue().toString());
            DefaultTableModel model = new DefaultTableModel();
            model.setRowCount(nOrigen + 1);
            model.setColumnCount(nDestino + 2);
            int i = 0;
            if (counter > 0) {
                objectList.clear();
            }
            objectList.add("");
            while (i < nDestino) {
                objectList.add("C" + (i + 1));
                i++;
            }
            objectList.add("Oferta");
            for (i = 0; i < nOrigen; i++) {
                model.setValueAt("X" + (i + 1), i, 0);
            }
            model.setValueAt("Demanda", nOrigen, 0);
            model.setColumnIdentifiers(objectList.toArray());
            counter++;
            jtDatos.setModel(model);

        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getCause());
            System.out.println(ex.getClass());
        }

    }//GEN-LAST:event_btnSetSizeActionPerformed

    private void btnResolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResolverActionPerformed
        esquinaNoroeste();
    }//GEN-LAST:event_btnResolverActionPerformed

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
            java.util.logging.Logger.getLogger(IngresoDatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IngresoDatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IngresoDatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IngresoDatos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IngresoDatos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnResolver;
    private javax.swing.JButton btnSetSize;
    private javax.swing.JComboBox<String> cbMetodo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel jlResult;
    private javax.swing.JTable jtDatos;
    private javax.swing.JTable jtSolucion;
    private javax.swing.JSpinner spDestino;
    private javax.swing.JSpinner spOrigen;
    // End of variables declaration//GEN-END:variables
}
