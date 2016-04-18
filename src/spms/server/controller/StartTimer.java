/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spms.server.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import spms.common.HibernateUtil;
import spms.server.model.ActiveGames;
import spms.server.model.Connections;
import spms.server.model.FinishGames;
import spms.server.model.FinishGamesId;
import spms.server.model.GameType;

/**
 *
 * @author yazeedalmusharraf
 */
public class StartTimer extends Thread {

    public void run() {
        while (true) {
            try {
                Date d = new Date();

                d.setMinutes(d.getMinutes() - 1);

               // System.out.println(d.toString());
                Session dbconn = HibernateUtil.getSessionFactory().openSession();
                dbconn.beginTransaction();
                Query q = dbconn.createQuery("Select g from ActiveGames g where g.createdTime<=:time").setParameter("time", d);
                List<ActiveGames> games = q.list();
                dbconn.close();

                Iterator<ActiveGames> gi = games.iterator();
                while (gi.hasNext()) {
                    ActiveGames g = gi.next();
                    dbconn = HibernateUtil.getSessionFactory().openSession();
                    dbconn.beginTransaction();
                    q = dbconn.createQuery("Select c from Connections c where c.activeGames.activeGameId=:id and c.status=:s").setParameter("id", g.getActiveGameId()).setParameter("s", SysConst.CONNECTION_STATUS_WAITING);
                    List<Connections> connections = q.list();
                    dbconn.close();

                    Iterator<Connections> ci = connections.iterator();
                    while (ci.hasNext()) {
                        Connections c = ci.next();
                        if (TCPServer.sessionList != null) {
                            if (TCPServer.sessionList.size() > 0) {
                                System.out.println(TCPServer.sessionList.get(c.getSessionId()).server.getRemoteSocketAddress().toString());
                          TCPServer session= TCPServer.sessionList.get(c.getSessionId());
                          
        String gen = new String(Files.readAllBytes((new File(g.getGeneratorFile()).toPath())));
        gen=gen.replace("\n",SysConst.ENDL);
        
                    FinishGames f=new FinishGames();
                    f.setId(new FinishGamesId(g.getGameType().getGameId(),c.getClientIp(),c.getConnectTime()));
                    f.setScore(0);
                    f.setSessionId(c.getSessionId());
                    dbconn = HibernateUtil.getSessionFactory().openSession();
                    dbconn.beginTransaction();
                    dbconn.save(f);
                    dbconn.beginTransaction().commit();
                    dbconn.close();
                    
                    
                          session.send(SysConst.START_GAME+gen);
                            
              
                          
                            }

                        }
                    }
                    
                    
                    dbconn = HibernateUtil.getSessionFactory().openSession();
                    dbconn.beginTransaction();
                    dbconn.delete(g);
                    dbconn.beginTransaction().commit();
                    dbconn.close();
                      
                  
                    
                    
                   

                }

                Thread.sleep(30000);
            } /*
   
             */ catch (InterruptedException ex) {
                Logger.getLogger(StartTimer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(StartTimer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
