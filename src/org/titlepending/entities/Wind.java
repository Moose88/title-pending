package org.titlepending.entities;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.titlepending.client.Client;

public class Wind extends Entity {
    private int playerID;
    //private Vector velocity;
    private float rotationRate;

    private float heading; //

    private Vector sailVector;

    private static SpriteSheet wind = new SpriteSheet(ResourceManager.getImage(Client.SS2_RSC), 32, 32);

    private Image arrow = wind.getSubImage(1, 1).getScaledCopy(3f);
    public Wind(double x, double y) {
        super((float) x, (float) y);
        //velocity = new Vector(0f, 0f);
        float direction =  (float) Math.random() * 360;
        heading = direction;
        rotationRate = 0.08f;
    }

    public float getHeading() {
        return heading;
    }

    public void setHeading(float heading) {
        this.heading = heading;
    }

    public void imageRotate(){
        arrow.setRotation(heading);
    }
    public void update(final int delta) {
        Vector pos = getPosition();
        //pos.setX(velocity.getX()*delta);
        //pos.setY(velocity.getY()*delta);
        imageRotate();

    }
    public void render(Graphics g, int x,int y){
        super.render(g);
        g.drawImage(arrow, x, y);

    }
}
