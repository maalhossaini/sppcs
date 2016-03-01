

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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TCPServer extends Thread{
    
 Socket server;
 static List<TCPServer> sessionList;
 DatagramSocket mcast ;

    public TCPServer(Socket server) {
        this.server = server;
     try {
         mcast=new DatagramSocket();
     } catch (SocketException ex) {
         Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
     }
    }
    
 @Override
    public void run(){
             
             
    
    }
    

    public void send(Object data){
     try {
         byte[] buff=data.toString().getBytes();
         DataOutputStream outToClient = new DataOutputStream(server.getOutputStream());
         outToClient.write(buff, 0, buff.length);
         outToClient.flush();
         //outToClient.close();
         
        } catch (IOException ex) {
         Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    public String receive(){
        String buff="";
     try {
         BufferedReader inFromClient = new BufferedReader(new InputStreamReader(server.getInputStream()));
         //inFromClient.
        if(inFromClient.ready()){
               buff= inFromClient.readLine();
                
         }
         
         
     } catch (IOException ex) {
         ex.printStackTrace();
       //  System.out.println(ex);
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
