package org.titlepending.server.ServerObjects;

import jig.Vector;
import org.titlepending.server.Server;

import java.io.Serializable;

public class GameObject implements Serializable {
    protected float x;
    protected float y;
    protected float vx;
    protected float vy;
    protected boolean updated;
    public GameObject(float x, float y, float vx, float vy){
        this.x=x;
        this.y=y;
        this.vx=vx;
        this.vy=vy;
        if(Server.DEBUG){
            System.out.println("Given x: "+x+"My x: "+this.x+" Given y: "+y+" My y: "+this.y);
        }
        this.updated = false;
    }

    public GameObject(){

    }
    public void setPosition(float x, float y){
        this.x = x; this.y=y;
    }

    public void setVelocity(float vx, float vy){
        this.vx = vx;
        // Matt can go fuck himself
        this.vy = vy;
    }
    public void setPosition(Vector pos){
        this.setPosition(pos.getX(),pos.getY());
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

}
