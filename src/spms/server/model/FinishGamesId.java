package spms.server.model;
// Generated Apr 11, 2016 12:21:00 AM by Hibernate Tools 3.6.0


import java.util.Date;

/**
 * FinishGamesId generated by hbm2java
 */
public class FinishGamesId  implements java.io.Serializable {


     private int gameId;
     private String playerIp;
     private Date time;

    public FinishGamesId() {
    }

    public FinishGamesId(int gameId, String playerIp, Date time) {
       this.gameId = gameId;
       this.playerIp = playerIp;
       this.time = time;
    }
   
    public int getGameId() {
        return this.gameId;
    }
    
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    public String getPlayerIp() {
        return this.playerIp;
    }
    
    public void setPlayerIp(String playerIp) {
        this.playerIp = playerIp;
    }
    public Date getTime() {
        return this.time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof FinishGamesId) ) return false;
		 FinishGamesId castOther = ( FinishGamesId ) other; 
         
		 return (this.getGameId()==castOther.getGameId())
 && ( (this.getPlayerIp()==castOther.getPlayerIp()) || ( this.getPlayerIp()!=null && castOther.getPlayerIp()!=null && this.getPlayerIp().equals(castOther.getPlayerIp()) ) )
 && ( (this.getTime()==castOther.getTime()) || ( this.getTime()!=null && castOther.getTime()!=null && this.getTime().equals(castOther.getTime()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getGameId();
         result = 37 * result + ( getPlayerIp() == null ? 0 : this.getPlayerIp().hashCode() );
         result = 37 * result + ( getTime() == null ? 0 : this.getTime().hashCode() );
         return result;
   }   


}


