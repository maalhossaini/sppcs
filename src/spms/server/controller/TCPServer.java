

package spms.server.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TCPServer extends Thread{
    
 Socket server;
 static List<TCPServer> sessionList;
 DatagramSocket mcast ;

    public TCPServer(Socket server) {
        sessionList=new ArrayList<TCPServer>();
        this.server = server;
     try {
         mcast=new DatagramSocket();
     } catch (SocketException ex) {
         Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
     }
    }
    
 @Override
    public void run(){
          System.out.println(this.receive());
        
             CreateGame creator=new CreateGame(this);
             ActiveGame active=new ActiveGame(this);
             sessionList.add(this);
               active.sendGames();
             creator.sendGames();
           
             
    
    }
    

    public void send(Object data){
       // receive();
     try{
        System.out.println("SEND: "+data.toString());
         byte[] buff=(data.toString()+"\n").getBytes();
         DataOutputStream outToClient = new DataOutputStream(server.getOutputStream());
         outToClient.write(buff, 0, buff.length);
         //outToClient.flush();
        // outToClient.close();
         
         
        } catch (IOException ex) {
         Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    public String receive(){
        String buff="";
     try {
         BufferedReader inFromClient = new BufferedReader(new InputStreamReader(server.getInputStream()));
         //inFromClient.
        while(true){
            if(inFromClient.ready()){
                
               buff= inFromClient.readLine();
              System.out.println("RECEIVED: "+buff);
              break;
            
            }
              Thread.sleep(1000);
         }
         
         
     } catch (IOException ex) {
         ex.printStackTrace();
       //  System.out.println(ex);
     } catch (InterruptedException ex) {
         Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
     }
     
    
    
    return buff;
    }
  public void sendMuiltCast(Object data,String ipGroup){
  
  
        
        try {
                InetAddress addr = InetAddress.getByName(ipGroup);
                DatagramPacket msgPacket = new DatagramPacket(data.toString().getBytes(),
                data.toString().getBytes().length, addr, 7501);
                mcast.send(msgPacket);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
  
  
  
  }
    
}
