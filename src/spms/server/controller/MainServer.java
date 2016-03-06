/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spms.server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import spms.common.HibernateUtil;

/**
 *
 * @author yazeedalmusharraf
 */
public class MainServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            
               Session dbconn=HibernateUtil.getSessionFactory().openSession();
        dbconn.beginTransaction();
        Query q=dbconn.createQuery("Select g from GameType g");
        q.list();
        dbconn.close();
            
            
            ServerSocket welcomeSocket = new ServerSocket(5556);
            
            while(true)
            {
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("Accept Player IP:"+connectionSocket.getRemoteSocketAddress().toString());
                if (connectionSocket != null)
                {
                    TCPServer client = new TCPServer(connectionSocket);
                    
                    client.start();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
