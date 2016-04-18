package spms.server.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import spms.common.HibernateUtil;
import spms.server.model.ActiveGames;
import spms.server.model.Connections;
import spms.server.model.FinishGames;
import spms.server.model.FinishGamesId;
import spms.server.model.GameType;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class ActiveGame {
    TCPServer session;  //Through this Object we can reach communication between the Server and Client and call Method send and receive data to and from the client
    GameType game;  //This Object contains the game that chosen by client

    public ActiveGame(TCPServer session) {
        this.session = session;
    }

    public ActiveGame() {
    }
    
    
    public void joinToGame(String data){ 
        try {
        //this method receive game data that chosen by clients to join it
        
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(data);
        
        JSONObject jobj = (JSONObject) obj;
        Integer gameId= Integer.parseInt(jobj.get("gameId").toString());
        
        
        Session dbconn=HibernateUtil.getSessionFactory().openSession();
        dbconn.beginTransaction();
        Query q=dbconn.createQuery("Select g from ActiveGames g where g.activeGameId=:id").setParameter("id",gameId);
        ActiveGames ags= (ActiveGames) q.uniqueResult();
        dbconn.close();
           
        
        dbconn=HibernateUtil.getSessionFactory().openSession();
        dbconn.beginTransaction();
        q=dbconn.createQuery("Select t from ActiveGames g,GameType t where g.activeGameId=:id and t.gameId=g.gameType.gameId").setParameter("id",gameId);
        System.out.println(q.getQueryString());
        GameType gt= (GameType) q.uniqueResult();
        dbconn.close();
        
        
               
             Connections gameCon=new Connections();
             
             gameCon.setClientIp(session.server.getRemoteSocketAddress().toString());
             gameCon.setActiveGames(ags);
             gameCon.setSessionId(session.conn_index);
             gameCon.setConnectTime(new Date());
             gameCon.setStatus(SysConst.CONNECTION_STATUS_WAITING);
             
             
             dbconn=HibernateUtil.getSessionFactory().openSession();
             dbconn.beginTransaction().begin();
               dbconn.save(gameCon);
               dbconn.beginTransaction().commit();
             dbconn.close();  
             
        String dom = new String(Files.readAllBytes((new File(gt.getDomainFile()).toPath())));
        dom=dom.replace("\n",SysConst.ENDL);
        session.send(SysConst.DOMAIN_FILE+dom);     
                
        
        
        } catch (ParseException ex) {
            Logger.getLogger(ActiveGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ActiveGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    public void sendGames(){         //Through this method server can send game that client can join it  
        
        try {

        
        Session dbconn=HibernateUtil.getSessionFactory().openSession();
        dbconn.beginTransaction();
        Query q=dbconn.createQuery("Select g from  ActiveGames a,GameType g where a.gameType.gameId=g.gameId order by a.createdTime");
        Query q2=dbconn.createQuery("Select a from  ActiveGames a,GameType g where a.gameType.gameId=g.gameId order by a.createdTime");
        List<ActiveGames> agames=q2.list();
        List<GameType> games=q.list();
        dbconn.close();
        
        
        
        
        JSONArray list=new JSONArray();
        Iterator<GameType> gi=games.iterator();
        Iterator<ActiveGames> ai=agames.iterator();
        while(gi.hasNext()){
            GameType g=gi.next();
            ActiveGames a=ai.next();
            JSONObject obj = new JSONObject();
            obj.put("gameId",a.getActiveGameId());
            obj.put("gameDesc",g.getGameDesc());
            obj.put("gameName",g.getGameName());
            obj.put("gameLavel",g.getGameLavel());
            list.add(obj);
        
        }
                
        StringWriter out = new StringWriter();
        list.writeJSONString(out);
        
        String jsonText = out.toString();
        if(games.size()>0)
                session.send(jsonText);
        else{
              session.send(SysConst.NO_DATA);
        }
        
        
        } catch (IOException ex) {
            Logger.getLogger(ActiveGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setConnection(){  //This method send the connection status between server and client(the case will be sent to the server that client has done join on one of the game and start solve the game)
      
    }
    
    public GameType getGame(){  //Responsible for bringing games from databace for sent it to client
        return null;

    }
    public void FinishedGame(String data){
        
        // TODO:: convert data result to integer score
        int score=0;
         data=data.replace(SysConst.ENDL,"\n");
        
   
        
        
              Session dbconn = HibernateUtil.getSessionFactory().openSession();
                    dbconn.beginTransaction();
                    Query q = dbconn.createQuery("Select f from FinishGames f where f.sessionId=:sid and f.id.playerIp=:ip")
                            .setParameter("sid", session.conn_index).setParameter("ip",session.server.getRemoteSocketAddress().toString());
                    List<FinishGames> fgames = q.list();
                    dbconn.close();
                    if(fgames.size()>0)
                    {
                    FinishGames fg=fgames.get(0);
                    
                       
            String path="/var/spms/solve/"+(fg.getSessionId())+(fg.getId().getGameId())+"solve.pddl";
           
            try {
                FileOutputStream f = new FileOutputStream(path);
                f.write(data.getBytes());
                f.close();
                
                fg.setResult(path);
                
                
                String solveTime=(data.trim().substring(data.trim().lastIndexOf("\n"),data.trim().indexOf("seconds total time"))).trim();
                String numSteps=(data.trim().substring(data.trim().indexOf("step"),data.trim().indexOf("time spent:"))).trim();
                numSteps=(numSteps.substring(numSteps.trim().lastIndexOf("\n"),numSteps.trim().lastIndexOf(":"))).trim();
                
                //System.out.println(numSteps);
                
                fg.setSecondsTime(Double.parseDouble(solveTime));
                fg.setScore(Integer.parseInt(numSteps));
                
            }catch(Exception ex){
                System.err.println(ex.getMessage());
            }
                    
                    
                    
                        
                        
                            dbconn=HibernateUtil.getSessionFactory().openSession();
             dbconn.beginTransaction().begin();
               dbconn.saveOrUpdate(fg);
               dbconn.beginTransaction().commit();
             dbconn.close();
                    }
                    
        
          
     
           
 
        
          
                
             
             // TODO:: call to VAL
    
    }

}
