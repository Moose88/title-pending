package org.titlepending.shared;

import org.titlepending.server.ServerObjects.Ship;

import java.io.Serializable;
import java.util.ArrayList;

public class Action extends CommandObject implements Serializable {
    private boolean fireCannons;
    private float x;
    private float y;
    private float vx;
    private float vy;
    private float heading;
    private int updatedShip;
    public  Action(int id){
        super(id);
        fireCannons=false;
    }


    public void setX(float x){this.x=x;}
    public void setY(float y){this.y=y;}
    public void setVx(float vx){this.vx=vx;}
    public void setVy(float vy){this.vy=vy;}
    public void setFireCannons(boolean fireCannons) {this.fireCannons = fireCannons;}
    public void setHeading(float heading) {this.heading = heading;}
    public void setUpdatedShip(int updatedShip){this.updatedShip=updatedShip;}

    public boolean getFireCannons(){return fireCannons;}
    public float getX(){return x;}
    public float getY(){return y;}
    public float getVx(){return vx;}
    public float getVy(){return vy;}
    public float getHeading() {return heading;}
    public int getUpdatedShip(){return updatedShip;}
}
