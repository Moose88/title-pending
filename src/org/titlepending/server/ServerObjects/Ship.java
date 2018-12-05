package org.titlepending.server.ServerObjects;

import java.io.Serializable;

public class Ship extends GameObject implements Serializable {
    private int[] stats;
    private int playerID;
    public Ship(float x, float y, int playerID){
        super(x,y,0,0);
        this.playerID=playerID;
    }


    public void setStats(int[] stats) {
        this.stats = stats;
    }

    public int[] getStats(){return stats;}

    public int getPlayerID() {
        return playerID;
    }


}
