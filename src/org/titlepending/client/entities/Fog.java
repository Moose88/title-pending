package org.titlepending.client.entities;

import jig.ConvexPolygon;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.Color;
import org.newdawn.slick.SpriteSheet;
import org.titlepending.client.Client;

public class Fog extends Entity {
    private ConvexPolygon hitBox;
    SpriteSheet FogShape = new SpriteSheet(ResourceManager.getImage(Client.FOG_RSC),512 ,512 );
       public Fog(float dx, float dy) {
           super(dx,dy);
           addImage(FogShape.getSubImage(0,0,512,512));
           hitBox = new ConvexPolygon(150);
           addShape(hitBox, new Vector(0, 0), new Color(200,100,100,100), Color.white);
           this.scale(32);

    }
    public void update(){
       this.scale(.9995f);
    }

    public ConvexPolygon getHitBox(){return hitBox;}
}
