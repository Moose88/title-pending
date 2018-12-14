package org.titlepending.client.entities;

import jig.ConvexPolygon;
import jig.Entity;
import jig.Vector;
import org.newdawn.slick.Color;

public class TargetNet extends Entity {
    ConvexPolygon shape;

    TargetNet(float x, float y){
        super(x,y);
        shape = new ConvexPolygon(new Vector[]{
                new Vector(250,-400),
                new Vector(50,0),
                new Vector(-50,0),
                new Vector(-250,-400)
        });
        addShape(shape,new Vector(0f,-75f) ,new Color(200,100,100,100) ,Color.red);
    }

    public void update(int delta){

    }

}
