package org.titlepending.server.ServerObjects;

import java.io.Serializable;

public class Ball extends GameObject implements Serializable {
    private int ttl;
    private int ballID;
    private float destX;
    private float destY;
    private int playerID;
    public Ball(float x, float y, float vx, float vy, int ballID, int ttl, float destX, float destY,int playerID){
        super(x,y,vx,vy);
        this.ttl = ttl;
        this.ballID= ballID;
        this.destX = destX;
        this.destY = destY;
        this.playerID=playerID;
    }
    public int getTtl(){return this.ttl;}
    public void update(int delta){
        super.update(delta);
        ttl -= delta;
    }
    public float getDestX(){return destX;}
    public float getDestY(){return destY;}
    public int getBallID(){return ballID;}
    public int getPlayerID(){return playerID;}
}
