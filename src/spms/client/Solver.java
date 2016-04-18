/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spms.client;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
    
    public void executePDDL(javax.swing.JTextArea t,javax.swing.JButton b) {  //Through this method will call planner and give it generatorFile that chosen by client and receive planner output to be sent to the server
        System.out.println("STTTTTTTTTTTTTTTTTTTTTTTTT");
        //TODO: call to planner software & send the generator file to planner
        
        String planner_path="/home/yazeedalmusharraf/Desktop/gameProject/SPMS_Project/";
        int randomNum = 1 + (int)(Math.random() * 1000); 
        String path=planner_path+game.getGameName()+"/plan"+randomNum+".pddl";
     
        Runtime run= Runtime.getRuntime();
        try {
        
       
         // System.out.println(planner_path+"planner/FF-v2.3/./ff -o "+planner_path+game.getGameName()+"/domain.pddl -f "+planner_path+game.getGameName()+"/genfile.pddl");
            pddlProcces=run.exec(planner_path+"planner/FF-v2.3/./ff -o "+planner_path+game.getGameName()+"/domain.pddl -f "+planner_path+game.getGameName()+"/genfile.pddl");
            BufferedReader d= new BufferedReader(new InputStreamReader(pddlProcces.getInputStream()));
            FileOutputStream out = new FileOutputStream(path);

            
            String line;
            while((line=d.readLine())!=null){
                t.append(line+"\n");
                out.write(line.getBytes());
                out.write("\n".getBytes());
            }
            out.close();
            String f=t.getText().replace("\n", SysConst.ENDL);
            b.setVisible(true);
          session.send(SysConst.SEND_FINISHED_GMAE+f);
         
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        
        
        
        
        
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
