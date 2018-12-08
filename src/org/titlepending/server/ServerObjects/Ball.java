package org.titlepending.server.ServerObjects;

import java.io.Serializable;

public class Ball extends GameObject implements Serializable {
    private int ttl;
    Ball(float x, float y, float vx, float vy, int playerID, int ttl){
        super(x,y,vx,vy);
        this.ttl = ttl;
        this.playerID = playerID;
    }

    public void update(int delta){
        x += vx*delta;
        y += vy*delta;
        ttl -= delta;
    }

}
