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
public class CreateGame {
    
    TCPServer session;  //Through this Object we can reach communication between the server and Client and call Method send and receive data to and from the client
    GameType game;  //This Object contains the Games that Client wants to established and will be sent it to the client
    List<GameType> games;  //This attribute contains a set of object of type Games which contain game data that Client will chose one of them

    public CreateGame(TCPServer session) {
        this.session = session;
    }

    public CreateGame() {
    }
    
    public void sendGames(){ 
        
        try {
        //Through this method it's called Method send which in class TCPServer and it's send the game data that chosen by client
        Session dbconn=HibernateUtil.getSessionFactory().openSession();
        dbconn.beginTransaction();
        Query q=dbconn.createQuery("Select g from GameType g");
        games=q.list();
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
            Logger.getLogger(CreateGame.class.getName()).log(Level.SEVERE, null, ex);
        }
       
      
      
        
    }
    
    public void receiveGame(String data){  try {
        //Through this method it's called Method Receive which in calss TCPServer and it's receive the game data that can client select it
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(data);

                JSONObject jobj = (JSONObject) obj;
                Integer gameId= (Integer)jobj.get("gameId");

                Session dbconn=HibernateUtil.getSessionFactory().openSession();
                dbconn.beginTransaction();
                Query q=dbconn.createQuery("Select g from GameType g where g.gameId=:id").setParameter("id",gameId);
                game=(GameType) q.uniqueResult();
                dbconn.close();
        
                createGame();
        
        
        } catch (ParseException ex) {
            Logger.getLogger(CreateGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void createGame(){  //Through this method it create game on the server
        
        String generatorFilePath="";
        
        //TODO:: call generator class
        
        
        ActiveGames ags=new ActiveGames();
        ags.setCreatedTime(new Date());
        ags.setGameType(game);
        ags.setGeneratorFile(generatorFilePath);
        
             Session dbconn=HibernateUtil.getSessionFactory().openSession();
             dbconn.beginTransaction().begin();
               dbconn.save(ags);
               dbconn.beginTransaction().commit();
             dbconn.close();
             
       // TODO:: run the Timer
        
        
    }
    
    public void setConnection(){  //These method send the connection status between server and client to the server (the case will be sent to the server that client created game and waiting for other clients doing join to this game that have been created)
        
    }
    
    

}
