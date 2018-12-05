package org.titlepending.entities;

import jig.Entity;
import jig.ResourceManager;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.titlepending.client.Client;
import org.titlepending.server.ServerObjects.Ship;

public class ClientShip extends Entity {

    protected int stats[]; //hull, sail, cannon, captain... Get these numbers from another state
    private boolean isDead = false;
    private int playerID;

    private static SpriteSheet ship_RSC_96 = new SpriteSheet(ResourceManager.getImage(Client.SHIP_RSC), 64, 96);

    Image lgHaul = ship_RSC_96.getSubImage(1, 0).getScaledCopy(3f);
    Image medHaul = ship_RSC_96.getSubImage(0, 0).getScaledCopy(3f);
    Image smallHaul = ship_RSC_96.getSubImage(2, 0).getScaledCopy(3f);
    Image oneSail = ship_RSC_96.getSubImage(5, 0).getScaledCopy(3f);
    Image twoSails = ship_RSC_96.getSubImage(4, 0).getScaledCopy(3f);
    Image threeSails = ship_RSC_96.getSubImage(3, 0).getScaledCopy(3f);
    Image oneCannon = ship_RSC_96.getSubImage(6, 1).getScaledCopy(3f);
    Image twoCannons = ship_RSC_96.getSubImage(5, 1).getScaledCopy(3f);
    Image threeCannons = ship_RSC_96.getSubImage(4, 1).getScaledCopy(3f);


    public ClientShip(double x, double y, int playerID) {
        super((float) x, (float) y);
        this.playerID = playerID;
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


    public void setStats(int[] stats) {
        this.stats = stats;
    }

    public int[] getStats(){return stats;}

    public int getPlayerID() {
        return playerID;
    }

    public void addSprites(){
        //SpriteSheet Shipsprites = ResourceManager.getSpriteSheet(Client.SHIP_RSC,64,96);
        //SpriteSheet Sailsprites = ResourceManager.getSpriteSheet(Client.SHIP_RSC,64,32);
        //these will change depending on what is selected most likely handled elsewhere

        //Setting Cannons
        switch (stats[2]) {
            case 0:
                addImage(oneCannon);
                break;
            case 1:
                addImage(twoCannons);
                break;
            case 2:
                addImage(threeCannons);
                break;
            default:
                System.out.println("I BROKE MY shooters!!!");
                System.exit(-100);
                break;
        }

        // Setting Hull
        switch (stats[0]) {
            case 0:
                addImage(smallHaul);
                break;
            case 1:
                addImage(medHaul);
                break;
            case 2:
                addImage(lgHaul);
                break;
            default:
                System.out.println("I BROKE MY haul!!!");
                System.exit(-100);
                break;

        }

        // Setting Sails
        switch (stats[1]) {
            case 0:
                addImage(oneSail);
                break;
            case 1:
                addImage(twoSails);
                break;
            case 2:
                addImage(threeSails);
                break;
            default:
                System.out.println("I BROKE MY SAILS!!!");
                System.exit(-100);
                break;
        }



    }

    public void update(final int delta) {
        if(isDead==false) {//they are still alive
            //translate(velocity.scale(delta));
        }
        else {//they died
        }
        //

    }
}
