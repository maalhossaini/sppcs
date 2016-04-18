package spms.server.controller;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Generator {
    
    GameType game;  //This object contains the game that chosen by client (or that He created)
    Process pddlProcces;  ////Through this object will call generator and receive generatorFile and domainFile to be sent to the client

    public Generator(GameType game) {
        this.game = game;
    }
    
    public String executePDDL(){  //Through this method will call generator and receive generatorFile and domainFile to be sent to the client
       
      
        int randomNum = 1 + (int)(Math.random() * 1000); 
        String path="/var/spms/genfiles/"+game.getGameId()+randomNum+".pddl";
      
        System.out.println("/var/spms/"+game.getGameName().toLowerCase()+"/GENERATOR/"+game.getGeneratorCmd());
        
        Runtime run= Runtime.getRuntime();
        try {
            
            
          
            pddlProcces=run.exec("/var/spms/"+game.getGameName().toLowerCase()+"/GENERATOR/"+game.getGeneratorCmd());
            BufferedReader d= new BufferedReader(new InputStreamReader(pddlProcces.getInputStream()));
           FileOutputStream out = new FileOutputStream(path);

            
            String line;
            while((line=d.readLine())!=null){
                out.write(line.getBytes());
                out.write("\n".getBytes());
            }
            out.close();
          
         
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        
        
        return path;
        
    }
    
    
}
