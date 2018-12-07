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
    private float destinationX;
    private float destinationY;
    public CannonBall (float x, float y, float destX,float destY, float rotation){
        super(x,y);
        SpriteSheet ss = ResourceManager.getSpriteSheet(Client.SS2_RSC, 32, 32);
        Image image = ss.getSubImage(3,0);
        Animation smoke = new Animation(ss,4,0,6,0,true,300,false);
        addImage(image);
        velocity = new Vector(0,-.5f).rotate(rotation);
        destinationX = destX;
        destinationY = destY;
    }

    public void update(int delta){
        this.setPosition(this.getPosition().add(velocity.scale(delta)));
    }

}
