package org.titlepending.entities;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.titlepending.client.Client;
import org.titlepending.server.ServerObjects.Ship;

public class ClientShip extends Entity {

    protected int stats[]; //hull, sail, cannon, captain... Get these numbers from another state
    private boolean isDead = false;
    private int playerID;
    private Vector velocity;
    private float rotationRate;

    private float heading;

    private Vector sailVector;

    private static SpriteSheet ship_RSC_96 = new SpriteSheet(ResourceManager.getImage(Client.SHIP_RSC), 64, 96);

    private Image lgHaul = ship_RSC_96.getSubImage(1, 0).getScaledCopy(1.5f);
    private Image medHaul = ship_RSC_96.getSubImage(0, 0).getScaledCopy(1.5f);
    private Image smallHaul = ship_RSC_96.getSubImage(2, 0).getScaledCopy(1.5f);
    private Image oneSail = ship_RSC_96.getSubImage(5, 0).getScaledCopy(1.5f);
    private Image twoSails = ship_RSC_96.getSubImage(4, 0).getScaledCopy(1.5f);
    private Image threeSails = ship_RSC_96.getSubImage(3, 0).getScaledCopy(1.5f);
    private Image SoneCannon = ship_RSC_96.getSubImage(6, 2).getScaledCopy(1.5f);
    private Image StwoCannons = ship_RSC_96.getSubImage(5, 2).getScaledCopy(1.5f);
    private Image SthreeCannons = ship_RSC_96.getSubImage(4, 2).getScaledCopy(1.5f);
    private Image oneCannon = ship_RSC_96.getSubImage(6, 1).getScaledCopy(1.5f);
    private Image twoCannons = ship_RSC_96.getSubImage(5, 1).getScaledCopy(1.5f);
    private Image threeCannons = ship_RSC_96.getSubImage(4, 1).getScaledCopy(1.5f);

    public ClientShip(double x, double y, int playerID) {
        super((float) x, (float) y);
        this.playerID = playerID;
        velocity = new Vector(0f, 0f);
        float center = (float) new Vector((float) x, (float) y).angleTo(new Vector(3200, 3200));
        heading = center;
        rotationRate = 0.05f;



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

    public Vector getVelocity() {
        return velocity;
    }
    public void setVelocity(final Vector v){
        velocity =v;
    }

    public Vector getSailVector() {
        return sailVector;
    }

    public void setStats(int[] stats) {
        this.stats = stats;
    }

    public int[] getStats(){return stats;}

    public int getPlayerID() {
        return playerID;
    }

    public float getHeading() {
        return heading;
    }

    public void updateHeading(int delta){
        heading += delta * rotationRate;
        imageRotate();
    }

    public void updateVelocity(){
        setVelocity(sailVector.setRotation(heading));
        imageRotate();
    }

    public void updateVelocity(Vector stop){
        setVelocity(stop.setRotation(heading));
    }
    public void addSprites(){
        //these will change depending on what is selected most likely handled elsewhere
//

        //Setting Cannons
        switch (stats[2]) {
            case 2:
                if(stats[0] == 2)
                    addImage(SoneCannon);
                else
                    addImage(oneCannon);
                break;
            case 1:
                if(stats[0] == 2)
                    addImage(StwoCannons);
                else
                    addImage(twoCannons);
                break;
            case 0:
                if(stats[0] == 2)
                    addImage(SthreeCannons);
                else
                    addImage(threeCannons);
                break;
            default:
                System.out.println("I BROKE MY shooters!!!");
                System.exit(-100);
                break;
        }

        // Setting Hull
        switch (stats[0]) {
            case 2:
                addImage(smallHaul);
                break;
            case 1:
                addImage(medHaul);
                break;
            case 0:
                addImage(lgHaul);
                break;
            default:
                System.out.println("I BROKE MY haul!!!");
                System.exit(-100);
                break;

        }

        // Setting Sails
        switch (stats[1]) {
            case 2:
                addImage(oneSail);
                sailVector = new Vector(0f, -1f);
                break;
            case 1:
                addImage(twoSails);
                sailVector = new Vector(0f, -1.25f);
                break;
            case 0:
                addImage(threeSails);
                sailVector = new Vector(0f, -1.5f);
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

        translate(velocity.scale(delta));

        imageRotate();

    }

    public void imageRotate(){

        oneCannon.setRotation(heading+90);
        twoCannons.setRotation(heading+90);
        threeCannons.setRotation(heading+90);

        SoneCannon.setRotation(heading+90);
        StwoCannons.setRotation(heading+90);
        SthreeCannons.setRotation(heading+90);

        smallHaul.setRotation(heading+90);
        medHaul.setRotation(heading+90);
        lgHaul.setRotation(heading+90);

        oneSail.setRotation(heading+90);
        twoSails.setRotation(heading+90);
        threeSails.setRotation(heading+90);

    }
}
