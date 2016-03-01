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
public class CreateGame {

    TCPClient session;  //Through this Object we can reach communication between the server and Client and call Method send and receive data to and from the server
    GameType game;  //This Object contains the Games that Client wants to established and will be sent to the server (ie, the game will be sent a gameId and through which the server can access the values of properties Object)
    List<GameType> games;  //This attribute contains a set of object of type Games which contain game data that can  Client created on the server

    public void sendGame() {  //Through this method it's called Method send which in class TCPClient and it's send the game data that chosen by client
        
        
        
        try {

        JSONObject obj = new JSONObject();
        
        obj.put("gameId",game.getGameId());

        
        StringWriter out = new StringWriter();
        obj.writeJSONString(out);
        
        String jsonText = out.toString();
        
        session.send(SysConst.SEND_CREATED_GAME+jsonText);

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

    public void receiveGames() {  //Through this method it's called Method Receive which in calss TCPClient and it's receive the games data that can client select it

        session.send(SysConst.GET_GAMES_TYPE);
        String data = session.receive();
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(data);
            JSONArray array = (JSONArray) obj;
            Iterator ait = array.iterator();
            games = new ArrayList<GameType>();
            while (ait.hasNext()) {
                GameType g = (GameType) ait.next();
                games.add(g);

            }
            
            // TODO: show on UI List
            

        } catch (ParseException pe) {
            pe.printStackTrace();
        }

    }

    public void createGame() {  //Through this method it receive game that want to be created on the server by client
            
        
            // TODO: get selected game from UI list
        
            sendGame();
    
    }

    public void setConnection() {  //These method send the connection status between server and client to the server (the case will be sent to the server that client created game and waiting for other clients doing join to this game that have been created)
    
        session.send(SysConst.CONNECTION_STATUS_CREATED);
        
    
    }

}
