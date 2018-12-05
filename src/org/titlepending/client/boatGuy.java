package org.titlepending.client;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.titlepending.client.Client;

public class boatGuy extends Entity {

    public boolean done = false;
    public boolean isMoving = false;
    public Vector movingTo;
    public float speed = 0.15f;
    private Animation boat_a;
    SpriteSheet master = new SpriteSheet(ResourceManager.getImage(Client.SHIP_RSC), 96, 96);

    public Animation getBoat_a() {
        return boat_a;
    }

    public void setBoat_a(Animation boat_a) {
        this.boat_a = boat_a;
    }

    public boatGuy(){
        Image[] boat = new Image[5];
        boat[0] = master.getSubImage(0,1).getScaledCopy(2);
        boat[1] = master.getSubImage(0,1).getScaledCopy(2);
        boat[1].setRotation(10);
        boat[2] = master.getSubImage(0,1).getScaledCopy(2);
        boat[3] = master.getSubImage(0,1).getScaledCopy(2);
        boat[3].setRotation(-10f);
        boat[4] = master.getSubImage(0,1).getScaledCopy(2);

        boat_a = new Animation(boat,200,true);
        setBoat_a(boat_a);


    }

    public void movement(float newX){

        if(isMoving) {
            return;
        }


        movingTo = getPosition().setX(newX);
        isMoving = true;

    }

    public void update(int delta){

        if (isMoving) {
            double angle = getPosition().angleTo(movingTo);
            setPosition(getPosition().add(Vector.getUnit(angle).scale(speed * delta)));
            if (getPosition().epsilonEquals(movingTo, speed * delta)) {
                isMoving = false;

                setPosition(movingTo);

            }

        }

    }


    public void render(Graphics g, int y){
        super.render(g);
        if(done)
            return;
        g.drawAnimation(getBoat_a(), getPosition().getX(), y);

    }
}
