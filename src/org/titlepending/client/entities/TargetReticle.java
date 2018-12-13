package org.titlepending.client.entities;

import jig.ConvexPolygon;
import jig.Entity;
import jig.ResourceManager;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.titlepending.client.Client;

public class TargetReticle extends Entity {

    private boolean isVisible;
    private boolean isTurret;

    public TargetReticle(float x,float y,boolean isTurret){
        super(x,y);
        this.isTurret = isTurret;
        SpriteSheet sheet = new SpriteSheet(ResourceManager.getImage(Client.SS2_RSC), 128, 128);
        Image reticle;
        if(!isTurret)
            reticle = sheet.getSubImage(3, 0).getScaledCopy(.75f);
        else
            reticle = sheet.getSubImage(3, 1).getScaledCopy(.75f);
        ConvexPolygon shape = new ConvexPolygon(30);
        addShape(shape, Color.transparent, Color.transparent);
        addImage(reticle);
        isVisible=false;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    @Override
    public void render(Graphics g){
        if(isVisible){
            super.render(g);
        }
    }

}
