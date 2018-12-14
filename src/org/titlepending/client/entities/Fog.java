package org.titlepending.client.entities;

import jig.ConvexPolygon;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.titlepending.client.Client;

public class Fog extends Entity {
    private ConvexPolygon hitBox;
    private Circle test;
    SpriteSheet FogShape = new SpriteSheet(ResourceManager.getImage(Client.FOG_RSC),512 ,512 );
       public Fog(float dx, float dy) {
           super(dx,dy);
           Image fogImage = FogShape.getSubImage(0,0,512,512);
           fogImage.setFilter(Image.FILTER_LINEAR);
           addImage(fogImage);
           hitBox = new ConvexPolygon(150);
           addShape(hitBox, new Vector(0, 0), Color.transparent, Color.transparent);
           this.scale(32);
           test = new Circle(this.getX(),this.getY(),150*this.getScale());
    }
    public void update(){
       this.scale(.9995f);
       test.setRadius(test.getRadius()*.9995f);
       test.setCenterX(this.getX());
       test.setCenterY(this.getY());

    }

    public Circle getHitBox(){return test;}

    public float getRadius(){
           return test.getRadius()*5;
    }
    @Override
    public void render(Graphics g){
           super.render(g);
           if(Client.DEBUG){
               g.setColor(Color.black);
               g.draw(test);
           }
    }
}
