package org.titlepending.entities;

import jig.Entity;
import jig.Vector;
import org.newdawn.slick.Animation;

import java.io.Serializable;

public class Ship extends  implements Serializable {


    public Ship(double x, double y, int playerID){
        super((float) x,(float) y);
    }


    public void setStats(int[] stats) {
        this.stats = stats;
    }

    public int[] getStats(){return stats;}



    public int getPlayerID() {
        return playerID;
    }


}
