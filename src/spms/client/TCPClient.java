/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spms.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yazeedalmusharraf
 */
public class TCPClient {

    MulticastSocket mcast;  //This object responsible for the receive multiCast of messages that sent by server
    Socket server;  //This object responsible for communication between client and server so client sends through this object connection request then the server approving the connection request, there becomes an open connection between the server and client for send and receive data through it
    public static TCPClient session;  //Through this attribute it can access to connection between client and server at any time and from within any thead in procces
    public String IP_GROUP;

    public TCPClient(Socket server) {
        this.server = server;
        try {
            mcast = new MulticastSocket(7501);
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {  //This method responsible for creating thread whene server accept connecttion to client so that it can receive data at any time from the server
      
        //TODO: run sub thread for wiating receved data from server 
        
    }

    public void send(Object data) {  //Through this method client can send data to server
        try {
            byte[] buff = data.toString().getBytes();
            DataOutputStream outToServer = new DataOutputStream(server.getOutputStream());
            outToServer.write(buff, 0, buff.length);
            outToServer.flush();
            //outToServer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            //Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String receive() {  //Through this method client can receive data from server
        String buff = "";
        try {
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));

            if (inFromServer.ready()) {
                buff = inFromServer.readLine();

            }

        } catch (IOException ex) {
            ex.printStackTrace();

            //Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return buff;
    }

    public String receiveMuiltCast(String ipGroup) {  //Through this method client can receive multiCast from server

        byte[] buf = new byte[1024];
        String msg = "";
        try {
            InetAddress address = InetAddress.getByName(ipGroup);
            mcast.joinGroup(address);
            while (true) {
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                mcast.receive(msgPacket);
                msg = new String(buf, 0, buf.length);

            }
        } catch (IOException ex) {
            ex.printStackTrace();

        }

        return msg;

    }

}
