package org.titlepending.server.ServerObjects;

import jig.Vector;

import java.io.Serializable;

public class GameObject implements Serializable {
    float x;
    float y;
    float vx;
    float vy;
    public GameObject(float x, float y,float vx, float vy){
        this.x=x;
        this.y=y;
        this.vx=vx;
        this.vy=vy;
    }
    public GameObject(){

    }

    void setPosition(float x, float y){
        this.x = x; this.y=y;
    }
    void setPosition(Vector pos){
        this.setPosition(pos.getX(),pos.getY());
    }

    void update(int delta){
        //move the object here
    }

    public float getX(){return x;}
    public float getY(){return y;}
}
