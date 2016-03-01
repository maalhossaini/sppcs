package spms.server.controller;

import java.io.IOException;
import java.io.StringWriter;
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
        Integer gameId= (Integer)jobj.get("gameId");
        
        
        Session dbconn=HibernateUtil.getSessionFactory().openSession();
        dbconn.beginTransaction();
        Query q=dbconn.createQuery("Select g from ActiveGames g where g.activeGameId=:id").setParameter("id",gameId);
        ActiveGames ags= (ActiveGames) q.uniqueResult();
        dbconn.close();
                
                
                
                
        
        
        } catch (ParseException ex) {
            Logger.getLogger(ActiveGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    public void sendGames(){         //Through this method server can send game that client can join it  
        
        try {

        
        Session dbconn=HibernateUtil.getSessionFactory().openSession();
        dbconn.beginTransaction();
        Query q=dbconn.createQuery("Select g from  ActiveGames a,GameType g where a.gameType.gameId=g.gameId").setParameter("state", SysConst.CONNECTION_STATUS_WAITING);
        List<GameType> games=q.list();
        dbconn.close();
        
        
        
        
        JSONArray list=new JSONArray();
        Iterator<GameType> gi=games.iterator();
        while(gi.hasNext()){
            GameType g=gi.next();
            JSONObject obj = new JSONObject();
            obj.put("gameId",g.getGameId());
            obj.put("gameDesc",g.getGameDesc());
            obj.put("gameName",g.getGameName());
            obj.put("gameLavel",g.getGameLavel());
            list.add(obj);
        
        }
                
        StringWriter out = new StringWriter();
        list.writeJSONString(out);
        
        String jsonText = out.toString();
        
        session.send(jsonText);
        
        
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
        
            FinishGames fg=new FinishGames();
            
            fg.setId(new FinishGamesId(game.getGameId(),session.server.getRemoteSocketAddress().toString(),new Date()));
            fg.setScore(score);
 
        
             Session dbconn=HibernateUtil.getSessionFactory().openSession();
             dbconn.beginTransaction().begin();
               dbconn.save(fg);
               dbconn.beginTransaction().commit();
             dbconn.close();
    
    }

}
