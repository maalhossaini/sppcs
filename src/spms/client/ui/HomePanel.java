/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spms.client.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import spms.client.ActiveGame;
import spms.client.CreateGame;
import spms.client.GameType;

/**
 *
 * @author yazeedalmusharraf
 */
public class HomePanel extends javax.swing.JPanel {
    CreateGame creator;
    ActiveGame active;
    Timer timer;
    /**
     * Creates new form HomePanel
     */
    public void getGameLists(){
       
        
     List<GameType> list= creator.receiveGames();
                  
  // List<GameType> list= active.receiveGames();
     Object[] data1=new Object[list.size()];
     Iterator<GameType> i=list.iterator();
     int index=0;
     while(i.hasNext()){
        GameType gt= i.next();
         data1[index]=gt.getGameName()+" (Level "+gt.getGameLavel()+")";
         index++;
     }
     jList1.setListData(data1);
     System.out.println("##########################################3");
     //list= creator.receiveGames();
     list= active.receiveGames();
     Object[] data2=new Object[list.size()];
     i=list.iterator();
     index=0;
     while(i.hasNext()){
         GameType g=i.next();
         data2[index]=g.getGameName()+" (Level "+g.getGameLavel()+")";
         index++;
     }
     jList2.setListData(data2);
    
    }
    public HomePanel() {
        creator=new CreateGame(ClientFrame.tcp);
        active=new ActiveGame(ClientFrame.tcp);
        initComponents();
        getGameLists();
        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
        
         timer = new Timer();
       
        timer.scheduleAtFixedRate(new TimerTask() {
          @Override
          public void run() {
            
              
             List<GameType>   list= active.receiveGames();
             Object[] data2=new Object[list.size()];
             Iterator<GameType> i=list.iterator();
            int index=0;
            while(i.hasNext()){
                GameType g=i.next();
                data2[index]=g.getGameName()+" (Level "+g.getGameLavel()+")";
                index++;
            }
            jList2.setListData(data2);
    

      
          }
        }, 30000,30000);
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel1.setText("Games Available to Create");

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList2);

        jLabel2.setText("Games Available to Join");

        jButton1.setText("create");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("join");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(52, 52, 52))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(33, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(jList1.getSelectedValue()!=null){
        int g=jList1.getSelectedIndex();
        creator.createGame(g);
        timer.cancel();
        ClientFrame.frame.viewWaitingPanel(creator);
        }else{
         JOptionPane.showMessageDialog(null, "Please Select Game !", "WARNING", JOptionPane.WARNING_MESSAGE);
        
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if(jList2.getSelectedValue()!=null){
        int g=jList2.getSelectedIndex();
        active.joinToGame(g);
        timer.cancel();
        ClientFrame.frame.viewWaitingPanel(active);
        }else{
         JOptionPane.showMessageDialog(null, "Please Select Game !", "WARNING", JOptionPane.WARNING_MESSAGE);
        
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
