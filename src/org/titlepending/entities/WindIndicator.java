package org.titlepending.entities;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.titlepending.client.Client;

public class WindIndicator extends Entity {
    private static SpriteSheet ss;
    private Image arrow;
    private  Wind wind;

    public WindIndicator(float x, float y) {
        super(x,y);
        ss = new SpriteSheet(ResourceManager.getImage(Client.SS2_RSC), 32, 32);
        arrow = ss.getSubImage(1, 1).getScaledCopy(3f);
        addImage(arrow);

    }

    public void update(float x, float y){
        this.setPosition(x,y);
    }

    public void setWind(Wind wind){
        this.wind = wind;
        this.rotate(wind.getRotation());
    }
    public Wind getWind(){return wind;}
    public void update(Vector wind){
        this.wind.update(wind);this.rotate(wind.getRotation());
    }

}