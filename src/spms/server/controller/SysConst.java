/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spms.server.controller;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import spms.common.HibernateUtil;
import spms.server.model.Connections;

/**
 *
 * @author yazeedalmusharraf
 */
public class SysConst {

    public static final String GET_GAMES_TYPE = "GGT:";
    public static final String SEND_CREATED_GAME = "SCG:";
    public static final String IP_GROUP = "IPG:";
    public static final String GET_ACTIVE_GAMES = "GAG:";
    public static final String JOIN_TO_GAME = "JTG:";
    public static final String CONNECTION_STATUS_CREATED = "CSC:";
    public static final String CONNECTION_STATUS_JOIN = "CSJ:";
    public static final String SEND_FINISHED_GMAE = "SFG:";
    public static final String DOMAIN_FILE="SDF:";
    public static final String START_GAME="STG:";
    public static final String NO_DATA="NDF:";
    public static final String CLOSE_CONNECTION="CCS:";
    public static final String BACK_TO_HOME="BAK:";
     
    public static final String ENDL="@##@";
    public static final String CONNECTION_STATUS_WAITING="W";
    
    public static Hashtable<String,Integer> MUILT_CAST_IP_GROUPS=new Hashtable<String,Integer>();

    public static void parser(String data,TCPServer session) {
        String op = data.substring(0, 4);

        //System.out.println("OP= "+op);
        
        switch (op) {
            case GET_GAMES_TYPE:
            {
                    CreateGame cg=new CreateGame(session);
                    cg.sendGames();
            }
                break;

            case SEND_CREATED_GAME:
            {
                    CreateGame cg=new CreateGame(session);
                    cg.receiveGame(data.substring(4));
                   
            }
                break;
            case GET_ACTIVE_GAMES:
            {
                ActiveGame ag=new ActiveGame(session);
                ag.sendGames();
            }
                break;

            case JOIN_TO_GAME:
            {
                ActiveGame ag=new ActiveGame(session);
                ag.joinToGame(data.substring(4));
            }
                break;

            case CONNECTION_STATUS_CREATED:

                break;

            case CONNECTION_STATUS_JOIN:

                break;

            case SEND_FINISHED_GMAE:
            {
                 ActiveGame ag=new ActiveGame(session);
                ag.FinishedGame(data.substring(4));
            }
                break;
                
           case CLOSE_CONNECTION:
            {
                session.close();
               
            }
           break;
                case BACK_TO_HOME:
            {
               
               Iterator<TCPServer> sit=TCPServer.sessionList.iterator();
               int index=0;
               while(sit.hasNext()){
                    if(session.server.getRemoteSocketAddress().equals(sit.next().server.getRemoteSocketAddress()))
                    {
                           // System.out.println("RRRRRRRRRRRRRRRRRRRRRRR"+index);
                                break;
                    }
                    index++;
               }
                
                
                
                 Session   dbconn = HibernateUtil.getSessionFactory().openSession();
                  dbconn.beginTransaction();
                  Query  q = dbconn.createQuery("Select c from Connections c where c.sessionId=:id").setParameter("id", index);
                  List<Connections> connections = q.list();
                  dbconn.close();
                  if(connections.size()>0){
                     dbconn = HibernateUtil.getSessionFactory().openSession();
                        dbconn.beginTransaction();
                        dbconn.delete(connections.get(0));
                        dbconn.getTransaction().commit();
                        dbconn.close();
                  }
                  
      
               
            }
            
                break;

        }

    }

}
