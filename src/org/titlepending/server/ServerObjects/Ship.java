package org.titlepending.server.ServerObjects;

import org.titlepending.server.Server;

import java.io.Serializable;

public class Ship extends GameObject implements Serializable {
    private int[] stats;
    private int playerID;
    private float heading;
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

    public float getHeading(){return heading;}
    public void setHeading(float heading){this.heading = heading;}
    public void update(int delta){
//        if(Server.DEBUG)
//            System.out.println("Ship "+playerID+": current vx:"+this.vx+" updating vx: "+vx+
//                    " current vy: "+vy+" current x: "+this.x+" current y: "+this.y);

        x+=vx*delta;
        y+=vy*delta;

//        if(Server.DEBUG)
//            System.out.println("Updated x: " +this.x+" Updated y: "+this.y);

    }



}
