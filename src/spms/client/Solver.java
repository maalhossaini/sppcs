/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spms.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author yazeedalmusharraf
 */
public class Solver {
    TCPClient session;
    GameType game;  //This object contains the game that chosen by client (or that He created)
    Process pddlProcces;  //This object responsible for calling planner and give it the generatorFile that chosen by client and receive planner output to be sent to the server

    public Solver(TCPClient session,GameType game) {
        this.game = game;
        this.session=session;
    }
    
    public void executePDDL(){  //Through this method will call planner and give it generatorFile that chosen by client and receive planner output to be sent to the server
        
        //TODO: call to planner software & send the generator file to planner
        
    }
    
    public void sendFinishGame(){  //We can through this method send to server the final result of game after planner end of solution
        
        String result="";
        List<FinishGames> finishResult =new ArrayList<FinishGames>();
        
        //TODO: recieve result from planner
        
        
        session.send(SysConst.SEND_FINISHED_GMAE+result);
        
        String data=session.receive();
         JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(data);
            JSONArray array = (JSONArray) obj;
            Iterator ait = array.iterator();
            
            while (ait.hasNext()) {
                FinishGames r =  (FinishGames) ait.next();
                finishResult.add(r);

            }
            
            
            //TODO: show results for all client joined to same game on UI 
            
        }   catch (ParseException pe) {
            pe.printStackTrace();
        }
        
        
    }
    
    
}
