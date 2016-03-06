/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spms.server.controller;

import java.util.Hashtable;

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
    public static final String NO_DATA="NDF";
    public static final String CLOSE_CONNECTION="CCS";
     
    
    public static final String CONNECTION_STATUS_WAITING="W";
    
    public static Hashtable<String,Integer> MUILT_CAST_IP_GROUPS=new Hashtable<String,Integer>();

    public static void parser(String data) {
        String op = data.substring(0, 3);

      
        
        switch (op) {
            case GET_GAMES_TYPE:
            {
                    CreateGame cg=new CreateGame();
                    cg.sendGames();
            }
                break;

            case SEND_CREATED_GAME:
            {
                    CreateGame cg=new CreateGame();
                    cg.receiveGame(data.substring(4));
                   
            }
                break;
            case GET_ACTIVE_GAMES:
            {
                ActiveGame ag=new ActiveGame();
                ag.sendGames();
            }
                break;

            case JOIN_TO_GAME:
            {
                ActiveGame ag=new ActiveGame();
                ag.joinToGame(data.substring(4));
            }
                break;

            case CONNECTION_STATUS_CREATED:

                break;

            case CONNECTION_STATUS_JOIN:

                break;

            case SEND_FINISHED_GMAE:
            {
                 ActiveGame ag=new ActiveGame();
                ag.FinishedGame(data.substring(4));
            }
                break;

        }

    }

}
