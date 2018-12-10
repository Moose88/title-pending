package org.titlepending.server.ServerObjects;

import org.titlepending.server.Server;

import java.io.Serializable;

public class GameObject implements Serializable {
    protected float x;
    protected float y;
    protected float vx;
    protected float vy;
    protected boolean updated;
    protected int playerID;
    protected boolean dead;
    public GameObject(float x, float y, float vx,float vy){
        this.x=x;
        this.y=y;
        this.vx=vx;
        this.vy=vy;
        if(Server.DEBUG){
            System.out.println("Given x: "+x+"My x: "+this.x+" Given y: "+y+" My y: "+this.y);
        }
        this.updated = false;
        this.dead=false;
    }

    public void setVelocity(float vx, float vy){
        this.vx = vx;
        this.vy = vy;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }

    public boolean getUpdated(){return updated;}

    public float getX(){return x;}

    public float getY(){return y;}

    public void setDead(boolean dead){this.dead=dead;}

    public boolean getDead(){return dead;}
    public void update(int delta){
        x += vx*delta;
        y += vy*delta;
    }

}
