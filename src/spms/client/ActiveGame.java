/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spms.client;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author yazeedalmusharraf
 */
public class ActiveGame {
    
    TCPClient session;  //Through this Object we can reach communication between the Server and Client and call Method send and receive data to and from the server
    GameType game;  //This Object contains the game that chosen by client

    public ActiveGame(TCPClient session) {
        this.session = session;
    }
    
    
    public void joinToGame(){  //this method sends game data that chosen by client to join it
        
        //TODO: get selected game from UI & set to game
        
        try {

        JSONObject obj = new JSONObject();
        
        obj.put("gameId",game.getGameId());

        
        StringWriter out = new StringWriter();
        obj.writeJSONString(out);
        
        String jsonText = out.toString();
        
        session.send(SysConst.JOIN_TO_GAME+jsonText);
        

        
        setConnection();
        
        //TODO: move client to waiting screen UI
        
        String data="";
        data=session.receive();
        
        // TODO: write generator file on storage
        
        Solver game_solver=new Solver(session,game);
        game_solver.executePDDL();
        
        
        
        } catch (IOException ex) {
            Logger.getLogger(CreateGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }
    
    public List<GameType> receiveGames(){  //Through this method client can receive game that join it
        List<GameType> games=new ArrayList<GameType>();
        
        session.send(SysConst.GET_ACTIVE_GAMES);
        String data = session.receive();
        
        if(data.startsWith(SysConst.NO_DATA))
            return games;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(data);
            JSONArray array = (JSONArray) obj;
            Iterator ait = array.iterator();
           
            while (ait.hasNext()) {
               
                JSONObject jo= (JSONObject) ait.next();
                GameType g = new GameType();
                g.setGameId(Integer.parseInt(jo.get("gameId").toString()));
               g.setGameName(jo.get("gameName").toString());
               g.setGameDesc(jo.get("gameDesc")+"");
               g.setGameLavel(Integer.parseInt(jo.get("gameLavel").toString()));
               
                games.add(g);

            }
            
        

        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return games;
        
    }
    
    public void setConnection(){  //This method send the connection status between server and client to server (the case will be sent to the server that client has done join on one of the game and start solve the game)
        
        session.send(SysConst.CONNECTION_STATUS_JOIN);
        
        
    }
    
    
    
}
