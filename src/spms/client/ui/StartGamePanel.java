/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spms.client.ui;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;
import spms.client.GameType;
import spms.client.Solver;

/**
 *
 * @author yazeedalmusharraf
 */
public class StartGamePanel extends javax.swing.JPanel {
     GameType game;
     Solver game_solver;
    /**
     * Creates new form StartGamePanel
     */
    public StartGamePanel(GameType game) {
        initComponents();
        this.game=game;
        jButton1.setVisible(false);
           Date startTime=new Date();
           Timer  timer = new Timer();
         
       final GameType g=game;
        timer.scheduleAtFixedRate(new TimerTask() {
          @Override
          public void run() {
              if(game_solver==null){
                 game_solver=new Solver(ClientFrame.tcp,g);
                 game_solver.executePDDL(jTextArea1,jButton1);
              }
    
                  ClientFrame.frame.setVisible(true);

      
          }
        }, 0,100);
          
   
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        jLabel1.setText("Game Strated");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Finish Game");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 506, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(193, 193, 193)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
   
        
        
          String data=jTextArea1.getText();
        
                String solveTime=(data.trim().substring(data.trim().lastIndexOf("\n"),data.trim().indexOf("seconds total time"))).trim();
                String numSteps=(data.trim().substring(data.trim().indexOf("step"),data.trim().indexOf("time spent:"))).trim();
                numSteps=(numSteps.substring(numSteps.trim().lastIndexOf("\n"),numSteps.trim().lastIndexOf(":"))).trim();
                
        JPanel finish = new FinishPanel(solveTime,numSteps,game);

        ClientFrame.tabPanel.removeAll();
        ClientFrame.tabPanel.addTab("Finish Game", finish);
      
               
        
        JPanel result = new ResultPanel();
        ClientFrame.tabPanel.addTab("Result", result);

        
        
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}