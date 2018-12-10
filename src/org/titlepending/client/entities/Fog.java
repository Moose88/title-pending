package org.titlepending.client.entities;

import jig.ConvexPolygon;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.Color;
import org.newdawn.slick.SpriteSheet;
import org.titlepending.client.Client;

public class Fog extends Entity {
    private Vector velocity;
    SpriteSheet FogShape = new SpriteSheet(ResourceManager.getImage(Client.FOG_RSC),512 ,512 );
       public Fog(float dx, float dy) {
        super(dx,dy);
        addImage(FogShape.getSubImage(0,0,512,512));//FogShape.getSubImage(0, 0).getScaledCopy(32f),new Vector(150,150));
        addShape(new ConvexPolygon(150), new Vector(0, 0), new Color(200,100,100,100), Color.white);
        this.scale(32);

    }
    public void update(){
       this.scale(.9995f);
    }

}
