package org.titlepending.entities;

import jig.Entity;
import jig.Vector;
import org.newdawn.slick.Animation;

import java.io.Serializable;

public class Ship extends Entity implements Serializable {
    private int healthcount = 100;
    private Vector velocity;
    private float direction;
    private Animation ship;
    private Animation sail;
    private Animation cannon;
    protected int stats[]; //hull,sail,cannon,captain... Get these numbers from another state
    private boolean isDead = false;
    private int playerID;

    public Ship(double x, double y, int playerID){
        super((float) x,(float) y);
        this.playerID = playerID;
    }
    public void setVelocity(final Vector m) {
        velocity = m;
    }
    public void fire(Ship myship){
        //give birth to cannon balls
        //fire them perpendicular to ships velocity
    }
    public void isHit(Entity object,Ship target){
        //if object is cannon ball take x damage
        //takeDamage(target, x)
        //else if object is ship check velocity of ship object
        //takedamage(target,dmg based off of object speed)
        //takedamage(object, based off of targets speed)
    }
    public void takeDamage(Ship target,int dmg){
        target.healthcount-=dmg;
        if (target.healthcount<=0){
            //ur dead bro

        }
    }
    public void rotateship(Ship target,int direction){
        //direction 0 = left, 1 = right
        if(direction==0){
            target.rotate(-1.0);
            //velocity direction calculation
        }
        else if(direction==1){
            target.rotate(1.0);
            //velocity direction calculation
        }
    }

    public void setStats(int[] stats) {
        this.stats = stats;
    }

    public int[] getStats(){return stats;}

    public void update(final int delta) {
        if(isDead==false) {//they are still alive
            //translate(velocity.scale(delta));
        }
        else {//they died
        }
       //

    }

    public int getPlayerID() {
        return playerID;
    }


}
