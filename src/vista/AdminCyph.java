/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controler.AccessPassword;
import controler.ApplicationData;
import controler.ExportDataToXML;
import controler.ImportDataFromXML;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import javax.swing.JOptionPane;
import org.dom4j.DocumentException;
//import static javax.swing.JOptionPane.QUESTION_MESSAGE;
//import static javax.swing.JOptionPane.YES_NO_OPTION;

/**
 *
 * @author tony
 */
public class AdminCyph extends javax.swing.JFrame {

    /**
     * Creates new form AdminCyph
     */
    
    ImageIcon myIcon;
    MainWindow mw;
    
    public AdminCyph(MainWindow mw) {
        initComponents();
        this.mw = mw;
        this.setLocationRelativeTo(null);        
        myIcon=new ImageIcon(this.getClass().getResource("ET-quakewars_s.png"));      
        myIconPicture.setIcon(myIcon);        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnChangePassword = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnOk = new javax.swing.JButton();
        btnAbout = new javax.swing.JButton();
        btnExportTxt = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        myIconPicture = new javax.swing.JLabel();
        btnExportarXML = new javax.swing.JButton();
        btnImportarXML = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Administração");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnChangePassword.setText("Alterar password");
        btnChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangePasswordActionPerformed(evt);
            }
        });
        jPanel1.add(btnChangePassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 12, 140, -1));

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 235, 162, -1));

        btnOk.setText("OK");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });
        jPanel1.add(btnOk, new org.netbeans.lib.awtextra.AbsoluteConstraints(202, 235, 162, -1));

        btnAbout.setText("Acerca de...");
        btnAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAboutActionPerformed(evt);
            }
        });
        jPanel1.add(btnAbout, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 94, 140, -1));

        btnExportTxt.setText("Exportar txt");
        btnExportTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportTxtActionPerformed(evt);
            }
        });
        jPanel1.add(btnExportTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 53, 140, -1));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(myIconPicture)
                .addContainerGap(178, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(myIconPicture)
                .addContainerGap(168, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(174, 12, 190, 180));

        btnExportarXML.setText("Exportar XML");
        btnExportarXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarXMLActionPerformed(evt);
            }
        });
        jPanel1.add(btnExportarXML, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 130, -1));

        btnImportarXML.setText("Importar XML");
        btnImportarXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarXMLActionPerformed(evt);
            }
        });
        jPanel1.add(btnImportarXML, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 130, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangePasswordActionPerformed
        // TODO add your handling code here:        
        AccessPassword.changePassword(); 
        ApplicationData.getAppData().savePassData();
        JOptionPane.showMessageDialog(null, "Nova password definida!");
    }//GEN-LAST:event_btnChangePasswordActionPerformed

    private void btnAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAboutActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Produzido por Tony - toze100@gmail.com", "Acerca de CYPH", 1);
    }//GEN-LAST:event_btnAboutActionPerformed

    private void btnExportTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportTxtActionPerformed
        // TODO add your handling code here:
        Object[] options = {"Exportar!", "Cancelar"};
        int n = JOptionPane.showOptionDialog(null, 
                "Pretende exportar dados para ficheiro \"cyphDados.txt?\"",
                "Exportar...",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (n == 0){
            PanelTextView ptv = new PanelTextView();
            ptv.setVisible(true);
            ApplicationData.exportTxt(ptv);
            
        }else{
            // do nothing...
        }
    }//GEN-LAST:event_btnExportTxtActionPerformed

    private void btnExportarXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarXMLActionPerformed
        // TODO add your handling code here:
                String[] options = {"Exportar!", "Cancelar"};
        int n = JOptionPane.showOptionDialog(null, 
                "Pretende exportar dados para ficheiro \"cyphDados.xml?\"",
                "Exportar XML...",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (n == 0){
                    try {
                        ExportDataToXML exportXML = new ExportDataToXML();
                        //ptv.setVisible(true);
                        //ApplicationData.exportTxt(ptv);
                    } catch (IOException ex) {
                        //Logger.getLogger(AdminCyph.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Erro no XML parser: "+ex);
                    }
            
        }else{
            // do nothing...
        }
        
    }//GEN-LAST:event_btnExportarXMLActionPerformed

    private void btnImportarXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarXMLActionPerformed
        try {
            // TODO add your handling code here:
            ImportDataFromXML importXML = new ImportDataFromXML();
            mw.updatePasswordsList();
            
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(AdminCyph.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro no XML parser: "+ex);
        }
    }//GEN-LAST:event_btnImportarXMLActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbout;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnChangePassword;
    private javax.swing.JButton btnExportTxt;
    private javax.swing.JButton btnExportarXML;
    private javax.swing.JButton btnImportarXML;
    private javax.swing.JButton btnOk;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel myIconPicture;
    // End of variables declaration//GEN-END:variables
}
