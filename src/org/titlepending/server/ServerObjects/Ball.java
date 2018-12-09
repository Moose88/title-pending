package org.titlepending.server.ServerObjects;

import java.io.Serializable;

public class Ball extends GameObject implements Serializable {
    private int ttl;
    private int ballID;
    private float destX;
    private float destY;
    public Ball(float x, float y, float vx, float vy, int ballID, int ttl, float destX, float destY){
        super(x,y,vx,vy);
        this.ttl = ttl;
        this.ballID= ballID;
        this.destX = destX;
        this.destY = destY;
    }
    public int getTtl(){return this.ttl;}
    public void update(int delta){
        super.update(delta);
        ttl -= delta;
    }
    public float getDestX(){return destX;}
    public float getDestY(){return destY;}
    public int getBallID(){return ballID;}

}
