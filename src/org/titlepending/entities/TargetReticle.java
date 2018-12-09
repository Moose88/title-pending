package org.titlepending.entities;

import jig.ConvexPolygon;
import jig.Entity;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class TargetReticle extends Entity {
    ConvexPolygon shape;

    private boolean isVisible;

    public TargetReticle(float x,float y){
        super(x,y);
        shape = new ConvexPolygon(30);
        addShape(shape, Color.blue,Color.black);
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
