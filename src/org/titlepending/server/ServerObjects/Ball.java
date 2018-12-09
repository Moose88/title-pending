package org.titlepending.server.ServerObjects;

import java.io.Serializable;

public class Ball extends GameObject implements Serializable {
    private int ttl;
    private int ballID;
    public Ball(float x, float y, float vx, float vy, int ballID, int ttl){
        super(x,y,vx,vy);
        this.ttl = ttl;
        this.ballID= ballID;
    }
    public int getTtl(){return this.ttl;}
    public void update(int delta){
        super.update(delta);
        ttl -= delta;
    }
    public int getBallID(){return ballID;}

}
