package org.titlepending.client.entities;

import jig.Entity;

public class Fog extends Entity {
    //you would call draw circle at the edge(300*32) and as time goes on decrease the radius
    // also want to call for a bit in the opposite direction for fog cover.
    public void drawCircle(int x0, int y0, int radius){ //radius = 300*32 to start
        int x = radius - 1;
        int y = 0;
        int dx = 1;
        int dy = 1;
        int decision = dx - radius*2;

        while(x >= y){
            drawfog(x0 + x,y0 + y);
            drawfog(x0 + y,y0 + x);
            drawfog(x0 - y,y0 + x);
            drawfog(x0 - x,y0 + y);
            drawfog(x0 + x,y0 - y);
            drawfog(x0 + y,y0 - x);
            drawfog(x0 - y,y0 - x);
            drawfog(x0 - x,y0 - y);

            if(decision > 0){
                x--;
                dx +=2;
                decision +=dx -(radius*2);
            }
            else if(decision <= 0){
                y++;
                decision +=dy;
                dy+=2;
            }

        }
    }
    public void drawfog(int x, int y){

    }

}
