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
    SpriteSheet RSC_128_128 = new SpriteSheet(ResourceManager.getImage(Client.SS2_RSC),128 ,128 );
    //you would call draw circle at the edge(300*32) and as time goes on decrease the radius
    // also want to call for a bit in the opposite direction for fog cover.
    public Fog(float dx, float dy) {
        super(dx,dy);
        velocity = new Vector(dx, dy);
        addImage(RSC_128_128.getSubImage(2, 2).getScaledCopy(3f));
        //addShape(new ConvexPolygon((float)(200*32), (float)(200*32)), new Vector(0, 0), Color.white, Color.white);
        addShape(new ConvexPolygon((float)155*32),new Vector(32*150,32*150), new Color(200,100,100,100),Color.red);

    }
//    public void update(){
//
//    }

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
