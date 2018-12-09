package org.titlepending.entities;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.titlepending.client.Client;

public class CannonBall extends Entity {
    private Vector velocity;
    private int id;
    private float destinationX;
    private float destinationY;
    private int ttl;
    private boolean dead;
    public CannonBall (float x, float y, float destX,float destY, float rotation,int id){
        super(x,y);
        SpriteSheet ss = ResourceManager.getSpriteSheet(Client.SS2_RSC, 32, 32);
        Vector dest = new Vector(destX,destY);
        Image image = ss.getSubImage(3,0);
        Animation smoke = new Animation(ss,4,0,6,0,true,300,false);
        addImage(image);
        velocity = new Vector(0,-.5f).rotate(getPosition().angleTo(dest)+rotation);
        destinationX = destX;
        destinationY = destY;
        ttl = 5000;
        dead = false;
        this.id = id;
    }

    public void update(int delta){
        this.setPosition(this.getPosition().add(velocity.scale(delta)));
        ttl-= delta;
        if(ttl <= 0)
            dead=true;

    }

    public Vector getVelocity() {
        return velocity;
    }

    public int getId(){return id;}
    public float getDestX(){return destinationX;}
    public float getDestY(){return destinationY;}
    public int getTtl(){return ttl;}
    public boolean isDead(){
        return dead;
    }
}
