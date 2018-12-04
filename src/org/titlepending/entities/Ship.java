package org.titlepending.entities;

import jig.Entity;
import jig.Vector;
import jig.ResourceManager;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.titlepending.client.Client;

public class Ship extends Entity {
    private int healthcount = 100;
    private Vector velocity;
    private float direction;
    private Animation ship;
    private Animation sail;
    private Animation cannon;
    private int stats[]; //hull,sail,cannon,captain... Get these numbers from another state
    private boolean isDead = false;
    public Ship(final float x, final float y){
        super(x,y);

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


    public void addSprites(){
        SpriteSheet Shipsprites = ResourceManager.getSpriteSheet(Client.SHIP_RSC,64,96);
        SpriteSheet Sailsprites = ResourceManager.getSpriteSheet(Client.SHIP_RSC,64,32);
        //these will change depending on what is selected most likely handled elsewhere
        if(stats[0]==0) {
            ship = new Animation(Shipsprites, 0, 0, 0, 0, true, 1000, true);
        }
        else if(stats[0]==1){
            ship = new Animation(Shipsprites, 1, 0, 1, 0, true, 1000, true);
        }
        else{
            ship = new Animation(Shipsprites, 2, 0, 2, 0, true, 1000, true);
        }
        sail = new Animation(Sailsprites,3,0,3,0,true,1000,true);
        cannon = new Animation(Sailsprites,4,0,4,0,true,1000,true);

        if(stats[2]==0){
            addAnimation(cannon,new Vector(0,0));
        }
        else if(stats[2]==1){
            addAnimation(cannon,new Vector(0,16));
            addAnimation(cannon,new Vector(0,-16));
        }
        else {
            addAnimation(cannon, new Vector(0, 16));
            addAnimation(cannon, new Vector(0, -16));
            addAnimation(cannon, new Vector(0, 0));
        }
        addAnimation(ship);
        if(stats[1] == 0){
            addAnimation(sail, new Vector(0, -8));
        }
        else if(stats[2] == 1){
            addAnimation(sail, new Vector(0, 10));
            addAnimation(sail, new Vector(0, -8));
        }
        else {
            addAnimation(sail, new Vector(0, 10));
            addAnimation(sail, new Vector(0, -8));
            addAnimation(sail, new Vector(0, -26));
        }

    }
}
