package org.titlepending.entities;

import jig.Entity;
import jig.*;
import jig.Vector;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.titlepending.client.Client;

public class Fog extends Entity {
    private Vector velocity;
    SpriteSheet FogShape = new SpriteSheet(ResourceManager.getImage(Client.FOG_RSC),512 ,512 );
    float rad = (float)150*32;
    Shape fogS = new ConvexPolygon(rad);
    //you would call draw circle at the edge(300*32) and as time goes on decrease the radius
    // also want to call for a bit in the opposite direction for fog cover.
    public Fog(float dx, float dy) {
        super(dx,dy);
        velocity = new Vector(dx, dy);
        addImage(FogShape.getSubImage(0, 0).getScaledCopy(32f),new Vector(150,150));
        //addShape(new ConvexPolygon((float)(200*32), (float)(200*32)), new Vector(0, 0), Color.white, Color.white);
        //addShape(fogS,new Vector(150*16,150*16), new Color(200 ,100,100,100),Color.red);

    }
    public void update(){
        removeShape(fogS);
        if (rad > 25) {
            rad = rad - 10;
        }
        fogS = new ConvexPolygon(rad);
        addShape(fogS,new Vector(150*16,150*16), new Color(200 ,100,100,100),Color.red);
        //removeShape();
    }

//    public void drawCircle(int x0, int y0, int radius){ //radius = 300*32 to start
//    }
//    public void drawfog(Graphics g,int x, int y){
//
//    }
//    public void fogUpdate(){}
//
//    public void renderCircle(Graphics g,int x0, int y0,int radius ){
//        int x = radius - 1;
//        int y = 0;
//        int dx = 1;
//        int dy = 1;
//        int decision = dx - radius*2;
//
//        while(x >= y){
//            drawfog(g,x0 + x,y0 + y);
//            drawfog(g,x0 + y,y0 + x);
//            drawfog(g,x0 - y,y0 + x);
//            drawfog(g,x0 - x,y0 + y);
//            drawfog(g,x0 + x,y0 - y);
//            drawfog(g,x0 + y,y0 - x);
//            drawfog(g,x0 - y,y0 - x);
//            drawfog(g,x0 - x,y0 - y);
//
//            if(decision > 0){
//                x--;
//                dx +=2;
//                decision +=dx -(radius*2);
//            }
//            else if(decision <= 0){
//                y++;
//                decision +=dy;
//                dy+=2;
//            }
//
//        }
//
//    }



}
