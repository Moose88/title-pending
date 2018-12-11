package org.titlepending.client.entities;

import jig.ConvexPolygon;
import jig.Entity;
import org.newdawn.slick.Color;

public class TurretTargetNet extends Entity {
    ConvexPolygon targetNet;
    TurretTargetNet(float x, float y){
        super(x,y);
        targetNet = new ConvexPolygon(650);
        addShape(targetNet, Color.transparent,Color.red);
    }
}
